package com.donacion.app.solicitud.dto;

import jakarta.validation.constraints.*;
import java.math.*;

public record SolicitudRequest(@NotNull Long idPublicacion, @NotBlank String motivo,
		@NotNull @DecimalMin("0.01") BigDecimal cantidadSolicitada, @NotNull @Min(1) Integer personasBeneficiadas) {
}
