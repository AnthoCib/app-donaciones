package com.donacion.app.solicitud.controller;

import com.donacion.app.solicitud.dto.*;
import com.donacion.app.solicitud.service.*;
import com.donacion.app.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolicitudController {
	private final SolicitudService s;

	@PreAuthorize("hasRole('RECEPTOR')")
	@PostMapping("/receptor/solicitudes")
	public ResponseEntity<ApiResponse<SolicitudResponse>> solicitar(@Valid @RequestBody SolicitudRequest r) {
		return ResponseEntity.status(201).body(ApiResponse.ok("Solicitud registrada", s.solicitar(r)));
	}

	@PreAuthorize("hasRole('RECEPTOR')")
	@GetMapping("/receptor/solicitudes")
	public ResponseEntity<ApiResponse<List<SolicitudResponse>>> mias() {
		return ResponseEntity.ok(ApiResponse.ok("Mis solicitudes", s.mias()));
	}

	@PreAuthorize("hasRole('RECEPTOR')")
	@PatchMapping("/receptor/solicitudes/{id}/cancelar")
	public ResponseEntity<ApiResponse<SolicitudResponse>> cancelar(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Solicitud cancelada", s.cancelar(id)));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@GetMapping("/donante/solicitudes")
	public ResponseEntity<ApiResponse<List<SolicitudResponse>>> recibidas() {
		return ResponseEntity.ok(ApiResponse.ok("Solicitudes recibidas", s.recibidas()));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@PatchMapping("/donante/solicitudes/{id}/aceptar")
	public ResponseEntity<ApiResponse<SolicitudResponse>> aceptar(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Solicitud aceptada", s.aceptar(id)));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@PatchMapping("/donante/solicitudes/{id}/rechazar")
	public ResponseEntity<ApiResponse<SolicitudResponse>> rechazar(@PathVariable Long id,
			@RequestParam(required = false) String observacion) {
		return ResponseEntity.ok(ApiResponse.ok("Solicitud rechazada", s.rechazar(id, observacion)));
	}
	@PreAuthorize("hasAnyRole('DONANTE','RECEPTOR')")
	@PatchMapping("/solicitudes/{id}/confirmar-entrega")
	public ResponseEntity<ApiResponse<SolicitudResponse>> confirmarEntrega(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Entrega confirmada", s.confirmarEntrega(id)));
	}

}
