package com.donacion.app.historial.controller;

import com.donacion.app.historial.dto.HistorialOperacionResponse;
import com.donacion.app.historial.service.HistorialOperacionService;
import com.donacion.app.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HistorialOperacionController {
    private final HistorialOperacionService service;

    @GetMapping("/historial/operaciones")
    public ResponseEntity<ApiResponse<List<HistorialOperacionResponse>>> mio() {
        return ResponseEntity.ok(ApiResponse.ok("Historial de operaciones", service.mio()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADMINISTRADOR')")
    @GetMapping("/admin/historial/operaciones")
    public ResponseEntity<ApiResponse<List<HistorialOperacionResponse>>> listar() {
        return ResponseEntity.ok(ApiResponse.ok("Historial global", service.listar()));
    }
}
