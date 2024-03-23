package com.dph.ms.siscova.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="idVacuna")
public class Vacuna {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVacuna;
	private String nomva;
	private String estadova;
	private String descripcionva;
	
	// Relación con Nacido
	@ManyToMany(mappedBy = "vacunas")
    @JsonIgnore
	private List<Nacido> nacidos;
	

}
