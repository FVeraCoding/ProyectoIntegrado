package com.biblioteca.back.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="evento")
public class EventoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha")
	private LocalDate fecha;
	
	@ManyToOne
	@JoinColumn (name = "idEmpleadoOrganizador")
	private EmpleadoEntity empleadoOrganizador;
	
	@OneToMany(mappedBy="evento", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<AsistenciaEntity> listaAsistencia;

	public EventoEntity() {
		super();
	}

	public EventoEntity(Long id, String nombre, String descripcion, LocalDate fecha, EmpleadoEntity empleado,
			List<AsistenciaEntity> listaAsistencia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.empleadoOrganizador = empleado;
		this.listaAsistencia = listaAsistencia;
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

	public EmpleadoEntity getEmpleadoOrganizador() {
		return this.empleadoOrganizador;
	}

	public void setEmpleado(EmpleadoEntity empleado) {
		this.empleadoOrganizador = empleado;
	}

	public List<AsistenciaEntity> getListaAsistencia() {
		return listaAsistencia;
	}

	public void setListaAsistencia(List<AsistenciaEntity> listaAsistencia) {
		this.listaAsistencia = listaAsistencia;
	}
	
	
}
