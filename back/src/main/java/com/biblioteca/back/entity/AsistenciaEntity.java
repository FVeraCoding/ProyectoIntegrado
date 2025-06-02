package com.biblioteca.back.entity;

import com.biblioteca.backend.id.AsistenciaId;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name="asistencia_socios")
public class AsistenciaEntity {

	@EmbeddedId
	private AsistenciaId id;
	
	@ManyToOne
	@JsonBackReference
	@MapsId("idSocio")
	private SocioEntity socio;
	
	@ManyToOne
	@JsonBackReference
	@MapsId("idEvento")
	private EventoEntity evento;

	public AsistenciaEntity() {

	}

	public AsistenciaEntity(AsistenciaId id, SocioEntity socio, EventoEntity evento) {
		this.id = id;
		this.socio = socio;
		this.evento = evento;
	}

	public AsistenciaId getId() {
		return id;
	}

	public void setId(AsistenciaId id) {
		this.id = id;
	}

	public SocioEntity getSocio() {
		return socio;
	}

	public void setSocio(SocioEntity socio) {
		this.socio = socio;
	}

	public EventoEntity getEvento() {
		return evento;
	}

	public void setEvento(EventoEntity evento) {
		this.evento = evento;
	}
	
	
	
}
