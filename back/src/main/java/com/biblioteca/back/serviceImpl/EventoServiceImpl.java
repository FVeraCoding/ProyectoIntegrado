package com.biblioteca.back.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.EventoConverter;
import com.biblioteca.back.converter.ReservaConverter;
import com.biblioteca.back.entity.EventoEntity;
import com.biblioteca.back.repository.EventoRepository;
import com.biblioteca.back.service.EventoService;
import com.biblioteca.back.vo.EventoVO;

@Service
public class EventoServiceImpl implements EventoService{

    private final ReservaConverter reservaConverter;
	private final EventoRepository eventoRepository;
	private final EventoConverter eventoConverter;
		

	public EventoServiceImpl(ReservaConverter reservaConverter, EventoRepository eventoRepository,
			EventoConverter eventoConverter) {
		this.reservaConverter = reservaConverter;
		this.eventoRepository = eventoRepository;
		this.eventoConverter = eventoConverter;
	}

	@Override
	public EventoVO findById(Long id) {
		EventoEntity entity = eventoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con el id especificado."));
		EventoVO vo = eventoConverter.toVO(entity);
		return vo;
	}

	@Override
	public EventoVO findByNombre(String nombre) {
		EventoEntity entity = eventoRepository.findByNombre(nombre).orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con el nombr especificado"));
		EventoVO vo = eventoConverter.toVO(entity);
		return vo;
	}

	@Override
	public EventoVO addEvento(EventoVO evento) {

		EventoEntity entity = null;
		if(evento != null) {
			 entity = eventoConverter.toEntity(evento);
			eventoRepository.save(entity);
			return eventoConverter.toVO(entity);
		}
		
		return null;
	}

	@Override
	public boolean deleteEventoById(Long id) {
		
		eventoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con el id especificado"));
		eventoRepository.deleteById(id);
		return true;
	}

	@Override
	public EventoVO updateEvento(EventoVO evento) {
		
		EventoEntity entity = eventoRepository.findById(evento.getId()).orElseThrow(() -> new RuntimeException("No se ha encontrado el evento a actualizar"));
		
		EventoEntity actualizado = eventoConverter.toEntity(evento);
		
		EventoEntity guardado = eventoRepository.save(actualizado);
		
		EventoVO vo = eventoConverter.toVO(guardado);
		
		return vo;
	}

	@Override
	public List<EventoVO> findAll() {
		List<EventoVO> listaEventos = eventoRepository.findAll().stream().map(eventoConverter::toVO).toList();
		return listaEventos;
	}

}
