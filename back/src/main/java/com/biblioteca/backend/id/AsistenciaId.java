package com.biblioteca.backend.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class AsistenciaId implements Serializable{

	private Long idSocio;
	private Long idEvento;
	
	
	public AsistenciaId() {
	}

	public AsistenciaId(Long idSocio, Long idEvento) {
		this.idSocio = idSocio;
		this.idEvento = idEvento;
	}

	public Long getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Long idSocio) {
		this.idSocio = idSocio;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsistenciaId)) return false;
        AsistenciaId that = (AsistenciaId) o;
        return Objects.equals(idSocio, that.idSocio) &&
               Objects.equals(idEvento, that.idEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSocio, idEvento);
    }
		
}
