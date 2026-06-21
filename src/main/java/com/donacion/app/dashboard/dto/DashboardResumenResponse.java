package com.donacion.app.dashboard.dto;

import java.math.BigDecimal;

public record DashboardResumenResponse(
        long cantidadAlimentosDonados,
        BigDecimal kilosEntregados,
        int personasBeneficiadas,
        long distritosImpactados,
        long donantesActivos,
        long reservasRealizadas
) {}
