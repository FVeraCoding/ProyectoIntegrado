package com.biblioteca.back.entity;

import java.time.LocalDate;

import com.biblioteca.backend.id.ReservaId;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name="Reservas")
public class ReservaEntity {

	@EmbeddedId
	private ReservaId id;
	
	@ManyToOne
	@JsonBackReference
	@MapsId("socioId")
	@JoinColumn(name = "socio_id")
	private SocioEntity socio;
	
	@ManyToOne
	@JsonBackReference
	@MapsId("ejemplarId")
	@JoinColumn(name = "ejemplar_id")
	private EjemplarEntity ejemplar;
	
	private String nombreSocio;
	private String nombreLibro;
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	
	public ReservaEntity() {
		super();
	}

	public ReservaEntity(ReservaId id, SocioEntity socio, EjemplarEntity ejemplar, String nombreSocio,
			String nombreLibro, LocalDate fechaInicio, LocalDate fechaFin) {
		super();
		this.id = id;
		this.socio = socio;
		this.ejemplar = ejemplar;
		this.nombreSocio = nombreSocio;
		this.nombreLibro = nombreLibro;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public ReservaId getId() {
		return id;
	}

	public void setId(ReservaId id) {
		this.id = id;
	}

	public SocioEntity getSocio() {
		return socio;
	}

	public void setSocio(SocioEntity socio) {
		this.socio = socio;
	}

	public EjemplarEntity getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(EjemplarEntity ejemplar) {
		this.ejemplar = ejemplar;
	}

	public String getNombreSocio() {
		return nombreSocio;
	}

	public void setNombreSocio(String nombreSocio) {
		this.nombreSocio = nombreSocio;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	
	
}
