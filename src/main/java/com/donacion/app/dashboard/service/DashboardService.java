package com.donacion.app.dashboard.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.dashboard.dto.DashboardResponse;
import com.donacion.app.entrega.domain.EstadoEntrega;
import com.donacion.app.entrega.repository.EntregaRepository;
import com.donacion.app.publicacion.domain.EstadoPublicacion;
import com.donacion.app.publicacion.repository.PublicacionRepository;
import com.donacion.app.reporte.domain.EstadoReporte;
import com.donacion.app.reporte.repository.ReporteRepository;
import com.donacion.app.solicitud.repository.SolicitudRepository;
import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {
	private final PublicacionRepository pubs;
	private final SolicitudRepository sols;
	private final EntregaRepository ents;
	private final ReporteRepository reps;
	private final UsuarioRepository users;

	public DashboardResponse obtener() {
		var confirmadas = ents.findAllByEstado(EstadoEntrega.CONFIRMADA);
		BigDecimal kilos = confirmadas.stream()
				.map(e -> e.getPesoEntregadoKg() == null ? BigDecimal.ZERO : e.getPesoEntregadoKg())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		int personas = confirmadas.stream()
				.mapToInt(e -> e.getPersonasBeneficiadas() == null ? 0 : e.getPersonasBeneficiadas()).sum();
		long distritos = confirmadas.stream().map(e -> e.getSolicitud().getPublicacion().getDistrito()).distinct()
				.count();
		return new DashboardResponse(pubs.count(), pubs.countByEstado(EstadoPublicacion.PUBLICADA), kilos, personas,
				distritos, users.countByRolAndEstado(RolUsuario.DONANTE, EstadoUsuario.ACTIVO), sols.count(),
				ents.countByEstado(EstadoEntrega.CONFIRMADA), reps.countByEstado(EstadoReporte.PENDIENTE));
	}
}
