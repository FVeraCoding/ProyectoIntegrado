package com.biblioteca.back.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="club")
public class ClubEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", length = 255, nullable = false, unique = true)
	private String nombre;
	
	@Column(name = "descripcion", length = 255, nullable = false, unique = true)
	private String descripcion;
	
	private LocalDate fecha_creacion;
	
	@ManyToOne
	@JoinColumn(name = "idEmpleadoOrganizador")
	private EmpleadoEntity empleadoOrganizador;
	
	@ManyToMany
	@JoinTable(
			name = "participacion_club_socios",
			joinColumns = @JoinColumn(name = "idClub"),
			inverseJoinColumns = @JoinColumn (name = "idSocio")
	)
	private List<SocioEntity> socios;

	public ClubEntity() {

	}

	public ClubEntity(Long id, String nombre, String descripcion, LocalDate fecha_creacion) {
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

	public EmpleadoEntity getEmpleadoOrganizador() {
		return empleadoOrganizador;
	}

	public void setEmpleadoOrganizador(EmpleadoEntity empleadoOrganizador) {
		this.empleadoOrganizador = empleadoOrganizador;
	}

	public List<SocioEntity> getSocios() {
		return socios;
	}

	public void setSocios(List<SocioEntity> socios) {
		this.socios = socios;
	}
	
	
	
}
