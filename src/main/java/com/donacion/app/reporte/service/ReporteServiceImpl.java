package com.donacion.app.reporte.service;

import com.donacion.app.reporte.domain.*;
import com.donacion.app.reporte.dto.*;
import com.donacion.app.reporte.repository.*;
import com.donacion.app.usuario.service.UsuarioService;
import com.donacion.app.utils.RecursoNoEncontradoException;
import com.donacion.app.publicacion.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReporteServiceImpl implements ReporteService {
	private final ReporteRepository repo;
	private final PublicacionRepository pubs;
	private final UsuarioService actual;

	public ReporteResponse registrar(ReporteRequest r) {
		Reporte x = Reporte.builder().codigo("REP-" + System.currentTimeMillis()).reportante(actual.obtener())
				.publicacion(r.idPublicacion() == null ? null
						: pubs.findById(r.idPublicacion())
								.orElseThrow(() -> new RecursoNoEncontradoException("Publicación no encontrada")))
				.asunto(r.asunto()).descripcion(r.descripcion()).estado(EstadoReporte.PENDIENTE).build();
		return map(repo.save(x));
	}

	public List<ReporteResponse> listar() {
		return repo.findAll().stream().map(this::map).toList();
	}

	public ReporteResponse atender(Long id, EstadoReporte e, String resp) {
		Reporte r = repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Reporte no encontrado"));
		r.setEstado(e);
		r.setRespuestaAdmin(resp);
		r.setAdmin(actual.obtener());
		r.setFechaAtencion(LocalDateTime.now());
		return map(repo.save(r));
	}

	private ReporteResponse map(Reporte r) {
		return new ReporteResponse(r.getIdReporte(), r.getCodigo(),
				r.getReportante().getNombres() + " " + r.getReportante().getApellidos(),
				r.getPublicacion() == null ? null : r.getPublicacion().getIdPublicacion(), r.getAsunto(),
				r.getDescripcion(), r.getEstado(), r.getFechaReporte(), r.getRespuestaAdmin());
	}
}
