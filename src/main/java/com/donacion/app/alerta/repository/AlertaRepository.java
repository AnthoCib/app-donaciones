package com.donacion.app.alerta.repository;

import com.donacion.app.alerta.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
	boolean existsByPublicacionIdPublicacionAndTipo(Long id, TipoAlerta tipo);

	boolean existsByPublicacionIdPublicacionAndTipoAndUsuarioIdUsuario(Long id, TipoAlerta tipo, Long usuario);

	List<Alerta> findAllByUsuarioIdUsuarioOrderByFechaCreacionDesc(Long id);
}
