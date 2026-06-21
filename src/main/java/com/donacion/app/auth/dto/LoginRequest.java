package com.donacion.app.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene formato válido")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        String password

) {
}