package com.biblioteca.back.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ejemplar")
public class EjemplarEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Boolean reservado;
	
	@ManyToOne
	@JoinColumn(name = "id_libro")
	LibroEntity libro;
	
	@ManyToMany(mappedBy="ejemplaresReservados")
	private List<SocioEntity> socios;

	public EjemplarEntity(Boolean reservado, LibroEntity libro) {
		this.reservado = reservado;
		this.libro = libro;
	}
	
	public EjemplarEntity() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getReservado() {
		return reservado;
	}

	public void setReservado(Boolean reservado) {
		this.reservado = reservado;
	}

	public LibroEntity getLibro() {
		return libro;
	}

	public void setLibro(LibroEntity libro) {
		this.libro = libro;
	}
	
	
}
