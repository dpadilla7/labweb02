package com.dph.ms.siscova.services;

import java.util.List;

import com.dph.ms.siscova.domain.Nacido;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;

public interface NacidoService {

	List<Nacido> listarTodos();
	Nacido buscarPorId(Long id);
	Nacido grabar(Nacido nacido) throws IllegalOperationException;
	Nacido actualizar(Nacido nacido, Long id) throws EntityNotFoundException, IllegalOperationException;
    void eliminar(Long i) throws EntityNotFoundException, IllegalOperationException;
    public Nacido asignarVacuna (Long idNac, Long idVac) throws EntityNotFoundException, IllegalOperationException;
	
}
