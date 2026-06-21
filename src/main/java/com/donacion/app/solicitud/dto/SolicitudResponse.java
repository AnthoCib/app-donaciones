package com.donacion.app.solicitud.dto;

import com.donacion.app.solicitud.domain.EstadoSolicitud;
import java.math.*;
import java.time.*;

public record SolicitudResponse(Long idSolicitud, String codigo, Long idPublicacion, String alimento, Long idReceptor,
		String receptor, String motivo, BigDecimal cantidadSolicitada, Integer personasBeneficiadas,
		EstadoSolicitud estado, LocalDateTime fechaSolicitud, LocalDateTime fechaRespuesta,
		String observacionRespuesta) {
}
