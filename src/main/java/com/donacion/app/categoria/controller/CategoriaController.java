package com.donacion.app.categoria.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donacion.app.categoria.domain.Categoria;
import com.donacion.app.categoria.dto.CategoriaRequest;
import com.donacion.app.categoria.dto.CategoriaResponse;
import com.donacion.app.categoria.mapper.CategoriaMapper;
import com.donacion.app.categoria.service.CategoriaService;
import com.donacion.app.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

	private final CategoriaService categoriaService;
	private final CategoriaMapper categoriaMapper;

	@PostMapping
	public ResponseEntity<ApiResponse<CategoriaResponse>> registrar(@Valid @RequestBody CategoriaRequest request)
			throws Exception {

		Categoria categoria = categoriaService.registrar(categoriaMapper.toEntity(request));

		CategoriaResponse response = categoriaMapper.toResponse(categoria);

		return ResponseEntity.created(URI.create("/api/categorias/" + categoria.getIdCategoria()))
				.body(ApiResponse.ok("Categoría registrada correctamente", response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoriaResponse>> actualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaRequest request) throws Exception {

		Categoria categoria = categoriaMapper.toEntity(request);

		categoria.setIdCategoria(id);

		Categoria actualizada = categoriaService.actualizar(categoria);

		return ResponseEntity
				.ok(ApiResponse.ok("Categoría actualizada correctamente", categoriaMapper.toResponse(actualizada)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoriaResponse>> buscarPorId(@PathVariable Long id) throws Exception {

		Categoria categoria = categoriaService.buscarPorCodigo(id);

		return ResponseEntity.ok(ApiResponse.ok("Categoría encontrada", categoriaMapper.toResponse(categoria)));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listar() throws Exception {

		List<CategoriaResponse> categorias = categoriaService.listar().stream().map(categoriaMapper::toResponse)
				.toList();

		return ResponseEntity.ok(ApiResponse.ok("Categorías encontradas", categorias));
	}

	@GetMapping("/activas")
	public ResponseEntity<ApiResponse<List<CategoriaResponse>>> listarActivas() throws Exception {

		List<CategoriaResponse> categorias = categoriaService.listarActivas().stream().map(categoriaMapper::toResponse)
				.toList();

		return ResponseEntity.ok(ApiResponse.ok("Categorías activas encontradas", categorias));
	}

	@PatchMapping("/{id}/estado")
	public ResponseEntity<ApiResponse<CategoriaResponse>> cambiarEstado(@PathVariable Long id,
			@RequestParam Boolean estado) throws Exception {

		Categoria categoria = categoriaService.cambiarEstado(id, estado);

		return ResponseEntity
				.ok(ApiResponse.ok("Estado de la categoría actualizado", categoriaMapper.toResponse(categoria)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) throws Exception {

		categoriaService.eliminar(id);

		return ResponseEntity.ok(ApiResponse.ok("Categoría desactivada correctamente", null));
	}
}