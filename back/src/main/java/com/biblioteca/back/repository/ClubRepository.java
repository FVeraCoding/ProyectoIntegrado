package com.biblioteca.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.ClubEntity;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long>{

	ClubEntity findByNombre(String nombre);
	List<ClubEntity> findByEmpleadoOrganizadorId(Long id);
	List<ClubEntity> findBySociosId(Long id);
	
}
