package com.donacion.app.usuario.service;

import java.util.List;

import org.springframework.security.core.Authentication;
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
	public Usuario registrar(Usuario bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario actualizar(Usuario bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long cod) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario buscarPorCodigo(Long cod) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscarPorCorreo(String correo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario cambiarEstado(Long idUsuario, EstadoUsuario estado) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listarPorRol(RolUsuario rol) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}