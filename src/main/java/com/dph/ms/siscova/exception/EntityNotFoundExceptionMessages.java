package com.dph.ms.siscova.exception;

public class EntityNotFoundExceptionMessages {

	public static final String INVESTIGADOR_NOT_FOUND = "El investigador con id proporcionado no fue encontrado";
	public static final String PROYECTO_NOT_FOUND = "El proyecto con id proporcionado no fue encontrado";
	public static final String AVANCE_NOT_FOUND = "El avance con id proporcionado no fue encontrado";
	public static final String USUARIO_NOT_FOUND = "El usuario con id proporcionado no fue encontrado";
	
	
    // Constructor privado para evitar instanciación
	private EntityNotFoundExceptionMessages() {
		throw new IllegalStateException ("Utility class");
	}
	
}
