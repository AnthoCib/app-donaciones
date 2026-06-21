package com.donacion.app.categoria.service;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donacion.app.categoria.domain.Categoria;
import com.donacion.app.categoria.repository.CategoriaRepository;

import com.donacion.app.shared.service.ICRUDImpl;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl extends ICRUDImpl<Categoria, Long> implements CategoriaService {

	private final CategoriaRepository categoriaRepository;

	@Override
	public JpaRepository<Categoria, Long> repo() {
		return categoriaRepository;
	}

	
	protected String nombreEntidad() {
		return "Categoría";
	}

	@Override
	@Transactional
	public Categoria registrar(Categoria categoria) throws Exception {

		String nombre = categoria.getNombre().trim();

		if (categoriaRepository.existsByNombreIgnoreCase(nombre)) {
			throw new ReglaNegocioException("Ya existe una categoría con el nombre: " + nombre);
		}

		categoria.setIdCategoria(null);
		categoria.setNombre(nombre);
		categoria.setEstado(true);

		return super.registrar(categoria);
	}

	@Override
	@Transactional
	public Categoria actualizar(Categoria categoria) throws Exception {

		if (categoria.getIdCategoria() == null) {
			throw new ReglaNegocioException("El código de la categoría es obligatorio");
		}

		Categoria categoriaActual = buscarPorCodigo(categoria.getIdCategoria());

		String nombre = categoria.getNombre().trim();

		categoriaRepository.findByNombreIgnoreCase(nombre)
				.filter(item -> !item.getIdCategoria().equals(categoria.getIdCategoria())).ifPresent(item -> {
					throw new ReglaNegocioException("Ya existe otra categoría con ese nombre");
				});

		categoriaActual.setNombre(nombre);

		if (categoria.getEstado() != null) {
			categoriaActual.setEstado(categoria.getEstado());
		}

		return super.actualizar(categoriaActual);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listarActivas() {
		return categoriaRepository.findAllByEstadoTrueOrderByNombreAsc();
	}

	@Override
	@Transactional
	public Categoria cambiarEstado(Long idCategoria, Boolean estado) throws Exception {

		Categoria categoria = buscarPorCodigo(idCategoria);
		categoria.setEstado(estado);

		return super.actualizar(categoria);
	}

	@Override
	@Transactional
	public void eliminar(Long idCategoria) throws Exception {

		Categoria categoria = buscarPorCodigo(idCategoria);

		// Eliminación lógica
		categoria.setEstado(false);

		super.actualizar(categoria);
	}
}