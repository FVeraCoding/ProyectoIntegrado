package com.biblioteca.back.vo;

import java.time.LocalDate;
import java.util.List;

public class EventoVO {

	private Long id;
	private String nombre;
	private String descripcion;
	private LocalDate fecha;
	private Long idEmpleadoOrganizador;
	private List<Long> idAsistentes;
	private String nombreEmpleadoOrganizador;
	private int numeroAsistentes;

	

	public EventoVO(Long id, String nombre, String descripcion, LocalDate fecha, Long idEmpleadoOrganizador,
			List<Long> idAsistentes, String nombreEmpleadoOrganizador, int numeroAsistentes) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.idEmpleadoOrganizador = idEmpleadoOrganizador;
		this.idAsistentes = idAsistentes;
		this.nombreEmpleadoOrganizador = nombreEmpleadoOrganizador;
		this.numeroAsistentes = numeroAsistentes;
	}

	public EventoVO() {

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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getNombreEmpleadoOrganizador() {
		return nombreEmpleadoOrganizador;
	}

	public void setNombreEmpleadoOrganizador(String nombreEmpleadoOrganizador) {
		this.nombreEmpleadoOrganizador = nombreEmpleadoOrganizador;
	}

	public int getNumeroAsistentes() {
		return numeroAsistentes;
	}

	public void setNumeroAsistentes(int numeroAsistentes) {
		this.numeroAsistentes = numeroAsistentes;
	}

	public Long getIdEmpleadoOrganizador() {
		return idEmpleadoOrganizador;
	}

	public void setIdEmpleadoOrganizador(Long idEmpleadoOrganizador) {
		this.idEmpleadoOrganizador = idEmpleadoOrganizador;
	}

	public List<Long> getIdAsistentes() {
		return idAsistentes;
	}

	public void setIdAsistentes(List<Long> idAsistentes) {
		this.idAsistentes = idAsistentes;
	}

}
