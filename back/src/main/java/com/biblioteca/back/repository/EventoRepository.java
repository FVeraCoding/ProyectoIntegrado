package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.EventoEntity;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long>{
	Optional<EventoEntity> findByNombre(String nombre);
}
