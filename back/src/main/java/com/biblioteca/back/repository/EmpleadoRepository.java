package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.entity.UsuarioEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {
    
    Optional<EmpleadoEntity> findByUsuarioId(Long usuarioId);
    Optional<EmpleadoEntity> findByUsuario(UsuarioEntity usuario);
    Optional<EmpleadoEntity> findByCorreoElectronico(String correoElectronico);


}
