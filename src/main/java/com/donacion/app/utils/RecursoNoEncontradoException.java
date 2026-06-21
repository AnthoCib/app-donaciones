package com.donacion.app.utils;

public class RecursoNoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RecursoNoEncontradoException(String mensaje) {
		super(mensaje);
	}
}
