package com.dph.ms.siscova.exception;

public class EntityNotFoundExceptionMessages {

	public static final String VACUNA_NOT_FOUND = "La vacuna con id proporcionado no fue encontrado";
	public static final String NACIDO_NOT_FOUND = "El nacido con id proporcionado no fue encontrado";
	
	
	
    // Constructor privado para evitar instanciación
	private EntityNotFoundExceptionMessages() {
		throw new IllegalStateException ("Utility class");
	}
	
}
