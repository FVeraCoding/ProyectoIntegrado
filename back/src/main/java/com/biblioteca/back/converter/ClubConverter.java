package com.biblioteca.back.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.ClubEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.EmpleadoRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.vo.ClubVO;

@Component
public class ClubConverter {

	private final EmpleadoRepository empleadoRepo;
	private final SocioRepository socioRepo;

	public ClubConverter(EmpleadoRepository empleadoRepo, SocioRepository socioRepo) {
		this.empleadoRepo = empleadoRepo;
		this.socioRepo = socioRepo;
	}

	public ClubVO toVO(ClubEntity entity) {
		ClubVO vo = new ClubVO();

		vo.setId(entity.getId());
		vo.setNombre(entity.getNombre());
		vo.setDescripcion(entity.getDescripcion());
		vo.setFecha_creacion(entity.getFecha_creacion());

		if (entity.getEmpleadoOrganizador() != null) {
			vo.setNombreEmpleadoOrganizador(entity.getEmpleadoOrganizador().getNombre());
			vo.setIdEmpleadoOrganizador(entity.getEmpleadoOrganizador().getId());
		}

		
		if(entity.getSocios() != null) {
			vo.setSociosId(entity.getSocios().stream()
					.map(SocioEntity::getId)
					.collect(Collectors.toList()));
		}else {
			vo.setSociosId(List.of());
		}
		
		return vo;
	}

	public ClubEntity toEntity(ClubVO vo) {
		ClubEntity entity = new ClubEntity();

		entity.setId(vo.getId());
		entity.setNombre(vo.getNombre());
		entity.setDescripcion(vo.getDescripcion());
		entity.setFecha_creacion(vo.getFecha_creacion());

		if (vo.getIdEmpleadoOrganizador() != null) {
			empleadoRepo.findById(vo.getIdEmpleadoOrganizador()).ifPresent(entity::setEmpleadoOrganizador);
		}

		if (vo.getSociosId() != null && !vo.getSociosId().isEmpty()) {
			entity.setSocios(
				vo.getSociosId().stream()
					.map(id -> socioRepo.findById(id).orElse(null))
					.filter(socio -> socio != null)
					.collect(Collectors.toList())
			);
		}

		return entity;
	}
}
