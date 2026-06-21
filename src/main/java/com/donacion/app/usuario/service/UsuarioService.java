package com.donacion.app.usuario.service;



import java.util.List;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.shared.service.ICrud;
import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.domain.Usuario;

public interface UsuarioService
        extends ICrud<Usuario, Long> {

    Usuario buscarPorCorreo(String correo) throws Exception;

    Usuario cambiarEstado(
            Long idUsuario,
            EstadoUsuario estado
    ) throws Exception;

    List<Usuario> listarPorRol(
            RolUsuario rol
    ) throws Exception;

	Usuario obtener();
}
