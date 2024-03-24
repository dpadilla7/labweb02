package com.dph.ms.siscova.util;

import lombok.Data;

@Data
public class ApiResponse<T> {
	
	private boolean success;
    private String message;
    private T data;
    
    /**
     * Constructor para crear un objeto ApiResponse.
     *
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje descriptivo de la respuesta.
     * @param data    Datos asociados a la respuesta.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
