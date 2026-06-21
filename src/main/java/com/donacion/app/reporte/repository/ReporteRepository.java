package com.donacion.app.reporte.repository;

import com.donacion.app.reporte.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
	List<Reporte> findAllByEstadoOrderByFechaReporteAsc(EstadoReporte e);

	long countByEstado(EstadoReporte e);
}
