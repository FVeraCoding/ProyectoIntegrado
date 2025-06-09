package com.biblioteca.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.back.entity.AsistenciaEntity;
import com.biblioteca.backend.id.AsistenciaId;

public interface AsistenciaRepository extends JpaRepository<AsistenciaEntity, AsistenciaId>{

	List<AsistenciaEntity> findByIdIdSocio(Long idSocio);
	
}
