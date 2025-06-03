package com.biblioteca.back.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.EventoConverter;
import com.biblioteca.back.converter.ReservaConverter;
import com.biblioteca.back.converter.SocioConverter;
import com.biblioteca.back.entity.AsistenciaEntity;
import com.biblioteca.back.entity.EventoEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.EventoRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.service.EventoService;
import com.biblioteca.back.vo.EventoVO;
import com.biblioteca.back.vo.SocioVO;
import com.biblioteca.backend.id.AsistenciaId;

@Service
public class EventoServiceImpl implements EventoService{

    private final ReservaConverter reservaConverter;
	private final EventoRepository eventoRepository;
	private final EventoConverter eventoConverter;
	private final SocioRepository socioRepository;
	private final SocioConverter socioConverter;

	public EventoServiceImpl(ReservaConverter reservaConverter, EventoRepository eventoRepository,
			EventoConverter eventoConverter, SocioRepository socioRepository, SocioConverter socioConverter) {
		super();
		this.reservaConverter = reservaConverter;
		this.eventoRepository = eventoRepository;
		this.eventoConverter = eventoConverter;
		this.socioRepository = socioRepository;
		this.socioConverter = socioConverter;
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
	
	@Override
	public EventoVO addAsistencia(EventoVO evento, SocioVO socio) {
	    Long eventoId = evento.getId();
	    Long socioId = socio.getId();

	    EventoEntity eventoEntity = eventoRepository.findById(eventoId)
	        .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
	    SocioEntity socioEntity = socioRepository.findById(socioId)
	        .orElseThrow(() -> new RuntimeException("Socio no encontrado"));

	    AsistenciaId asistenciaId = new AsistenciaId(eventoId, socioId);

	    boolean yaRegistrado = eventoEntity.getListaAsistencia().stream()
	        .anyMatch(a -> a.getId().equals(asistenciaId));

	    if (!yaRegistrado) {
	        AsistenciaEntity asistencia = new AsistenciaEntity(asistenciaId, socioEntity, eventoEntity);
	        eventoEntity.getListaAsistencia().add(asistencia);
	        eventoRepository.save(eventoEntity); 
	    }

	    EventoVO vo = eventoConverter.toVO(eventoEntity);
	    vo.setIdAsistentes(
	        eventoEntity.getListaAsistencia().stream()
	            .map(a -> a.getId().getIdSocio())
	            .toList()
	    );

	    return vo;
	}




}
