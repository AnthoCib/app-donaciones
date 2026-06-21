package com.donacion.app.categoria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donacion.app.categoria.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	Optional <Categoria>findByNombreIgnoreCase(String nombre);

	boolean existsByNombreIgnoreCase(String nombre);

	List<Categoria> findAllByEstadoTrueOrderByNombreAsc();
}
