package com.biblioteca.back.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.AsistenciaEntity;
import com.biblioteca.back.entity.EventoEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.EventoRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.vo.AsistenteVO;
import com.biblioteca.backend.id.AsistenciaId;

@Component
public class AsistenciaConverter {

	private final SocioRepository socioRepo;
	private final EventoRepository eventoRepo;
	
	@Autowired	
	public AsistenciaConverter(SocioRepository socioRepo, EventoRepository eventoRepo) {
		super();
		this.socioRepo = socioRepo;
		this.eventoRepo = eventoRepo;
	}

	public AsistenteVO toVO(AsistenciaEntity entity) {
		
		AsistenteVO vo = new AsistenteVO();
		
		if(entity != null) {
			vo.setIdSocio(entity.getId().getIdSocio());
			vo.setIdEvento(entity.getId().getIdEvento());
			return vo;
		}

		return null;
	}
	
	public AsistenciaEntity toEntity(AsistenteVO vo) {
		
		AsistenciaEntity entity = new AsistenciaEntity();
		AsistenciaId asistenciaId = new AsistenciaId();
		
		if(vo != null) {
			
			asistenciaId.setIdSocio(vo.getIdSocio());
			asistenciaId.setIdEvento(vo.getIdEvento());
			
			SocioEntity socio = socioRepo.findById(vo.getIdSocio()).orElseThrow( () -> new RuntimeException("No se ha encontrado el socio especificado.") );
			EventoEntity evento = eventoRepo.findById(vo.getIdEvento()).orElseThrow( () -> new RuntimeException("No se ha encontrado el evento especificado.") );
			
			entity.setId(asistenciaId);
			entity.setSocio(socio);
			entity.setEvento(evento);
			
			return entity;
		}
		
		return null;
	}
	
}
