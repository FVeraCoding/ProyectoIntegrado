package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.SocioEntity;

@Repository
public interface SocioRepository extends JpaRepository<SocioEntity, Long> {

    Optional<SocioEntity> findByUsuario_Id(Long usuarioId);

    boolean existsByUsuario_Id(Long usuarioId);
}
