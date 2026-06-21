package com.donacion.app.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByCorreoIgnoreCase(String correo);

	boolean existsByCorreoIgnoreCase(String correo);

	List<Usuario> findAllByRolOrderByFechaRegistroDesc(RolUsuario rol);

	List<Usuario> findAllByEstadoOrderByFechaRegistroDesc(EstadoUsuario estado);

	long countByRolAndEstado(RolUsuario rol, EstadoUsuario estado);

	
	
}
