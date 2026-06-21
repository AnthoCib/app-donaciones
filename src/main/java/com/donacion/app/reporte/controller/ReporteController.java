package com.donacion.app.reporte.controller;

import com.donacion.app.reporte.dto.*;
import com.donacion.app.reporte.domain.EstadoReporte;
import com.donacion.app.reporte.service.*;
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
public class ReporteController {
	private final ReporteService s;

	@PostMapping("/reportes")
	public ResponseEntity<ApiResponse<ReporteResponse>> crear(@Valid @RequestBody ReporteRequest r) {
		return ResponseEntity.status(201).body(ApiResponse.ok("Reporte registrado", s.registrar(r)));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADMINISTRADOR')")
	@GetMapping("/admin/reportes")
	public ResponseEntity<ApiResponse<List<ReporteResponse>>> listar() {
		return ResponseEntity.ok(ApiResponse.ok("Reportes", s.listar()));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADMINISTRADOR')")
	@PatchMapping("/admin/reportes/{id}")
	public ResponseEntity<ApiResponse<ReporteResponse>> atender(@PathVariable Long id,
			@RequestParam EstadoReporte estado, @RequestParam String respuesta) {
		return ResponseEntity.ok(ApiResponse.ok("Reporte actualizado", s.atender(id, estado, respuesta)));
	}
}
