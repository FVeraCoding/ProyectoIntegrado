package com.backend.elex.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO {

	public UserVO() {

	}

	public UserVO(int id, String name, String email, String dni, String username, LocalDateTime fechaCreacion) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dni = dni;
		this.username = username;
		this.fechaCreacion = fechaCreacion;
	}

	private int id;

	private String name;

	private String email;

	private String dni;

	private String username;
	
	private LocalDateTime fechaCreacion;

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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
