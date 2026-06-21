package com.donacion.app.entrega.service;

import com.donacion.app.entrega.domain.*;
import com.donacion.app.entrega.dto.*;
import com.donacion.app.entrega.repository.*;
import com.donacion.app.publicacion.domain.*;
import com.donacion.app.publicacion.repository.*;

import com.donacion.app.solicitud.domain.*;
import com.donacion.app.solicitud.repository.*;
import com.donacion.app.usuario.domain.*;
import com.donacion.app.usuario.service.UsuarioService;
import com.donacion.app.utils.RecursoNoEncontradoException;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EntregaServiceImpl implements EntregaService {
	private final EntregaRepository repo;
	private final SolicitudRepository solicitudes;
	private final PublicacionRepository publicaciones;
	private final UsuarioService actual;

	public EntregaResponse confirmarDonante(Long id, BigDecimal cantidad, String o) {
		Solicitud s = solicitudes.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));
		Usuario u = actual.obtener();
		if (!s.getPublicacion().getDonante().getIdUsuario().equals(u.getIdUsuario()))
			throw new ReglaNegocioException("No puede confirmar esta entrega");
		if (s.getEstado() != EstadoSolicitud.ACEPTADA)
			throw new ReglaNegocioException("Solicitud no aceptada");
		Entrega e = repo.findBySolicitudIdSolicitud(id)
				.orElse(Entrega.builder().codigo("ENT-" + System.currentTimeMillis()).solicitud(s)
						.cantidadEntregada(cantidad != null ? cantidad : s.getCantidadSolicitada())
						.personasBeneficiadas(s.getPersonasBeneficiadas()).build());
		e.setConfirmadaDonante(true);
		e.setEstado(EstadoEntrega.ENTREGADA_DONANTE);
		e.setFechaEntrega(LocalDateTime.now());
		e.setObservacion(o);
		return map(repo.save(e));
	}

	public EntregaResponse confirmarReceptor(Long id) {
		Entrega e = repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Entrega no encontrada"));
		if (!e.getSolicitud().getReceptor().getIdUsuario().equals(actual.obtener().getIdUsuario()))
			throw new ReglaNegocioException("No puede confirmar esta recepción");
		if (!e.getConfirmadaDonante())
			throw new ReglaNegocioException("El donante aún no confirmó la entrega");
		e.setConfirmadaReceptor(true);
		e.setEstado(EstadoEntrega.CONFIRMADA);
		Solicitud s = e.getSolicitud();
		s.setEstado(EstadoSolicitud.ENTREGADA);
		solicitudes.save(s);
		Publicacion p = s.getPublicacion();
		p.setCantidadDisponible(BigDecimal.ZERO);
		p.setEstado(EstadoPublicacion.ENTREGADA);
		publicaciones.save(p);
		return map(repo.save(e));
	}

	public List<EntregaResponse> historial() {
		Usuario u = actual.obtener();
		List<Entrega> l = u.getRol() == RolUsuario.DONANTE
				? repo.findAllBySolicitudPublicacionDonanteIdUsuario(u.getIdUsuario())
				: repo.findAllBySolicitudReceptorIdUsuario(u.getIdUsuario());
		return l.stream().map(this::map).toList();
	}

	private EntregaResponse map(Entrega e) {
		return new EntregaResponse(e.getIdEntrega(), e.getCodigo(), e.getSolicitud().getIdSolicitud(),
				e.getSolicitud().getPublicacion().getNombreAlimento(), e.getCantidadEntregada(),
				e.getPersonasBeneficiadas(), e.getConfirmadaDonante(), e.getConfirmadaReceptor(), e.getEstado(),
				e.getFechaEntrega(), e.getObservacion());
	}
}
