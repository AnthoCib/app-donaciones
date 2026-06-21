package com.donacion.app.publicacion.controller;

import com.donacion.app.publicacion.dto.*;
import com.donacion.app.publicacion.service.*;
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
public class PublicacionController {
	private final PublicacionService s;

	@GetMapping("/public/publicaciones")
	public ResponseEntity<ApiResponse<List<PublicacionResponse>>> disponibles(
			@RequestParam(required = false) String distrito, @RequestParam(required = false) Long categoria) {
		return ResponseEntity.ok(ApiResponse.ok("Publicaciones disponibles", s.disponibles(distrito, categoria)));
	}

	@GetMapping("/public/publicaciones/cercanas")
	public ResponseEntity<ApiResponse<List<PublicacionResponse>>> cercanas(@RequestParam java.math.BigDecimal latitud,
			@RequestParam java.math.BigDecimal longitud, @RequestParam(defaultValue = "10") double radioKm) {
		return ResponseEntity.ok(ApiResponse.ok("Publicaciones cercanas", s.cercanas(latitud, longitud, radioKm)));
	}

	@GetMapping("/public/publicaciones/{id}")
	public ResponseEntity<ApiResponse<PublicacionResponse>> detalle(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Detalle", s.buscarPorId(id)));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@PostMapping("/donante/publicaciones")
	public ResponseEntity<ApiResponse<PublicacionResponse>> crear(@Valid @RequestBody PublicacionRequest r) {
		return ResponseEntity.status(201)
				.body(ApiResponse.ok("Publicación creada y pendiente de aprobación", s.registrar(r)));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@GetMapping("/donante/publicaciones")
	public ResponseEntity<ApiResponse<List<PublicacionResponse>>> mias() {
		return ResponseEntity.ok(ApiResponse.ok("Mis publicaciones", s.mias()));
	}

	@PreAuthorize("hasRole('DONANTE')")
	@PutMapping("/donante/publicaciones/{id}")
	public ResponseEntity<ApiResponse<PublicacionResponse>> actualizar(@PathVariable Long id,
			@Valid @RequestBody PublicacionRequest r) {
		return ResponseEntity.ok(ApiResponse.ok("Publicación actualizada", s.actualizar(id, r)));
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping("/admin/publicaciones")
	public ResponseEntity<ApiResponse<List<PublicacionResponse>>> todas() {
		return ResponseEntity.ok(ApiResponse.ok("Publicaciones", s.listar()));
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PatchMapping("/admin/publicaciones/{id}/aprobar")
	public ResponseEntity<ApiResponse<PublicacionResponse>> aprobar(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok("Publicación aprobada", s.aprobar(id)));
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PatchMapping("/admin/publicaciones/{id}/bloquear")
	public ResponseEntity<ApiResponse<PublicacionResponse>> bloquear(@PathVariable Long id,
			@RequestParam String motivo) {
		return ResponseEntity.ok(ApiResponse.ok("Publicación bloqueada", s.bloquear(id, motivo)));
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PatchMapping("/admin/publicaciones/{id}/rechazar")
	public ResponseEntity<ApiResponse<PublicacionResponse>> rechazar(@PathVariable Long id,
			@RequestParam String motivo) {
		return ResponseEntity.ok(ApiResponse.ok("Publicación rechazada", s.rechazar(id, motivo)));
	}
}
