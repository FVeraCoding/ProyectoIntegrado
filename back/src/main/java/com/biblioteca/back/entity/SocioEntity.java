package com.biblioteca.back.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    
    @ManyToMany
    @JoinTable(
    		name = "Reservas_Socio",
    		joinColumns = @JoinColumn(name = "id_socio"),
    		inverseJoinColumns = @JoinColumn(name = "id_ejemplar")
    		)
    private List<EjemplarEntity> ejemplaresReservados;

    public SocioEntity() {}

    public SocioEntity(String nombre, String apellidos, LocalDate fechaNacimiento, String correoElectronico,
                       String telefono, String direccion, LocalDate fechaAlta, UsuarioEntity usuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaAlta = fechaAlta;
        this.usuario = usuario;
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

    
}
