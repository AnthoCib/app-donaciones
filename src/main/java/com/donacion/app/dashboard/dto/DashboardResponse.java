package com.donacion.app.dashboard.dto;

import java.math.BigDecimal;

public record DashboardResponse(long totalPublicaciones, long publicacionesDisponibles, BigDecimal cantidadAlimentosEntregados,
		int personasBeneficiadas, long distritosImpactados, long donantesActivos, long reservasRealizadas,
		long entregasConfirmadas, long reportesPendientes) {
}
