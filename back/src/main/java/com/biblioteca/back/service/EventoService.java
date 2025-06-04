package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.AsistenteVO;
import com.biblioteca.back.vo.EventoVO;
import com.biblioteca.back.vo.SocioVO;

public interface EventoService {

	List<EventoVO> findAll();
	EventoVO findById(Long id);
	EventoVO findByNombre(String nombre);
	EventoVO addEvento(EventoVO evento);
	boolean deleteEventoById(Long id);
	EventoVO updateEvento(EventoVO evento);
	EventoVO addAsistencia(EventoVO evento, SocioVO socio);
	List<AsistenteVO> obtenerAsistentesDelEvento(Long idEvento);
	
}
