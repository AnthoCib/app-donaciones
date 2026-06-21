package com.donacion.app.auth.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.donacion.app.categoria.domain.EstadoUsuario;
import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByCorreoIgnoreCase(correo.trim().toLowerCase())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado con el correo: " + correo
                        )
                );

        boolean cuentaActiva =
                usuario.getEstado() == EstadoUsuario.ACTIVO;

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .roles(usuario.getRol().name())
                .disabled(!cuentaActiva)
                .accountLocked(usuario.getEstado() == EstadoUsuario.BLOQUEADO)
                .accountExpired(false)
                .credentialsExpired(false)
                .build();
    }
}