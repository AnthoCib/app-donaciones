package com.donacion.app.utils;

public class ReglaNegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReglaNegocioException(String mensaje) {
		super(mensaje);
	}
}