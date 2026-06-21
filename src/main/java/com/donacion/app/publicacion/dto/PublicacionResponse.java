package com.donacion.app.publicacion.dto;

import com.donacion.app.publicacion.domain.*;
import java.math.*;
import java.time.*;

public record PublicacionResponse(Long idPublicacion, String codigo, Long idDonante, String donante, Long idCategoria,
		String categoria, String nombreAlimento, String descripcion, BigDecimal cantidadDisponible,
		UnidadMedida unidadMedida, LocalDateTime fechaVencimiento, String imagenUrl,
		String distrito, String direccion, BigDecimal latitud, BigDecimal longitud, EstadoPublicacion estado,
		LocalDateTime fechaPublicacion, String motivoObservacion) {
}
