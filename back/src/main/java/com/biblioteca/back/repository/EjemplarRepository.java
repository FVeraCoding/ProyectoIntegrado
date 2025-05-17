package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.EjemplarEntity;

@Repository
public interface EjemplarRepository extends JpaRepository<EjemplarEntity, Long>{
	Optional<EjemplarEntity> findById(Long id);
	
}
