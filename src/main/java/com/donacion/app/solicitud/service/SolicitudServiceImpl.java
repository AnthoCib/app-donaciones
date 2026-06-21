package com.donacion.app.solicitud.service;

import com.donacion.app.solicitud.domain.*;
import com.donacion.app.solicitud.dto.*;
import com.donacion.app.solicitud.repository.*;
import com.donacion.app.publicacion.domain.*;
import com.donacion.app.publicacion.repository.*;

import com.donacion.app.usuario.domain.*;
import com.donacion.app.usuario.service.UsuarioService;
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

	public SolicitudResponse solicitar(SolicitudRequest r) {
		Usuario u = actual.obtener();
		if (u.getRol() != RolUsuario.RECEPTOR)
			throw new ReglaNegocioException("Solo un receptor puede solicitar");
		Publicacion p = publicaciones.findById(r.idPublicacion())
				.orElseThrow(() -> new RecursoNoEncontradoException("Publicación no encontrada"));
		if (p.getDonante().getIdUsuario().equals(u.getIdUsuario()))
			throw new ReglaNegocioException("No puede solicitar su propia publicación");
		if (p.getEstado() != EstadoPublicacion.PUBLICADA)
			throw new ReglaNegocioException("La publicación no está disponible");
		if (r.cantidadSolicitada().compareTo(p.getCantidadDisponible()) > 0)
			throw new ReglaNegocioException("Cantidad solicitada supera la disponible");
		if (repo.existsByPublicacionIdPublicacionAndReceptorIdUsuario(p.getIdPublicacion(), u.getIdUsuario()))
			throw new ReglaNegocioException("Ya solicitó esta publicación");
		return map(repo.save(Solicitud.builder().codigo("SOL-" + System.currentTimeMillis()).publicacion(p).receptor(u)
				.motivo(r.motivo()).cantidadSolicitada(r.cantidadSolicitada())
				.personasBeneficiadas(r.personasBeneficiadas()).estado(EstadoSolicitud.PENDIENTE).build()));
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
		if (p.getEstado() != EstadoPublicacion.PUBLICADA)
			throw new ReglaNegocioException("Publicación no disponible");
		if (s.getCantidadSolicitada().compareTo(p.getCantidadDisponible()) > 0)
			throw new ReglaNegocioException("Cantidad ya no disponible");
		s.setEstado(EstadoSolicitud.ACEPTADA);
		s.setFechaRespuesta(LocalDateTime.now());
		p.setEstado(EstadoPublicacion.RESERVADA);
		publicaciones.save(p);
		return map(repo.save(s));
	}

	public SolicitudResponse rechazar(Long id, String o) {
		Solicitud s = get(id);
		if (!s.getPublicacion().getDonante().getIdUsuario().equals(actual.obtener().getIdUsuario()))
			throw new ReglaNegocioException("No puede rechazar esta solicitud");
		s.setEstado(EstadoSolicitud.RECHAZADA);
		s.setFechaRespuesta(LocalDateTime.now());
		s.setObservacionRespuesta(o);
		return map(repo.save(s));
	}

	public SolicitudResponse cancelar(Long id) {
		Solicitud s = get(id);
		if (!s.getReceptor().getIdUsuario().equals(actual.obtener().getIdUsuario()))
			throw new ReglaNegocioException("No puede cancelar esta solicitud");
		if (s.getEstado() != EstadoSolicitud.PENDIENTE)
			throw new ReglaNegocioException("Solo puede cancelar solicitudes pendientes");
		s.setEstado(EstadoSolicitud.CANCELADA);
		return map(repo.save(s));
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
