package com.donacion.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private boolean success;
    private String mensaje;
    private T data;

    public static <T> ApiResponse<T> ok(
            String mensaje,
            T data
    ) {
        return ApiResponse.<T>builder()
                .success(true)
                .mensaje(mensaje)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(
            String mensaje,
            T data
    ) {
        return ApiResponse.<T>builder()
                .success(false)
                .mensaje(mensaje)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> exito(
            String mensaje
    ) {
        return ApiResponse.<Void>builder()
                .success(true)
                .mensaje(mensaje)
                .data(null)
                .build();
    }

    public static ApiResponse<Void> error(
            String mensaje
    ) {
        return ApiResponse.<Void>builder()
                .success(false)
                .mensaje(mensaje)
                .data(null)
                .build();
    }
}