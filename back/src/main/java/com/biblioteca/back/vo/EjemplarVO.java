
package com.biblioteca.back.vo;

import java.util.List;

import com.biblioteca.back.entity.ReservaEntity;

public class EjemplarVO {

	private Long id;
	private boolean reservado;
	private Long idLibro;
	private List<ReservaVO> reservas;
	
	public EjemplarVO() {
		super();
	}

	public EjemplarVO(boolean reservado) {
		this.reservado = reservado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isReservado() {
		return reservado;
	}

	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	public Long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}

	public List<ReservaVO> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaVO> reservas) {
		this.reservas = reservas;
	}
	
	
	
	
	
}
