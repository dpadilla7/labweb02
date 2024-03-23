package com.dph.ms.siscova.exception;


import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;


/**
 * Clase que representa un mensaje de error personalizado.
 */
@Getter
@Setter
public class ErrorResponse {
	private int statusCode;
	/**
     * Marca de tiempo cuando se generó el error.
     */
	private LocalDateTime timestamp;
	private String message;
	private Map<String, String> errors;
	private String description;
	
	/**
     * Constructor para crear un objeto ErrorMessage.
     *
     * @param statusCode  Código de estado HTTP asociado al error.
     * @param message     Mensaje descriptivo del error.
     * @param errors      Descripcion de cada error.
     * @param description Uri del recurso.
     */
	public ErrorResponse(HttpStatus statusCode, String message, Map<String, String> errors, String description) {
		this.statusCode = statusCode.value();
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.errors = errors;
		this.description = description;
	}
	
}
