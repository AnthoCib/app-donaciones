package com.donacion.app.usuario.dto;



import java.math.BigDecimal;

import com.donacion.app.categoria.domain.TipoEntidad;
import com.donacion.app.usuario.domain.RolUsuario;


import jakarta.validation.constraints.*;

public record UsuarioRequest(

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(
                min = 2,
                max = 80,
                message = "Los nombres deben tener entre 2 y 80 caracteres"
        )
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(
                min = 2,
                max = 100,
                message = "Los apellidos deben tener entre 2 y 100 caracteres"
        )
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 120)
        String correo,

        @Size(
                min = 8,
                max = 100,
                message = "La contraseña debe tener mínimo 8 caracteres"
        )
        String password,

        @Pattern(
                regexp = "^[0-9]{9,15}$",
                message = "El teléfono debe contener entre 9 y 15 números"
        )
        String telefono,

        @NotNull(message = "El rol es obligatorio")
        RolUsuario rol,

        @NotNull(message = "El tipo de entidad es obligatorio")
        TipoEntidad tipoEntidad,

        @NotBlank(message = "El distrito es obligatorio")
        @Size(max = 80)
        String distrito,

        @Size(max = 200)
        String direccion,

        @DecimalMin(
                value = "-90.0",
                message = "La latitud mínima es -90"
        )
        @DecimalMax(
                value = "90.0",
                message = "La latitud máxima es 90"
        )
        BigDecimal latitud,

        @DecimalMin(
                value = "-180.0",
                message = "La longitud mínima es -180"
        )
        @DecimalMax(
                value = "180.0",
                message = "La longitud máxima es 180"
        )
        BigDecimal longitud

) {
}