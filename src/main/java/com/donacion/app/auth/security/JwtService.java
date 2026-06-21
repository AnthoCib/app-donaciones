package com.donacion.app.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.donacion.app.usuario.domain.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generarToken(Usuario usuario) {

        Date fechaActual = new Date();

        Date fechaExpiracion =
                new Date(
                        fechaActual.getTime() + expiration
                );

        return Jwts.builder()
                .subject(usuario.getCorreo())
                .claim(
                        "idUsuario",
                        usuario.getIdUsuario()
                )
                .claim(
                        "rol",
                        usuario.getRol().name()
                )
                .claim(
                        "tipoEntidad",
                        usuario.getTipoEntidad().name()
                )
                .issuedAt(fechaActual)
                .expiration(fechaExpiracion)
                .signWith(obtenerClave())
                .compact();
    }

    public String extraerCorreo(String token) {

        return extraerClaim(
                token,
                Claims::getSubject
        );
    }

    public boolean validarToken(
            String token,
            UserDetails userDetails
    ) {

        String correo = extraerCorreo(token);

        return correo.equalsIgnoreCase(
                userDetails.getUsername()
        ) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {

        return extraerFechaExpiracion(token)
                .before(new Date());
    }

    private Date extraerFechaExpiracion(String token) {

        return extraerClaim(
                token,
                Claims::getExpiration
        );
    }

    private <T> T extraerClaim(
            String token,
            Function<Claims, T> resolver
    ) {

        Claims claims = Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return resolver.apply(claims);
    }

    private SecretKey obtenerClave() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }
}