package com.donacion.app.auth.service;

import com.donacion.app.auth.dto.*;

public interface AuthService {

    AuthResponse registrar(
            RegistroRequest request
    ) throws Exception;

    AuthResponse login(
            LoginRequest request
    ) throws Exception;
}