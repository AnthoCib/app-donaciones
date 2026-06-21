package com.donacion.app.solicitud.repository;

import com.donacion.app.solicitud.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
	boolean existsByPublicacionIdPublicacionAndReceptorIdUsuario(Long p, Long r);

	List<Solicitud> findAllByReceptorIdUsuarioOrderByFechaSolicitudDesc(Long r);

	List<Solicitud> findAllByPublicacionDonanteIdUsuarioOrderByFechaSolicitudDesc(Long d);

	long countByEstado(EstadoSolicitud estado);
}
