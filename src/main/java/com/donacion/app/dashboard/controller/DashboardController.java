package com.donacion.app.dashboard.controller;

import com.donacion.app.dashboard.dto.*;
import com.donacion.app.dashboard.service.*;
import com.donacion.app.utils.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class DashboardController {
	private final DashboardService s;

	@GetMapping
	public ResponseEntity<ApiResponse<DashboardResponse>> get() {
		return ResponseEntity.ok(ApiResponse.ok("Dashboard global", s.obtener()));
	}
}
