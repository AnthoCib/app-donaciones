package com.donacion.app.utils;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ValidationHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationErrors(MethodArgumentNotValidException e) {
		Map<String, String> errores = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> {
			errores.put(error.getField(), error.getDefaultMessage());
		});
		ApiResponse<Map<String, String>> response = new ApiResponse<>(false, "Errores de validación", errores);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<ApiResponse<?>> manejarModeloNotFoundException(RecursoNoEncontradoException ex,
			WebRequest request) {
		ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// ERROR 500 internal server error
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
		ApiResponse<String> response = new ApiResponse<>(false, "Error interno del servidor", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Manejo de errores de negocio 409
	@ExceptionHandler(ReglaNegocioException.class)
	public ResponseEntity<ApiResponse<?>> handleBusinessException(ReglaNegocioException ex) {
		ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);// 409
	}

}
