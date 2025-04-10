package com.backend.elex.entity;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class UserEntity {

	
	public UserEntity() {

	}

	public UserEntity(int id, String name, String email, String dni, String username,
			String password, LocalDateTime fechaCreacion) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dni = dni;
		this.username = username;
		this.password = password;
		this.fechaCreacion = fechaCreacion;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(unique = true ,nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false, length = 9)
	private String dni;
	
	@Column(nullable = false, length = 30)
	private String username;
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion;

	@PrePersist
	protected void onCreate() {
		this.fechaCreacion = LocalDateTime.now();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
	
	
}
