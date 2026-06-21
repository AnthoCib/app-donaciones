package com.donacion.app.publicacion.service;

import com.donacion.app.categoria.repository.CategoriaRepository;
import com.donacion.app.publicacion.domain.*;
import com.donacion.app.publicacion.dto.*;
import com.donacion.app.publicacion.repository.*;

import com.donacion.app.usuario.domain.*;
import com.donacion.app.usuario.service.UsuarioService;
import com.donacion.app.utils.RecursoNoEncontradoException;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicacionServiceImpl implements PublicacionService {
	private final PublicacionRepository repo;
	private final CategoriaRepository categorias;
	private final UsuarioService actual;

	public PublicacionResponse registrar(PublicacionRequest r) {
		Usuario u = actual.obtener();
		if (u.getRol() != RolUsuario.DONANTE)
			throw new ReglaNegocioException("Solo un donante puede publicar");
		var c = categorias.findById(r.idCategoria())
				.orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
		Publicacion p = Publicacion.builder().codigo("PUB-" + System.currentTimeMillis()).donante(u).categoria(c)
				.nombreAlimento(r.nombreAlimento()).descripcion(r.descripcion())
				.cantidadDisponible(r.cantidadDisponible()).unidadMedida(r.unidadMedida())
				.fechaVencimiento(r.fechaVencimiento()).imagenUrl(r.imagenUrl()).distrito(r.distrito())
				.direccion(r.direccion()).latitud(r.latitud()).longitud(r.longitud())
				.estado(EstadoPublicacion.PENDIENTE).build();
		return map(repo.save(p));
	}

	public PublicacionResponse actualizar(Long id, PublicacionRequest r) {
		Publicacion p = get(id);
		Usuario u = actual.obtener();
		if (!p.getDonante().getIdUsuario().equals(u.getIdUsuario()))
			throw new ReglaNegocioException("No puede editar esta publicación");
		if (p.getEstado() == EstadoPublicacion.ENTREGADA)
			throw new ReglaNegocioException("Publicación entregada");
		p.setCategoria(categorias.findById(r.idCategoria())
				.orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada")));
		p.setNombreAlimento(r.nombreAlimento());
		p.setDescripcion(r.descripcion());
		p.setCantidadDisponible(r.cantidadDisponible());
		p.setUnidadMedida(r.unidadMedida());
		p.setFechaVencimiento(r.fechaVencimiento());
		p.setImagenUrl(r.imagenUrl());
		p.setDistrito(r.distrito());
		p.setDireccion(r.direccion());
		p.setLatitud(r.latitud());
		p.setLongitud(r.longitud());
		p.setEstado(EstadoPublicacion.PENDIENTE);
		return map(repo.save(p));
	}

	@Transactional(readOnly = true)
	public PublicacionResponse buscarPorId(Long id) {
		return map(get(id));
	}

	@Transactional(readOnly = true)
	public List<PublicacionResponse> listar() {
		return repo.findAll().stream().map(this::map).toList();
	}

	public void eliminar(Long id) {
		Publicacion p = get(id);
		p.setEstado(EstadoPublicacion.CANCELADA);
		repo.save(p);
	}

	@Transactional(readOnly = true)
	public List<PublicacionResponse> disponibles(String d, Long c) {
		List<Publicacion> l = d != null
				? repo.findAllByEstadoAndDistritoIgnoreCaseOrderByFechaVencimientoAsc(EstadoPublicacion.PUBLICADA, d)
				: c != null
						? repo.findAllByEstadoAndCategoriaIdCategoriaOrderByFechaVencimientoAsc(
								EstadoPublicacion.PUBLICADA, c)
						: repo.findAllByEstadoOrderByFechaVencimientoAsc(EstadoPublicacion.PUBLICADA);
		return l.stream().filter(p -> p.getFechaVencimiento().isAfter(LocalDateTime.now())).map(this::map).toList();
	}

	public List<PublicacionResponse> mias() {
		return repo.findAllByDonanteIdUsuarioOrderByFechaPublicacionDesc(actual.obtener().getIdUsuario()).stream()
				.map(this::map).toList();
	}

	public List<PublicacionResponse> cercanas(java.math.BigDecimal lat, java.math.BigDecimal lon, double radio) {
		return repo.findAllByEstadoOrderByFechaVencimientoAsc(EstadoPublicacion.PUBLICADA).stream()
				.filter(p -> distancia(lat.doubleValue(), lon.doubleValue(), p.getLatitud().doubleValue(),
						p.getLongitud().doubleValue()) <= radio)
				.map(this::map).toList();
	}

	private double distancia(double lat1, double lon1, double lat2, double lon2) {
		double r = 6371.0;
		double dLat = Math.toRadians(lat2 - lat1), dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		return r * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

	public PublicacionResponse aprobar(Long id) {
		Publicacion p = get(id);
		p.setEstado(EstadoPublicacion.PUBLICADA);
		p.setFechaAprobacion(LocalDateTime.now());
		p.setAdminAprobador(actual.obtener());
		return map(repo.save(p));
	}

	public PublicacionResponse bloquear(Long id, String m) {
		Publicacion p = get(id);
		p.setEstado(EstadoPublicacion.BLOQUEADA);
		p.setMotivoObservacion(m);
		return map(repo.save(p));
	}

	public PublicacionResponse rechazar(Long id, String m) {
		Publicacion p = get(id);
		p.setEstado(EstadoPublicacion.RECHAZADA);
		p.setMotivoObservacion(m);
		return map(repo.save(p));
	}

	private Publicacion get(Long id) {
		return repo.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Publicación no encontrada"));
	}

	public PublicacionResponse map(Publicacion p) {
		return new PublicacionResponse(p.getIdPublicacion(), p.getCodigo(), p.getDonante().getIdUsuario(),
				p.getDonante().getNombres() + " " + p.getDonante().getApellidos(), p.getCategoria().getIdCategoria(),
				p.getCategoria().getNombre(), p.getNombreAlimento(), p.getDescripcion(), p.getCantidadDisponible(),
				p.getUnidadMedida(), p.getFechaVencimiento(), p.getImagenUrl(), p.getDistrito(),
				p.getDireccion(), p.getLatitud(), p.getLongitud(), p.getEstado(), p.getFechaPublicacion(),
				p.getMotivoObservacion());
	}
}
