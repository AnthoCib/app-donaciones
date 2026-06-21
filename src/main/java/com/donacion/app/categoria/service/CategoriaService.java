package com.donacion.app.categoria.service;

import java.util.List;

import com.donacion.app.categoria.domain.Categoria;
import com.donacion.app.shared.service.ICrud;

public interface CategoriaService extends ICrud<Categoria, Long> {

	List<Categoria> listarActivas() throws Exception;

	Categoria cambiarEstado(Long idCategoria, Boolean estado) throws Exception;
}
