package com.dph.ms.siscova.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dph.ms.siscova.domain.Vacuna;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;
import com.dph.ms.siscova.repositories.VacunaRepository;
import com.dph.ms.siscova.exception.EntityNotFoundExceptionMessages;


@Service
public class VacunaServiceImp implements VacunaService {
	
	@Autowired
	private VacunaRepository vacRep;
	

	@Override
	@Transactional
	public List<Vacuna> listarTodos() {
		return vacRep.findAll();
	}

	@Override
	@Transactional
	public Vacuna buscarPorId(Long id) throws EntityNotFoundException {
		Optional<Vacuna> vacuna = vacRep.findById(id);
		if(vacuna.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND);
		return vacuna.get();
	}

	@Override
	@Transactional
	public Vacuna grabar(Vacuna vacuna) throws IllegalOperationException {
		
		if(!vacRep.findByNomva(vacuna.getNomva()).isEmpty()) {
			throw new IllegalOperationException("Ya existe ua vacuna con ese nombre");
		}
		
		return vacRep.save(vacuna);
	}

	@Override
	@Transactional
	public Vacuna actualizar(Vacuna vacuna, Long id) throws EntityNotFoundException, IllegalOperationException {
		Optional<Vacuna> vacEntity = vacRep.findById(id);
		//Validar si la vacuna existe o no en la bd
		if(!vacEntity.isPresent())
			throw new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND);
		
		// Validar si ya existe una vacuna con el mismo nombre
	    Optional<Vacuna> existingByNomva = vacRep.findByNomva(vacuna.getNomva());
	    if (existingByNomva.isPresent() && !existingByNomva.get().getIdVacuna().equals(id)) {
	        throw new IllegalOperationException("Ya existe una vacuna con ese nombre");
	    }
	    
	    vacuna.setIdVacuna(id);		
		return vacRep.save(vacuna);
				
			
	}

	@Override
	@Transactional
	public void eliminar(Long id) throws EntityNotFoundException, IllegalOperationException {
		vacRep.findById(id).orElseThrow(
				()->new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND)
				);			
		
		/*if(vacEntity.getNacidos()!=null) {
			throw new IllegalOperationException("La vacuna fue aplicada a un nacido");
		}*/
		vacRep.deleteById(id);

	}

	@Override
	@Transactional
	public Vacuna findByEstadova(String estadova) {
		Optional<Vacuna> vacuna = vacRep.findByEstadova(estadova);
		if(vacuna.isEmpty())throw new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND);
		return vacuna.get();
	}

	@Override
	@Transactional
	public Vacuna actualizarPorAtributos(Long id, Map<String, Object> camposActualizados)
			throws EntityNotFoundException, IllegalOperationException {
		
		Vacuna vacuna = vacRep.findById(id).
				orElseThrow(()-> new EntityNotFoundException(EntityNotFoundExceptionMessages.VACUNA_NOT_FOUND));
		
		Optional<Vacuna> existingByNomva = vacRep.findByNomva(vacuna.getNomva());
	    if (existingByNomva.isPresent() && !existingByNomva.get().getIdVacuna().equals(id)) {
	        throw new IllegalOperationException("Ya existe una vacuna con ese nombre");
	    }
	    
	    camposActualizados.forEach((campo, valor) -> {
            switch (campo) {
            	case "nomva":
            		vacuna.setNomva ((String) valor);
            		break;
            	case "estadova":
            		vacuna.setEstadova((String) valor);
            		break;
                case "descripcionva":
                	vacuna.setDescripcionva((String) valor);
                    break;          		
                case "fechaVencimiento":
                	String dateValue = (String)valor;
					try {
						vacuna.setFechaVencimiento(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue));
					} catch (ParseException e) {
						e.printStackTrace();// TODO Auto-generated catch block
						//throw new IllegalOperationException(e);
					}
				
                    break;
                default:
                    // No se hace nada para otros campos no reconocidos
                    break;
            }
        });
	
		
		return vacRep.save(vacuna);
		
	}

}
