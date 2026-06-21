package com.donacion.app.entrega.dto;

import com.donacion.app.entrega.domain.EstadoEntrega;
import java.math.*;
import java.time.*;

public record EntregaResponse(Long idEntrega, String codigo, Long idSolicitud, String alimento,
		BigDecimal cantidadEntregada, BigDecimal pesoEntregadoKg, Integer personasBeneficiadas,
		Boolean confirmadaDonante, Boolean confirmadaReceptor, EstadoEntrega estado, LocalDateTime fechaEntrega,
		String observacion) {
}
