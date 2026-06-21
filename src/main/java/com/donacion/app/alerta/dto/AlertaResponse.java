package com.donacion.app.alerta.dto;

import com.donacion.app.alerta.domain.TipoAlerta;
import java.time.*;

public record AlertaResponse(Long idAlerta, Long idPublicacion, TipoAlerta tipo, String mensaje, Boolean leida,
		LocalDateTime fechaCreacion) {
}
