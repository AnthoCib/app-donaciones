package com.donacion.app.entrega.controller;

import com.donacion.app.entrega.dto.*;
import com.donacion.app.entrega.service.*;
import com.donacion.app.utils.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EntregaController {
	private final EntregaService s;

	@PreAuthorize("hasRole('DONANTE')")
	@PostMapping("/donante/entregas/solicitud/{id}/confirmar")
	public ResponseEntity<ApiResponse<EntregaResponse>> donante(@PathVariable Long id,
			@RequestParam(required = false) BigDecimal cantidadEntregada, @RequestParam(required = false) String observacion) {
		return ResponseEntity
				.ok(ApiResponse.ok("Entrega confirmada por donante", s.confirmarDonante(id, cantidadEntregada, observacion)));
	}

	@PreAuthorize("hasRole('RECEPTOR')")
	@PostMapping("/receptor/entregas/{id}/confirmar")
	public ResponseEntity<ApiResponse<EntregaResponse>> receptor(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Recepción confirmada", s.confirmarReceptor(id)));
	}

	@PreAuthorize("hasAnyRole('DONANTE','RECEPTOR')")
	@GetMapping("/historial/entregas")
	public ResponseEntity<ApiResponse<List<EntregaResponse>>> historial() {
		return ResponseEntity.ok(ApiResponse.ok("Historial de entregas", s.historial()));
	}
}
