package com.donacion.app.usuario.dto;


import com.donacion.app.categoria.domain.EstadoUsuario;

import jakarta.validation.constraints.NotNull;

public record CambiarEstadoRequest(

        @NotNull(message = "El estado es obligatorio")
        EstadoUsuario estado

) {
}
