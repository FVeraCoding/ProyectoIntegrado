package com.biblioteca.back.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.AsistenciaEntity;
import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.entity.EventoEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.service.EmpleadoService;
import com.biblioteca.back.vo.EmpleadoVO;
import com.biblioteca.back.vo.EventoVO;
import com.biblioteca.backend.id.AsistenciaId;

@Component
public class EventoConverter {
	
	private EmpleadoService empleadoService;
	private EmpleadoConverter empleadoConverter;
	private SocioRepository socioRepository;
	
	public EventoConverter(EmpleadoService empleadoService, EmpleadoConverter empleadoConverter,
			SocioRepository socioRepository) {
		super();
		this.empleadoService = empleadoService;
		this.empleadoConverter = empleadoConverter;
		this.socioRepository = socioRepository;
	}

	public EventoVO toVO(EventoEntity entity) {
		EventoVO vo = new EventoVO();
		
		vo.setId(entity.getId());
		vo.setNombre(entity.getNombre());
		vo.setDescripcion(entity.getDescripcion());
		vo.setFecha(entity.getFecha());
		vo.setIdEmpleadoOrganizador(entity.getEmpleadoOrganizador().getId());
		vo.setNombreEmpleadoOrganizador(entity.getEmpleadoOrganizador().getNombre());
		vo.setNumeroAsistentes(
			    entity.getListaAsistencia() != null ? entity.getListaAsistencia().size() : 0
			);
		
		if (entity.getListaAsistencia() != null) {
		    vo.setIdAsistentes(
		        entity.getListaAsistencia().stream()
		            .map(a -> a.getId().getIdSocio())
		            .toList()
		    );
		}

		
		return vo;
	}
	
	public EventoEntity toEntity(EventoVO vo) {
		
		EventoEntity entity = new EventoEntity();

		entity.setId(vo.getId());
		entity.setNombre(vo.getNombre());
		entity.setDescripcion(vo.getDescripcion());
		entity.setFecha(vo.getFecha());
		
		//Empleado organizador
		EmpleadoVO empleadoBuscado = empleadoService.findById(vo.getIdEmpleadoOrganizador());
		if(empleadoBuscado!=null) {
			
			EmpleadoEntity empBuscadoEntity = empleadoConverter.toEntity(empleadoBuscado);
			entity.setEmpleado(empBuscadoEntity);
		}
		
		//Lista de asistentes
		if (vo.getIdAsistentes() != null) {
	        List<AsistenciaEntity> asistencias = vo.getIdAsistentes().stream().map(idSocio -> {
	            AsistenciaId asistenciaId = new AsistenciaId(idSocio, vo.getId());
	            SocioEntity socio = socioRepository.findById(idSocio)
	                    .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
	            return new AsistenciaEntity(asistenciaId, socio, entity);
	        }).toList();
	        entity.setListaAsistencia(asistencias);
	    }
		
		
		
		return entity;
		
	}
	
}
