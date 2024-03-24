package com.dph.ms.siscova.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.dph.ms.siscova.domain.Nacido;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VacunaDTO {
	
	private Long idVacuna;
	@NotBlank(message = "El nombre de vacuna no puede estar en blanco")
	private String nomva;
	@NotBlank(message = "El estado no puede estar en blanco")
	@Pattern(regexp = "^(BUENO|REGULAR|MALO|TERMINADO)$", message = "Los estados permitidos son: CREADO|EN_EJECUCION|CANCELADO|ELIMINADO|TERMINADO ")
	@Pattern(regexp = "^[A-Z]*$", message = "Los estados deben estar en mayuscula")
	private String estadova;
	@NotBlank(message = "Ingrese alguna descripcion")
	private String descripcionva;
	@Future
	@NotNull(message = "La fecha de vencimiento de la vacuna no puede estar vacía")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaVencimiento;
	private List<Nacido> nacidos;

}
