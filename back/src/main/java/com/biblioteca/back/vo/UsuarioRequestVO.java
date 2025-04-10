package com.biblioteca.back.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestVO {

    @Size(max = 40, message = "El nombre de usuario no debe superar los 40 caracteres")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombre;
	
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;
    
    private Long id;


    public UsuarioRequestVO() {}

    public UsuarioRequestVO(String email, String password, String nombre, Long id) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
