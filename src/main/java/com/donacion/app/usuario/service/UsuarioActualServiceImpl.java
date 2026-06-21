package com.donacion.app.usuario.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.repository.UsuarioRepository;
import com.donacion.app.utils.RecursoNoEncontradoException;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioActualServiceImpl
        implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario obtener() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                        authentication.getPrincipal()
                )) {

            throw new ReglaNegocioException(
                    "No existe un usuario autenticado"
            );
        }

        String correo = authentication.getName();

        return usuarioRepository
                .findByCorreoIgnoreCase(correo)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Usuario autenticado no encontrado"
                        )
                );
    }

	@Override
	public Usuario registrar(Usuario bean) {
        if (usuarioRepository.existsByCorreoIgnoreCase(bean.getCorreo())) {
            throw new ReglaNegocioException("El correo ya se encuentra registrado");
        }
        bean.setCorreo(bean.getCorreo().trim().toLowerCase());
        bean.setPassword(passwordEncoder.encode(bean.getPassword()));
        if (bean.getEstado() == null) bean.setEstado(EstadoUsuario.ACTIVO);
		return usuarioRepository.save(bean);
	}

	@Override
	public Usuario actualizar(Usuario bean) {
        Usuario actual = buscarPorCodigo(bean.getIdUsuario());
        actual.setNombres(bean.getNombres());
        actual.setApellidos(bean.getApellidos());
        actual.setTelefono(bean.getTelefono());
        actual.setRol(bean.getRol());
        actual.setTipoEntidad(bean.getTipoEntidad());
        actual.setDistrito(bean.getDistrito());
        actual.setDireccion(bean.getDireccion());
        actual.setLatitud(bean.getLatitud());
        actual.setLongitud(bean.getLongitud());
        if (bean.getEstado() != null) actual.setEstado(bean.getEstado());
		return usuarioRepository.save(actual);
	}

	@Override
	public void eliminar(Long cod) {
        Usuario usuario = buscarPorCodigo(cod);
        usuario.setEstado(EstadoUsuario.INACTIVO);
        usuarioRepository.save(usuario);
	}

	@Override
	public Usuario buscarPorCodigo(Long cod) {
		return usuarioRepository.findById(cod)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
	}

	@Override
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscarPorCorreo(String correo) {
		return usuarioRepository.findByCorreoIgnoreCase(correo.trim().toLowerCase())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
	}

	@Override
	public Usuario cambiarEstado(Long idUsuario, EstadoUsuario estado) {
        Usuario usuario = buscarPorCodigo(idUsuario);
        usuario.setEstado(estado);
		return usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> listarPorRol(RolUsuario rol) {
		return usuarioRepository.findAllByRolOrderByFechaRegistroDesc(rol);
	}
}