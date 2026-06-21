package com.donacion.app.usuario.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.categoria.domain.TipoEntidad;
import com.donacion.app.usuario.domain.RolUsuario;


public record UsuarioResponse(

        Long idUsuario,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        RolUsuario rol,
        TipoEntidad tipoEntidad,
        String distrito,
        String direccion,
        BigDecimal latitud,
        BigDecimal longitud,
        EstadoUsuario estado,
        LocalDateTime fechaRegistro

) {
}