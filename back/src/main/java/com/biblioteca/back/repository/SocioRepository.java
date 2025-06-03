package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.entity.UsuarioEntity;

@Repository
public interface SocioRepository extends JpaRepository<SocioEntity, Long> {
    Optional<SocioEntity> findByUsuarioId(Long usuarioId);
    Optional<SocioEntity> findByUsuario(UsuarioEntity usuario);
    Optional<SocioEntity> findByCorreoElectronico(String correoElectronico);
    boolean existsByUsuarioId(Long usuarioId);
}
