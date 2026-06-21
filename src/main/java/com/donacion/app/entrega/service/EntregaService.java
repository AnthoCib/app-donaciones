package com.donacion.app.entrega.service;

import com.donacion.app.entrega.dto.*;
import java.math.*;
import java.util.*;

public interface EntregaService {
	EntregaResponse confirmarDonante(Long solicitud, BigDecimal peso, String observacion);

	EntregaResponse confirmarReceptor(Long entrega);

	List<EntregaResponse> historial();
}
