package com.dph.ms.siscova.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dph.ms.siscova.domain.Nacido;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VacunaDTO {
	
	private Long idVacuna;
	@NotBlank(message = "El nombre de vacuna no puede estar en blanco")
	private String nomva;
	@NotBlank(message = "El estado no puede estar en blanco")
	private String estadova;
	@NotBlank(message = "Ingrese alguna descripcion")
	private String descripcionva;
	@Future
	@NotNull(message = "La fecha de vencimiento de la vacuna no puede estar vacía")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;
	private List<Nacido> nacidos;

}
