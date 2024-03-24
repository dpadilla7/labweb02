package com.dph.ms.siscova.services;

import java.util.List;
import java.util.Map;

import com.dph.ms.siscova.domain.Vacuna;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;

public interface VacunaService {
	
	List<Vacuna> listarTodos();
	Vacuna buscarPorId(Long id);
	Vacuna grabar(Vacuna vacuna) throws IllegalOperationException;
	Vacuna actualizar(Vacuna vacuna, Long id) throws EntityNotFoundException, IllegalOperationException;
    void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException;
    Vacuna findByEstadova(String estadova);
    
    Vacuna actualizarPorAtributos(Long id, Map<String, Object> camposActualizados)
			throws EntityNotFoundException, IllegalOperationException ;

}
