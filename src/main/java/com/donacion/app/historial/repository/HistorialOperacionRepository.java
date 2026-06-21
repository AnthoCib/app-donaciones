package com.donacion.app.historial.repository;

import com.donacion.app.historial.domain.HistorialOperacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialOperacionRepository extends JpaRepository<HistorialOperacion, Long> {
    List<HistorialOperacion> findAllByUsuarioActorIdUsuarioOrUsuarioRelacionadoIdUsuarioOrderByFechaOperacionDesc(Long actor, Long relacionado);
}
