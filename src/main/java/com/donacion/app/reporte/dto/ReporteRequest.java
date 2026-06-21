package com.donacion.app.reporte.dto;

import jakarta.validation.constraints.*;

public record ReporteRequest(Long idPublicacion, @NotBlank String asunto, @NotBlank String descripcion) {
}
