
package com.biblioteca.back.vo;

import java.util.List;

import com.biblioteca.back.entity.ReservaEntity;

public class EjemplarVO {

	private Long id;
	private boolean reservado;
	private Long idLibro;
	private List<ReservaEntity> reservas;
	
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

	public List<ReservaEntity> getReservas() {
		return reservas;
	}

	public void setReservas(List<ReservaEntity> reservas) {
		this.reservas = reservas;
	}
	
	
	
	
	
}
