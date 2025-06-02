package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.ReservaVO;
import com.biblioteca.backend.id.ReservaId;

public interface ReservaService {

	boolean addReserva(ReservaVO vo);
	
	ReservaVO findReservaById(ReservaId reservaId);
	
	List<ReservaVO> findReservasBySocioId(Long id);
	
	void updateReserva(ReservaVO vo);
	
	boolean deleteReservaById(ReservaId id);
	
}
