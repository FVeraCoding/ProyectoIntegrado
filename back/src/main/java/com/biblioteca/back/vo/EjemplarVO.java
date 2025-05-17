
package com.biblioteca.back.vo;

public class EjemplarVO {

	private Long id;
	private boolean reservado;
	private Long idLibro;
	
	public EjemplarVO(Long id, boolean reservado, Long idLibro) {
		super();
		this.id = id;
		this.reservado = reservado;
		this.idLibro = idLibro;
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
	
	
	
	
	
}
