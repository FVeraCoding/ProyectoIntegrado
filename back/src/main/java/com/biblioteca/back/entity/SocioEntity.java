package com.biblioteca.back.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "socio")
public class SocioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 40)
	private String nombre;

	@Column(nullable = false, length = 60)
	private String apellidos;

	@Column(name = "fecha_nacimiento", nullable = false)
	private LocalDate fechaNacimiento;

	@Column(name = "correo_electronico", nullable = false, length = 100)
	private String correoElectronico;

	@Column(nullable = false, length = 15)
	private String telefono;

	@Column(nullable = false, length = 255)
	private String direccion;

	@Column(name = "f_alta", nullable = false)
	private LocalDate fechaAlta;

	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false, unique = true)
	private UsuarioEntity usuario;

	@OneToMany(mappedBy = "socio")
	@JsonManagedReference
	private List<ReservaEntity> reservas;
	
	@OneToMany(mappedBy = "socio")
	@JsonManagedReference
	private List<AsistenciaEntity> asistencias;

	@ManyToMany(mappedBy = "socios")
	private List<ClubEntity> listaClubes;

	public SocioEntity() {
	}

	public SocioEntity(Long id, String nombre, String apellidos, LocalDate fechaNacimiento, String correoElectronico,
			String telefono, String direccion, LocalDate fechaAlta, UsuarioEntity usuario,
			List<ReservaEntity> reservas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
		this.direccion = direccion;
		this.fechaAlta = fechaAlta;
		this.usuario = usuario;
		this.reservas = reservas;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public List<ReservaEntity> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaEntity> reservas) {
		this.reservas = reservas;
	}

	public List<ClubEntity> getListaClubes() {
		return listaClubes;
	}

	public void setLeDistaClubes(List<ClubEntity> listaClubes) {
		this.listaClubes = listaClubes;
	}

	public List<AsistenciaEntity> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<AsistenciaEntity> asistencias) {
		this.asistencias = asistencias;
	}

	public void setListaClubes(List<ClubEntity> listaClubes) {
		this.listaClubes = listaClubes;
	}
	
	

}
