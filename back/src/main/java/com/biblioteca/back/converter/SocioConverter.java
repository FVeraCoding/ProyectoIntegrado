package com.biblioteca.back.converter;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.vo.SocioVO;

@Component
public class SocioConverter {

    public SocioVO toVO(SocioEntity entity) {
        if (entity == null) return null;

        SocioVO vo = new SocioVO();
        vo.setId(entity.getId());
        vo.setNombre(entity.getNombre());
        vo.setApellidos(entity.getApellidos());
        vo.setFechaNacimiento(entity.getFechaNacimiento());
        vo.setCorreoElectronico(entity.getCorreoElectronico());
        vo.setTelefono(entity.getTelefono());
        vo.setDireccion(entity.getDireccion());
        vo.setFechaAlta(entity.getFechaAlta());

        if (entity.getUsuario() != null) {
            vo.setIdUsuario(entity.getUsuario().getId());
        }

        return vo;
    }

    public SocioEntity toEntity(SocioVO vo) {
        if (vo == null) return null;

        SocioEntity entity = new SocioEntity();
        entity.setId(vo.getId());
        entity.setNombre(vo.getNombre());
        entity.setApellidos(vo.getApellidos());
        entity.setFechaNacimiento(vo.getFechaNacimiento());
        entity.setCorreoElectronico(vo.getCorreoElectronico());
        entity.setTelefono(vo.getTelefono());
        entity.setDireccion(vo.getDireccion());
        entity.setFechaAlta(vo.getFechaAlta());


        return entity;
    }
}
