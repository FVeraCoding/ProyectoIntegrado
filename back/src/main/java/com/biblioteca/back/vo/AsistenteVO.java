package com.biblioteca.back.vo;

public class AsistenteVO {
	private Long idSocio;
	private Long idEvento;
	
	public AsistenteVO() {
		super();
	}

	public AsistenteVO(Long idSocio, Long idEvento) {
		super();
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
	
	
}
