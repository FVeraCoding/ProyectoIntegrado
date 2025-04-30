package com.biblioteca.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.back.entity.LibroEntity;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {
    Optional<LibroEntity> findByIsbn(String isbn);
    Optional<LibroEntity> findById(Long id);
}
