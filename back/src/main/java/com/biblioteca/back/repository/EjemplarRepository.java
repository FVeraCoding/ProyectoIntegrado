package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.back.entity.EjemplarEntity;

public interface EjemplarRepository extends JpaRepository<EjemplarEntity, Long>{
	
}
