package com.donacion.app.categoria.mapper;

import org.springframework.stereotype.Component;

import com.donacion.app.categoria.domain.Categoria;
import com.donacion.app.categoria.dto.CategoriaRequest;
import com.donacion.app.categoria.dto.CategoriaResponse;

@Component
public class CategoriaMapper {

	public Categoria toEntity(CategoriaRequest request) {
		return Categoria.builder()
				.nombre(request.nombre())
				.build();
	}

	public CategoriaResponse toResponse(Categoria categoria) {
		return new CategoriaResponse(categoria.getIdCategoria(), categoria.getNombre(), categoria.getEstado());
	}
}
