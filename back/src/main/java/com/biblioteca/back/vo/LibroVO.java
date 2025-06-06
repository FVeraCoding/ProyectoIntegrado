package com.biblioteca.back.vo;

import java.util.ArrayList;
import java.util.List;

public class LibroVO {

    private Long id;
    private String titulo;
    private String autor;
    private String editorial;
    private String genero;
    private String isbn;
    private Integer anioPublicacion;
    private String imagenUrl;
    private String descripcion;
    private List<EjemplarVO> ejemplares = new ArrayList();


    public LibroVO() {
        super();
    }

    public LibroVO(Long id, String titulo, String autor, String editorial, String genero, String isbn,
                   Integer anioPublicacion, String imagenUrl, String descripcion, List<EjemplarVO> ejemplares) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
    }

    // Getters y setters

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
    
    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<EjemplarVO> getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(List<EjemplarVO> ejemplares) {
        this.ejemplares = ejemplares;
    }
}
