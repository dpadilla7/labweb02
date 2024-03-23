package com.dph.ms.siscova.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dph.ms.siscova.domain.Nacido;

public interface NacidoRepository extends JpaRepository<Nacido, Long> {
	Optional<Nacido> findByDni(String dni);
}
