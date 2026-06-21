package com.donacion.app.historial.dto;

import com.donacion.app.historial.domain.TipoOperacion;
import java.time.LocalDateTime;

public record HistorialOperacionResponse(
        Long idHistorial,
        TipoOperacion tipo,
        Long idUsuarioActor,
        Long idUsuarioRelacionado,
        Long idPublicacion,
        Long idSolicitud,
        String descripcion,
        LocalDateTime fechaOperacion
) {}
