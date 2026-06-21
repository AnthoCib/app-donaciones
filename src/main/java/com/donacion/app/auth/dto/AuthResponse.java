package com.donacion.app.auth.dto;



import com.donacion.app.categoria.domain.TipoEntidad;
import com.donacion.app.usuario.domain.RolUsuario;


public record AuthResponse(

        Long idUsuario,
        String nombres,
        String apellidos,
        String correo,
        RolUsuario rol,
        TipoEntidad  tipoEntidad,
        String token,
        String tipoToken

) {
}
