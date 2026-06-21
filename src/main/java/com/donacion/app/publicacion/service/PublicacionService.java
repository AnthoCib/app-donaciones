package com.donacion.app.publicacion.service;

import com.donacion.app.shared.service.CrudService;
import com.donacion.app.publicacion.dto.*;
import java.util.*;

public interface PublicacionService extends CrudService<PublicacionRequest, PublicacionResponse, Long> {
	List<PublicacionResponse> disponibles(String distrito, Long categoria);

	List<PublicacionResponse> mias();

	List<PublicacionResponse> cercanas(java.math.BigDecimal latitud, java.math.BigDecimal longitud, double radioKm);

	PublicacionResponse aprobar(Long id);

	PublicacionResponse confirmarDisponibilidad(Long id);

	PublicacionResponse bloquear(Long id, String motivo);

	PublicacionResponse rechazar(Long id, String motivo);
}
