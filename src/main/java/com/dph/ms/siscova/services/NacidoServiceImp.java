package com.dph.ms.siscova.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.ms.siscova.domain.Nacido;
import com.dph.ms.siscova.domain.Vacuna;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;
import com.dph.ms.siscova.repositories.NacidoRepository;
import com.dph.ms.siscova.repositories.VacunaRepository;
import com.dph.ms.siscova.exception.EntityNotFoundExceptionMessages;

@Service
public class NacidoServiceImp implements NacidoService {

	@Autowired
	private NacidoRepository nacRep;
	@Autowired
	private VacunaRepository vacRep;
	
	@Override
	@Transactional
	public List<Nacido> listarTodos() {
		return nacRep.findAll();
	}

	@Override
	@Transactional
	public Nacido buscarPorId(Long id) throws EntityNotFoundException {
		Optional<Nacido> nacido = nacRep.findById(id);
		if(nacido.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.NACIDO_NOT_FOUND);
		return nacido.get();
	}

	@Override
	@Transactional
	public Nacido grabar(Nacido nacido) throws IllegalOperationException {
		
		if(!nacRep.findByDni(nacido.getDni()).isEmpty()) {
			throw new IllegalOperationException("Ya existe un nacido con ese dni");
		}
		
		return nacRep.save(nacido);
	}

	@Override
	@Transactional
	public Nacido actualizar(Nacido nacido, Long id) throws EntityNotFoundException, IllegalOperationException {
		Optional<Nacido> nacEntity = nacRep.findById(id);
		//Validar si nacido existe o no en la bd
		if(!nacEntity.isPresent())
			throw new EntityNotFoundException(EntityNotFoundExceptionMessages.NACIDO_NOT_FOUND);
		
		 // Validar si ya existe un investigador con el mismo DNI
	    Optional<Nacido> existingByDni = nacRep.findByDni(nacido.getDni());
	    if (existingByDni.isPresent() && !existingByDni.get().getIdNacido().equals(id)) {
	        throw new IllegalOperationException("Ya existe un nacido con ese dni");
	    }
	    
		nacido.setIdNacido(id);		
		return nacRep.save(nacido);
	}

	@Override
	@Transactional
	public void eliminar(Long i) throws EntityNotFoundException, IllegalOperationException {
		Nacido nacEntity = nacRep.findById(i).orElseThrow(
				()->new EntityNotFoundException(EntityNotFoundExceptionMessages.NACIDO_NOT_FOUND)
				);
		
		if(nacEntity.getVacunas()!=null) {
			throw new IllegalOperationException("El nacido ya recibio una vacuna.");
		}
		
		nacRep.deleteById(i);
		
	}

	@Override
	@Transactional
	public Nacido asignarVacuna(Long idNac, Long idVac) throws EntityNotFoundException, IllegalOperationException {
		Nacido nacEntity = nacRep.findById(idNac)
				.orElseThrow(()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.NACIDO_NOT_FOUND));
		
		Vacuna vacEntity = vacRep.findById(idVac)
				.orElseThrow(
						()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND)
						);
		
		if (nacEntity.getVacunas().contains(vacEntity)){
			throw new IllegalOperationException("La vacuna ya esta aplicado a un nacido");
        }
		
		nacEntity.getVacunas().add(vacEntity);
		return nacRep.save(nacEntity);
		
	}

}
