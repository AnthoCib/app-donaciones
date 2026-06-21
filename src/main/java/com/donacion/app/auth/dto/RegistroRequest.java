package com.donacion.app.auth.dto;

import java.math.BigDecimal;

import com.donacion.app.categoria.domain.TipoEntidad;
import com.donacion.app.usuario.domain.RolUsuario;


import jakarta.validation.constraints.*;

public record RegistroRequest(

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(min = 2, max = 80)
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(min = 2, max = 100)
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene formato válido")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
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
        String distrito,

        String direccion,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitud,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitud

) {
}