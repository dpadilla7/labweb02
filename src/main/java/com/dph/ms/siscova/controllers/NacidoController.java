package com.dph.ms.siscova.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dph.ms.siscova.domain.Nacido;
import com.dph.ms.siscova.dto.NacidoDTO;
import com.dph.ms.siscova.services.NacidoService;
import com.dph.ms.siscova.util.ApiResponse;
import com.dph.ms.siscova.exception.EntityNotFoundException;
import com.dph.ms.siscova.exception.IllegalOperationException;

import jakarta.validation.Valid;



@RestController
@RequestMapping(value = "/api/nacidos")
public class NacidoController {
	
	@Autowired
	private NacidoService nacService;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping
    public ResponseEntity<?> obtenerTodos(){
    	List<Nacido> nacidos = nacService.listarTodos();
    	if(nacidos==null || nacidos.isEmpty()) {
    		return ResponseEntity.noContent().build();
    	}
    	else {     
    		List<NacidoDTO> nacidoDTOs = nacidos.stream()
    				.map(nacido -> modelMapper.map(nacido, NacidoDTO.class))
    				.collect(Collectors.toList());
    		ApiResponse<List<NacidoDTO>> response = new ApiResponse<>(true, "Lista de nacidos obtenida con éxito", nacidoDTOs);
    		return ResponseEntity.ok(response);
    	}

    }
	
	@GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {      
    	Nacido nacido = nacService.buscarPorId(id);
    	NacidoDTO nacidoDTO = modelMapper.map(nacido, NacidoDTO.class);
    	ApiResponse<NacidoDTO> response = new ApiResponse<>(true, "Datos del Nacido obtenidos con éxito", nacidoDTO);
    	return ResponseEntity.ok(response);
    }
	
	@PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody NacidoDTO nacidoDTO) throws IllegalOperationException{	   	    	
    	Nacido nacido = modelMapper.map(nacidoDTO, Nacido.class);
    	nacService.grabar(nacido);
    	NacidoDTO savedNacidoDTO = modelMapper.map(nacido, NacidoDTO.class);
    	ApiResponse<NacidoDTO> response = new ApiResponse<>(true, "Datos del Nacido grabados con éxito", savedNacidoDTO);
    	return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
	
	@PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody NacidoDTO nacidoDTO, @PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {       
  	
    	Nacido nacido = modelMapper.map(nacidoDTO, Nacido.class);
    	nacService.actualizar(nacido, id);
    	NacidoDTO updatedNacidoDTO = modelMapper.map(nacido, NacidoDTO.class);
    	ApiResponse<NacidoDTO> response = new ApiResponse<>(true, "Datos del Nacido actualizados con éxito", updatedNacidoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
    	nacService.eliminar(id);
    	ApiResponse<?> response = new ApiResponse<>(true, "Nacido eliminado con éxito", null);
    	return ResponseEntity.status(HttpStatus.OK).body(response);//NO_CONTENT
    }   
	
	//Asignar vacuna a un nacido
    // uri: api/nacidos/{idNac}/vacuna/{idVac}
    @PutMapping(value = "/{idNac}/vacuna/{idVac}")
    public ResponseEntity<?> asignarVacuna (@PathVariable Long idNac, @PathVariable Long idVac) throws EntityNotFoundException, IllegalOperationException{
    	Nacido nacido = nacService.asignarVacuna(idNac, idVac);
    	NacidoDTO nacidoDTO = modelMapper.map(nacido, NacidoDTO.class);
    	ApiResponse<?> response = new ApiResponse<>(true, "Nacido aplicado su Vacuna con éxito", nacidoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	

}
