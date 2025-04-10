package com.backend.elex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.elex.entity.UserEntity;

@Repository	
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByDni(String dni);
	
}
