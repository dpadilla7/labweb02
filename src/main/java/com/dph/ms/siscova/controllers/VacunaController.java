package com.dph.ms.siscova.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dph.ms.siscova.domain.Vacuna;
import com.dph.ms.siscova.dto.VacunaDTO;
import com.dph.ms.siscova.services.VacunaService;
import com.dph.ms.siscova.util.ApiResponse;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/vacunas")
public class VacunaController {
	
	@Autowired
	private VacunaService vacService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(){
		List<Vacuna> vacunas = vacService.listarTodos();
		if(vacunas==null || vacunas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<VacunaDTO> vacunaDTOs = vacunas.stream()
					.map(vacuna -> modelMapper.map(vacuna, VacunaDTO.class))
    				.collect(Collectors.toList());
			ApiResponse<List<VacunaDTO>> response = new ApiResponse<>(true, "Lista de vacunas obtenida con éxito", vacunaDTOs);
    		return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
		Vacuna vacuna = vacService.buscarPorId(id);
		VacunaDTO vacunaDTO = modelMapper.map(vacuna, VacunaDTO.class);
		ApiResponse<VacunaDTO> response = new ApiResponse<>(true, "Datos de Vacuna obtenidos con éxito", vacunaDTO);
    	return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<?> guardar(@Valid @RequestBody VacunaDTO vacunaDTO) throws IllegalOperationException {	
		Vacuna vacuna = modelMapper.map(vacunaDTO, Vacuna.class);
    	vacService.grabar(vacuna);
    	VacunaDTO savedVacunaDTO = modelMapper.map(vacuna, VacunaDTO.class);
    	ApiResponse<VacunaDTO> response = new ApiResponse<>(true, "Datos de Vacuna grabados con éxito", savedVacunaDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody VacunaDTO vacunaDTO, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException { 
		Vacuna vacuna = modelMapper.map(vacunaDTO, Vacuna.class);
    	vacService.actualizar(vacuna, id);
    	VacunaDTO updatedVacunaDTO = modelMapper.map(vacuna, VacunaDTO.class);
    	ApiResponse<VacunaDTO> response = new ApiResponse<>(true, "Datos de Vacuna actualizados con éxito", updatedVacunaDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
    	vacService.eliminar(id);
    	ApiResponse<?> response = new ApiResponse<>(true, "Vacuna eliminado con éxito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);//NO_CONTENT
    }
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> actualizarParcial(@Valid @RequestBody Map<String, Object> camposActualizados, BindingResult bindingResult  ,@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException{
		
		// Validación manual
        bindingResult = new BeanPropertyBindingResult(camposActualizados, "camposActualizados");
        
        // Validar que el nombre no este en blanco
        if (camposActualizados.containsKey("nomva")) {
            String nomva = (String) camposActualizados.get("nomva");
            if (!isValidString(nomva)) {
            	throw new IllegalOperationException ("El nombre de vacuna no puede estar en blanco");
            }
        }
        
        // Validar que el estado este en blanco
        if (camposActualizados.containsKey("estadova")) {
            String estadova = (String) camposActualizados.get("estadova");
            if (!isValidString(estadova)) {
            	throw new IllegalOperationException ("El estado no puede estar en blanco");
            }
        }
        
        // Validar que el descripcion este en blanco
        if (camposActualizados.containsKey("descripcionva")) {
            String descripcionva = (String) camposActualizados.get("descripcionva");
            if (!isValidString(descripcionva)) {
            	throw new IllegalOperationException ("Ingrese alguna descripcion");
            }
        }
        
        // Validar que el fechaRegInv sea una fecha valida
        if (camposActualizados.containsKey("fechaVencimiento")) {
            String fecha = (String) camposActualizados.get("fechaVencimiento");
            if (!isValidDateFormat(fecha)) {
            	throw new IllegalOperationException ("El atributo fechaVencimiento no tiene un formato valido (yyyy-MM-dd)");
            }
            
            if (!isValidDate(fecha)) {
            	throw new IllegalOperationException ("El atributo fechaVencimiento no tiene un formato valido (yyyy-MM-dd)");
            }
            
        }
        
        if (bindingResult.hasErrors()) {
            // Manejar los errores de validación, por ejemplo, devolver una respuesta con los errores encontrados
            return ResponseEntity.badRequest().build();
        }
        
        //Despues de las validaciones realizamos la actualizacion
        Vacuna vacuna= vacService.actualizarPorAtributos(id,camposActualizados);   	
    	
    	VacunaDTO updatedVacunaDTO = modelMapper.map(vacuna, VacunaDTO.class);
    	ApiResponse<VacunaDTO> response = new ApiResponse<>(true, "Datos del Investigador actualizados con éxito", updatedVacunaDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
        
	}
	
	
	// Método para validar que una cadena no este en blanco
    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    // Método para validar que una cadena tiene el formato de fecha
    @SuppressWarnings("unused")
	private boolean isValidDate(String dateString) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Para hacer que el formato sea estricto

        try {
            // Intenta parsear el String en un objeto Date
          	Date parsedDate = dateFormat.parse(dateString);
            return true; // Si se puede parsear correctamente, el String es una fecha válida
        } catch (ParseException e) {
            return false; // Si ocurre una excepción, el String no es una fecha válida
        }
    }
    
    private static boolean isValidDateFormat(String dateString) {
        // Define una expresión regular para el patrón yyyy-MM-dd
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        
        // Comprueba si la cadena coincide con el patrón
        return dateString.matches(regex);
    }
	
	

}
