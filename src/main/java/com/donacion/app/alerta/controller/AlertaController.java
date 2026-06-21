package com.donacion.app.alerta.controller;

import com.donacion.app.alerta.dto.*;
import com.donacion.app.alerta.service.*;
import com.donacion.app.utils.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {
	private final AlertaService s;

	@GetMapping
	public ResponseEntity<ApiResponse<List<AlertaResponse>>> mias() {
		return ResponseEntity.ok(ApiResponse.ok("Alertas", s.mias()));
	}
}
