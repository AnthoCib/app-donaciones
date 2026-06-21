package com.donacion.app.entrega.repository;

import com.donacion.app.entrega.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
	Optional<Entrega> findBySolicitudIdSolicitud(Long id);

	List<Entrega> findAllBySolicitudPublicacionDonanteIdUsuario(Long id);

	List<Entrega> findAllBySolicitudReceptorIdUsuario(Long id);

	long countByEstado(EstadoEntrega estado);

	List<Entrega> findAllByEstado(EstadoEntrega estado);
}
