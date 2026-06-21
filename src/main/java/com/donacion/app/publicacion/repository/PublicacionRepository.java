package com.donacion.app.publicacion.repository;

import com.donacion.app.publicacion.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.*;
import java.util.*;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
	List<Publicacion> findAllByEstadoOrderByFechaVencimientoAsc(EstadoPublicacion estado);

	List<Publicacion> findAllByDonanteIdUsuarioOrderByFechaPublicacionDesc(Long id);

	List<Publicacion> findAllByEstadoAndDistritoIgnoreCaseOrderByFechaVencimientoAsc(EstadoPublicacion estado,
			String distrito);

	List<Publicacion> findAllByEstadoAndCategoriaIdCategoriaOrderByFechaVencimientoAsc(EstadoPublicacion estado,
			Long categoria);

	List<Publicacion> findAllByEstadoAndFechaVencimientoBetween(EstadoPublicacion estado, LocalDateTime inicio,
			LocalDateTime fin);

	long countByEstado(EstadoPublicacion estado);

	long countDistinctDonanteByEstado(EstadoPublicacion estado);
}
