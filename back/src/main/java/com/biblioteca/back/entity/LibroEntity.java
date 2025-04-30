package com.biblioteca.back.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libro")
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String editorial;
    private String genero;
    private String isbn;
    private Integer anioPublicacion;
    private String imagenUrl;
    private String descripcion;

       
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EjemplarEntity> ejemplares;
    
	public LibroEntity() {
		
	}
	
	public LibroEntity(Long id, String titulo, String autor, String editorial, String genero, String isbn,
			Integer anioPublicacion, String imagenUrl, String descripcion) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.genero = genero;
		this.isbn = isbn;
		this.anioPublicacion = anioPublicacion;
		this.imagenUrl = imagenUrl;
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}
	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}
	public String getImagenUrl() {
		return imagenUrl;
	}
	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}

	public List<EjemplarEntity> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(List<EjemplarEntity> ejemplares) {
		this.ejemplares = ejemplares;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
    
}
