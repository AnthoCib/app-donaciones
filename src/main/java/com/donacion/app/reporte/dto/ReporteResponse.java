package com.donacion.app.reporte.dto;

import com.donacion.app.reporte.domain.EstadoReporte;
import java.time.*;

public record ReporteResponse(Long idReporte, String codigo, String reportante, Long idPublicacion, String asunto,
		String descripcion, EstadoReporte estado, LocalDateTime fechaReporte, String respuestaAdmin) {
}
