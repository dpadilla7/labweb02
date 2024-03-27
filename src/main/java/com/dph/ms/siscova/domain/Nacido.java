package com.dph.ms.siscova.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property="idNacido")
public class Nacido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNacido;
	private String nombres;
	private String apePat;
	private String apeMat;
	@Column(unique = true)
	private String dni;
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	
	@ManyToMany
	@JsonIgnore
	private List<Vacuna> vacunas;
	
}
