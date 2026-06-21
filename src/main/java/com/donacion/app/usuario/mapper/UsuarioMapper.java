package com.donacion.app.usuario.mapper;

import org.springframework.stereotype.Component;

import com.donacion.app.usuario.domain.Usuario;
import com.donacion.app.usuario.dto.UsuarioRequest;
import com.donacion.app.usuario.dto.UsuarioResponse;

@Component
public class UsuarioMapper {

	public Usuario toEntity(UsuarioRequest request) {

		return Usuario.builder().nombres(request.nombres()).apellidos(request.apellidos()).correo(request.correo())
				.password(request.password()).telefono(request.telefono()).rol(request.rol())
				.tipoEntidad(request.tipoEntidad()).distrito(request.distrito()).direccion(request.direccion())
				.latitud(request.latitud()).longitud(request.longitud()).build();
	}

	public UsuarioResponse toResponse(Usuario usuario) {

		return new UsuarioResponse(usuario.getIdUsuario(), usuario.getNombres(), usuario.getApellidos(),
				usuario.getCorreo(), usuario.getTelefono(), usuario.getRol(), usuario.getTipoEntidad(),
				usuario.getDistrito(), usuario.getDireccion(), usuario.getLatitud(), usuario.getLongitud(),
				usuario.getEstado(), usuario.getFechaRegistro());
	}
}
