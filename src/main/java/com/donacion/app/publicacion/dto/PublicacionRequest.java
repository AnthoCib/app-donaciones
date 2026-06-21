package com.donacion.app.publicacion.dto;

import com.donacion.app.publicacion.domain.UnidadMedida;
import jakarta.validation.constraints.*;
import java.math.*;
import java.time.*;

public record PublicacionRequest(@NotNull Long idCategoria, @NotBlank String nombreAlimento, String descripcion,
		@NotNull @DecimalMin("0.01") BigDecimal cantidadDisponible, @NotNull UnidadMedida unidadMedida,
		@DecimalMin("0.01") BigDecimal pesoTotalKg, @NotNull @Future LocalDateTime fechaVencimiento, String imagenUrl,
		@NotBlank String distrito, @NotBlank String direccion, @NotNull BigDecimal latitud,
		@NotNull BigDecimal longitud) {
}
