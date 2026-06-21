package com.donacion.app.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.donacion.app.auth.dto.AuthResponse;
import com.donacion.app.auth.dto.LoginRequest;
import com.donacion.app.auth.dto.RegistroRequest;
import com.donacion.app.auth.security.JwtService;
import com.donacion.app.categoria.domain.EstadoUsuario;

import com.donacion.app.usuario.domain.RolUsuario;
import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.repository.UsuarioRepository;
import com.donacion.app.usuario.service.UsuarioService;
import com.donacion.app.utils.ReglaNegocioException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthResponse registrar(
            RegistroRequest request
    ) throws Exception {

        if (request.rol() == RolUsuario.ADMINISTRADOR) {
            throw new ReglaNegocioException(
                    "No se permite registrar administradores públicamente"
            );
        }

        Usuario usuario = Usuario.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .correo(request.correo())
                .password(request.password())
                .telefono(request.telefono())
                .rol(request.rol())
                .tipoEntidad(request.tipoEntidad())
                .distrito(request.distrito())
                .direccion(request.direccion())
                .latitud(request.latitud())
                .longitud(request.longitud())
                .estado(EstadoUsuario.ACTIVO)
                .build();

        Usuario registrado =
                usuarioService.registrar(usuario);

        String token =
                jwtService.generarToken(registrado);

        return construirRespuesta(
                registrado,
                token
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(
            LoginRequest request
    ) throws Exception {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.correo().trim().toLowerCase(),
                            request.password()
                    )
            );

        } catch (BadCredentialsException exception) {

            throw new ReglaNegocioException(
                    "Correo o contraseña incorrectos"
            );

        } catch (DisabledException exception) {

            throw new ReglaNegocioException(
                    "El usuario se encuentra bloqueado o inactivo"
            );
        }

        Usuario usuario = usuarioRepository
                .findByCorreoIgnoreCase(request.correo())
                .orElseThrow(() ->
                        new ReglaNegocioException(
                                "Correo o contraseña incorrectos"
                        )
                );

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new ReglaNegocioException(
                    "El usuario no se encuentra activo"
            );
        }

        String token =
                jwtService.generarToken(usuario);

        return construirRespuesta(
                usuario,
                token
        );
    }

    private AuthResponse construirRespuesta(
            Usuario usuario,
            String token
    ) {

        return new AuthResponse(
                usuario.getIdUsuario(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getTipoEntidad(),
                token,
                "Bearer"
        );
    }
}