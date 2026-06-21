package com.donacion.app.solicitud.service;

import com.donacion.app.solicitud.domain.*;
import com.donacion.app.solicitud.dto.*;
import com.donacion.app.solicitud.repository.*;
import com.donacion.app.publicacion.domain.*;
import com.donacion.app.publicacion.repository.*;

import com.donacion.app.usuario.domain.*;
import com.donacion.app.usuario.service.UsuarioService;
import com.donacion.app.alerta.domain.TipoAlerta;
import com.donacion.app.alerta.service.AlertaService;
import com.donacion.app.historial.domain.TipoOperacion;
import com.donacion.app.historial.service.HistorialOperacionService;
import com.donacion.app.utils.RecursoNoEncontradoException;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudServiceImpl implements SolicitudService {
	private final SolicitudRepository repo;
	private final PublicacionRepository publicaciones;
	private final UsuarioService actual;
	private final AlertaService alertas;
	private final HistorialOperacionService historial;

	public SolicitudResponse solicitar(SolicitudRequest r) {
		Usuario u = actual.obtener();
		if (u.getRol() != RolUsuario.RECEPTOR)
			throw new ReglaNegocioException("Solo un receptor puede solicitar");
		Publicacion p = publicaciones.findById(r.idPublicacion())
				.orElseThrow(() -> new RecursoNoEncontradoException("Publicación no encontrada"));
		if (p.getDonante().getIdUsuario().equals(u.getIdUsuario()))
			throw new ReglaNegocioException("No puede solicitar su propia publicación");
		if (p.getEstado() != EstadoPublicacion.DISPONIBLE)
			throw new ReglaNegocioException("La publicación no está disponible");
		if (!p.getFechaVencimiento().isAfter(LocalDateTime.now()))
			throw new ReglaNegocioException("No puede reservar alimentos vencidos");
		if (p.getCantidadDisponible().compareTo(java.math.BigDecimal.ZERO) <= 0)
			throw new ReglaNegocioException("La publicación no tiene stock disponible");
		if (r.cantidadSolicitada().compareTo(p.getCantidadDisponible()) > 0)
			throw new ReglaNegocioException("Cantidad solicitada supera la disponible");
		if (repo.existsByPublicacionIdPublicacionAndReceptorIdUsuario(p.getIdPublicacion(), u.getIdUsuario()))
			throw new ReglaNegocioException("Ya solicitó esta publicación");
		Solicitud guardada = repo.save(Solicitud.builder().codigo("SOL-" + System.currentTimeMillis()).publicacion(p).receptor(u)
				.motivo(r.motivo()).cantidadSolicitada(r.cantidadSolicitada())
				.personasBeneficiadas(r.personasBeneficiadas()).estado(EstadoSolicitud.PENDIENTE).build());
		alertas.crear(p, p.getDonante(), TipoAlerta.SOLICITUD_RECIBIDA, "Recibiste una nueva solicitud de donación");
		historial.registrar(TipoOperacion.SOLICITUD_CREADA, u, p.getDonante(), p, guardada, "Solicitud de donación creada");
		return map(guardada);
	}

	public List<SolicitudResponse> mias() {
		return repo.findAllByReceptorIdUsuarioOrderByFechaSolicitudDesc(actual.obtener().getIdUsuario()).stream()
				.map(this::map).toList();
	}

	public List<SolicitudResponse> recibidas() {
		return repo.findAllByPublicacionDonanteIdUsuarioOrderByFechaSolicitudDesc(actual.obtener().getIdUsuario())
				.stream().map(this::map).toList();
	}

	public SolicitudResponse aceptar(Long id) {
		Solicitud s = get(id);
		Usuario u = actual.obtener();
		if (!s.getPublicacion().getDonante().getIdUsuario().equals(u.getIdUsuario()))
			throw new ReglaNegocioException("No puede aceptar esta solicitud");
		if (s.getEstado() != EstadoSolicitud.PENDIENTE)
			throw new ReglaNegocioException("Solicitud no pendiente");
		Publicacion p = s.getPublicacion();
		if (p.getEstado() != EstadoPublicacion.DISPONIBLE)
			throw new ReglaNegocioException("Publicación no disponible");
		if (s.getCantidadSolicitada().compareTo(p.getCantidadDisponible()) > 0)
			throw new ReglaNegocioException("Cantidad ya no disponible");
		s.setEstado(EstadoSolicitud.ACEPTADA);
		s.setFechaRespuesta(LocalDateTime.now());
		s.setFechaReserva(LocalDateTime.now());
		p.setEstado(EstadoPublicacion.RESERVADA);
		publicaciones.save(p);
		Solicitud guardada = repo.save(s);
		alertas.crear(p, s.getReceptor(), TipoAlerta.RESERVA_CONFIRMADA, "Tu reserva fue aceptada por el donante");
		historial.registrar(TipoOperacion.SOLICITUD_ACEPTADA, u, s.getReceptor(), p, guardada, "Solicitud aceptada y alimento reservado");
		return map(guardada);
	}

	public SolicitudResponse rechazar(Long id, String o) {
		Solicitud s = get(id);
		if (!s.getPublicacion().getDonante().getIdUsuario().equals(actual.obtener().getIdUsuario()))
			throw new ReglaNegocioException("No puede rechazar esta solicitud");
		s.setEstado(EstadoSolicitud.RECHAZADA);
		s.setFechaRespuesta(LocalDateTime.now());
		s.setObservacionRespuesta(o);
		Solicitud guardada = repo.save(s);
		historial.registrar(TipoOperacion.SOLICITUD_RECHAZADA, actual.obtener(), s.getReceptor(), s.getPublicacion(), guardada, "Solicitud rechazada");
		return map(guardada);
	}

	public SolicitudResponse cancelar(Long id) {
		Solicitud s = get(id);
		if (!s.getReceptor().getIdUsuario().equals(actual.obtener().getIdUsuario()))
			throw new ReglaNegocioException("No puede cancelar esta solicitud");
		if (s.getEstado() != EstadoSolicitud.PENDIENTE)
			throw new ReglaNegocioException("Solo puede cancelar solicitudes pendientes");
		s.setEstado(EstadoSolicitud.CANCELADA);
		Solicitud guardada = repo.save(s);
		historial.registrar(TipoOperacion.SOLICITUD_CANCELADA, actual.obtener(), s.getPublicacion().getDonante(), s.getPublicacion(), guardada, "Solicitud cancelada por receptor");
		return map(guardada);
	}

	public SolicitudResponse confirmarEntrega(Long id) {
		Solicitud s = get(id);
		Usuario u = actual.obtener();
		boolean esDonante = s.getPublicacion().getDonante().getIdUsuario().equals(u.getIdUsuario());
		boolean esReceptor = s.getReceptor().getIdUsuario().equals(u.getIdUsuario());
		if (!esDonante && !esReceptor)
			throw new ReglaNegocioException("No puede confirmar esta entrega");
		if (s.getEstado() != EstadoSolicitud.ACEPTADA)
			throw new ReglaNegocioException("Solo puede confirmar entregas aceptadas");
		s.setEstado(EstadoSolicitud.ENTREGADA);
		s.setFechaConfirmacionEntrega(LocalDateTime.now());
		Publicacion p = s.getPublicacion();
		p.setEstado(EstadoPublicacion.ENTREGADA);
		publicaciones.save(p);
		Solicitud guardada = repo.save(s);
		alertas.crear(p, esDonante ? s.getReceptor() : p.getDonante(), TipoAlerta.ENTREGA_CONFIRMADA, "La entrega fue confirmada");
		historial.registrar(TipoOperacion.ENTREGA_CONFIRMADA, u, esDonante ? s.getReceptor() : p.getDonante(), p, guardada, "Entrega de donación confirmada");
		return map(guardada);
	}

	private Solicitud get(Long id) {
		return repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));
	}

	private SolicitudResponse map(Solicitud s) {
		return new SolicitudResponse(s.getIdSolicitud(), s.getCodigo(), s.getPublicacion().getIdPublicacion(),
				s.getPublicacion().getNombreAlimento(), s.getReceptor().getIdUsuario(),
				s.getReceptor().getNombres() + " " + s.getReceptor().getApellidos(), s.getMotivo(),
				s.getCantidadSolicitada(), s.getPersonasBeneficiadas(), s.getEstado(), s.getFechaSolicitud(),
				s.getFechaRespuesta(), s.getObservacionRespuesta());
	}
}
