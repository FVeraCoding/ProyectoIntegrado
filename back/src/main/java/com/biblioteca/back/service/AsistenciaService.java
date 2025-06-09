package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.AsistenteVO;

public interface AsistenciaService {

	List<AsistenteVO> getAsistenciasSocio(Long idSocio);
	
}
