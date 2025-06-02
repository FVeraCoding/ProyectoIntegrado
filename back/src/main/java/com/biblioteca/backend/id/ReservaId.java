package com.biblioteca.backend.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReservaId implements Serializable{

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
	

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservaId)) return false;

        ReservaId that = (ReservaId) o;

        return socioId != null && socioId.equals(that.socioId)
            && ejemplarId != null && ejemplarId.equals(that.ejemplarId);
    }

    @Override
    public int hashCode() {
        int result = socioId != null ? socioId.hashCode() : 0;
        result = 31 * result + (ejemplarId != null ? ejemplarId.hashCode() : 0);
        return result;
    }


}
