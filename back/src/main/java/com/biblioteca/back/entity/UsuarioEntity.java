package com.biblioteca.back.entity;

import com.biblioteca.back.enums.RolUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", unique = true, length = 40)
    private String nombre;
    
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 8)
    private RolUsuario rol;
        
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private SocioEntity socio;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private EmpleadoEntity empleado;
    

    public UsuarioEntity() {
    }


    public UsuarioEntity(Long id, String nombre, String password, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public RolUsuario getRol() {
	    return rol;
	}

	public void setRol(RolUsuario rol) {
	    this.rol = rol;
	}  

}
