package com.donacion.app.alerta.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donacion.app.alerta.domain.Alerta;
import com.donacion.app.alerta.domain.TipoAlerta;
import com.donacion.app.alerta.dto.AlertaResponse;
import com.donacion.app.alerta.repository.AlertaRepository;
import com.donacion.app.publicacion.domain.EstadoPublicacion;
import com.donacion.app.publicacion.domain.Publicacion;
import com.donacion.app.publicacion.repository.PublicacionRepository;
import com.donacion.app.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AlertaService {
	private final AlertaRepository repo;
	private final PublicacionRepository pubs;
	private final UsuarioService actual;

	@Scheduled(cron = "0 0 * * * *")
	public void revisar() {
		LocalDateTime now = LocalDateTime.now();
		for (Publicacion p : pubs.findAllByEstadoAndFechaVencimientoBetween(EstadoPublicacion.PUBLICADA, now,
				now.plusHours(24)))
			crear(p, TipoAlerta.PROXIMO_VENCIMIENTO, "El alimento está próximo a vencer");
		for (Publicacion p : pubs.findAllByEstadoAndFechaVencimientoBetween(EstadoPublicacion.PUBLICADA,
				now.minusYears(10), now)) {
			p.setEstado(EstadoPublicacion.VENCIDA);
			pubs.save(p);
			crear(p, TipoAlerta.VENCIDO, "El alimento ha vencido");
		}
	}

	private void crear(Publicacion p, TipoAlerta t, String m) {
		if (!repo.existsByPublicacionIdPublicacionAndTipo(p.getIdPublicacion(), t))
			repo.save(Alerta.builder().publicacion(p).usuario(p.getDonante()).tipo(t).mensaje(m).build());
	}

	public List<AlertaResponse> mias() {
		return repo.findAllByUsuarioIdUsuarioOrderByFechaCreacionDesc(actual.obtener().getIdUsuario()).stream()
				.map(a -> new AlertaResponse(a.getIdAlerta(), a.getPublicacion().getIdPublicacion(), a.getTipo(),
						a.getMensaje(), a.getLeida(), a.getFechaCreacion()))
				.toList();
	}
}
