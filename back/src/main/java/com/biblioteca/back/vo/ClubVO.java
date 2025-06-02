package com.biblioteca.back.vo;

import java.time.LocalDate;
import java.util.List;

import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.entity.SocioEntity;

public class ClubVO {

	private Long id;
	private String nombre;
	private String descripcion;
	private LocalDate fecha_creacion;
	private String nombreEmpleadoOrganizador;
	private Long idEmpleadoOrganizador;
	private List<Long> sociosId;
	
	public ClubVO() {

	}

	public ClubVO(Long id, String nombre, String descripcion, LocalDate fecha_creacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public String getNombreEmpleadoOrganizador() {
		return nombreEmpleadoOrganizador;
	}

	public void setNombreEmpleadoOrganizador(String nombreEmpleadoOrganizador) {
		this.nombreEmpleadoOrganizador = nombreEmpleadoOrganizador;
	}

	public List<Long> getSociosId() {
		return sociosId;
	}

	public void setSociosId(List<Long> sociosId) {
		this.sociosId = sociosId;
	}

	public Long getIdEmpleadoOrganizador() {
		return idEmpleadoOrganizador;
	}

	public void setIdEmpleadoOrganizador(Long idEmpleadoOrganizador) {
		this.idEmpleadoOrganizador = idEmpleadoOrganizador;
	}


	
		
	
}
