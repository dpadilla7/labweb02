package com.dph.ms.siscova.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dph.ms.siscova.domain.Vacuna;

public interface VacunaRepository extends JpaRepository<Vacuna, Long> {
	Optional<Vacuna> findByEstadova(String estadova);
	Optional<Vacuna> findByNomva(String nomva);
}
