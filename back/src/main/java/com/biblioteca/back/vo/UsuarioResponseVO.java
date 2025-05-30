package com.biblioteca.back.vo;

import com.biblioteca.back.enums.RolUsuario;

public class UsuarioResponseVO {

    private Long id;
    private String nombre;
    private RolUsuario rol;

    public UsuarioResponseVO() {}

    public UsuarioResponseVO(Long id, String nombre, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
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

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

    
}
