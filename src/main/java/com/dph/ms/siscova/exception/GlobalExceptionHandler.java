package com.dph.ms.siscova.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * Manejador global de excepciones para controlar y personalizar las respuestas de errores en la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Maneja excepciones de tipo EntityNotFoundException.
     *
     * @param ex      Excepción EntityNotFoundException.
     * @param request Solicitud web asociada.
     * @return Respuesta HTTP con detalles del error.
     */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
		
		Map<String, String> errores = new HashMap<>();
        errores.put("entity", ex.getMessage()); // Agrega el mensaje de la excepción al mapa bajo la clave "entity"
        ErrorResponse message = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "Entidad no encontrada",
                errores,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	
	/**
     * Maneja excepciones de tipo EntityValidationException.
     *
     * @param ex      Excepción EntityValidationException.
     * @param request Solicitud web asociada.
     * @return Respuesta HTTP con detalles del error.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errores.put(fieldName, errorMessage);
        });
        ErrorResponse message = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Errores de validación", //ex.getMessage(),
                errores,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		
	}
		
	
	/**
     * Maneja excepciones de tipo IllegalOperationException.
     *
     * @param ex      Excepción IllegalOperationException.
     * @param request Solicitud web asociada.
     * @return Respuesta HTTP con detalles del error.
     */
	@ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorResponse> illegalOperationException(IllegalOperationException ex, WebRequest request) {
		Map<String, String> errores = new HashMap<>();
        errores.put("operacion", ex.getMessage()); // Agrega el mensaje de la excepción al mapa bajo la clave "operacion"
        ErrorResponse message = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Operación ilegal",
                errores,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
	
	/**
     * Maneja excepciones generales.
     *
     * @param ex      Excepción general.
     * @param request Solicitud web asociada.
     * @return Respuesta HTTP con detalles del error.
     */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex, WebRequest request) {
		Map<String, String> errores = new HashMap<>();
        errores.put("error", ex.getMessage()); // Agrega el mensaje de la excepción al mapa bajo la clave "error"
        ErrorResponse message = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                errores,
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}    
	
}
