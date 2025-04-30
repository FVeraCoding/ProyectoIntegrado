package com.biblioteca.back.converter;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.vo.EmpleadoVO;

@Component
public class EmpleadoConverter {

	public EmpleadoVO toVO(EmpleadoEntity entity) {
	    if (entity == null) return null;

	    Long idUsuario = entity.getUsuario() != null ? entity.getUsuario().getId() : null;

	    return new EmpleadoVO(
	        entity.getId(),
	        entity.getNombre(),
	        entity.getApellidos(),
	        entity.getFechaNacimiento(),
	        entity.getCorreoElectronico(),
	        entity.getTelefono(),
	        entity.getDireccion(),
	        entity.getFechaAlta(),
	        entity.getCargo(),
	        idUsuario
	    );
	}


    public EmpleadoEntity toEntity(EmpleadoVO vo) {
        if (vo == null) return null;

        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setId(vo.getId());
        entity.setNombre(vo.getNombre());
        entity.setApellidos(vo.getApellidos());
        entity.setFechaNacimiento(vo.getFechaNacimiento());
        entity.setCorreoElectronico(vo.getCorreoElectronico());
        entity.setTelefono(vo.getTelefono());
        entity.setDireccion(vo.getDireccion());
        entity.setFechaAlta(vo.getFechaAlta());
        entity.setCargo(vo.getCargo());

        return entity;
    }
}
