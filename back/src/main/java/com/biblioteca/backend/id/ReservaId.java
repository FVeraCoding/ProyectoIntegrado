package com.biblioteca.backend.id;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReservaId {

	private Long socioId;
	private Long ejemplarId;
	
	
	public ReservaId() {

	}
	
	public ReservaId(Long socioId, Long ejemplarId) {

		this.socioId = socioId;
		this.ejemplarId = ejemplarId;
	}

	public Long getSocioId() {
		return socioId;
	}
	public void setSocioId(Long socioId) {
		this.socioId = socioId;
	}
	public Long getEjemplarId() {
		return ejemplarId;
	}
	public void setEjemplarId(Long ejemplarId) {
		this.ejemplarId = ejemplarId;
	}
	
	
	
}
