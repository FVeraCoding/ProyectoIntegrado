package com.biblioteca.back.vo;

import java.time.LocalDate;

import com.biblioteca.backend.id.ReservaId;

public class ReservaVO {

	private ReservaId reservaID;
	private Long socioID;
	private Long ejemplarID;
	private String nombreSocio;
	private String nombreLibro;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

	public ReservaVO() {

	}

	public ReservaVO(ReservaId reservaID, Long socioID, Long ejemplarID, String nombreSocio, String nombreLibro,
			LocalDate fechaInicio, LocalDate fechaFin) {
		super();
		this.reservaID = reservaID;
		this.socioID = socioID;
		this.ejemplarID = ejemplarID;
		this.nombreSocio = nombreSocio;
		this.nombreLibro = nombreLibro;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public ReservaId getReservaID() {
		return reservaID;
	}

	public void setReservaID(ReservaId reservaID) {
		this.reservaID = reservaID;
	}

	public Long getSocioID() {
		return socioID;
	}

	public void setSocioID(Long socioID) {
		this.socioID = socioID;
	}

	public Long getEjemplarID() {
		return ejemplarID;
	}

	public void setEjemplarID(Long ejemplarID) {
		this.ejemplarID = ejemplarID;
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

}
