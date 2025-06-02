package com.biblioteca.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.ReservaEntity;
import com.biblioteca.backend.id.ReservaId;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, ReservaId> {
	List<ReservaEntity> findByIdSocioId(Long socioId);
}
