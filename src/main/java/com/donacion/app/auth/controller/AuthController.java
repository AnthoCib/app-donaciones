package com.donacion.app.auth.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.donacion.app.auth.dto.*;
import com.donacion.app.auth.service.AuthService;
import com.donacion.app.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/registro")
	public ResponseEntity<ApiResponse<AuthResponse>> registrar(@Valid @RequestBody RegistroRequest request)
			throws Exception {

		AuthResponse response = authService.registrar(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Usuario registrado correctamente", response));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) throws Exception {

		AuthResponse response = authService.login(request);

		return ResponseEntity.ok(ApiResponse.ok("Inicio de sesión correcto", response));
	}
}
