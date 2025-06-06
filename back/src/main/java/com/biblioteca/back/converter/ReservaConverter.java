package com.biblioteca.back.converter;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.entity.ReservaEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.EjemplarRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.vo.ReservaVO;
import com.biblioteca.backend.id.ReservaId;

@Component
public class ReservaConverter {

    private final SocioRepository socioRepository;
    private final EjemplarRepository ejemplarRepository;

    public ReservaConverter(SocioRepository socioRepository, EjemplarRepository ejemplarRepository) {
        this.socioRepository = socioRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    public ReservaVO toVO(ReservaEntity entity) {
        ReservaVO vo = new ReservaVO();

        vo.setReservaID(entity.getId());
        vo.setSocioID(entity.getSocio().getId());
        vo.setEjemplarID(entity.getEjemplar().getId());

        vo.setNombreSocio(entity.getSocio().getNombre());
        vo.setNombreLibro(entity.getEjemplar().getLibro().getTitulo());

        vo.setFechaInicio(entity.getFechaInicio());
        vo.setFechaFin(entity.getFechaFin());

        return vo;
    }

    public ReservaEntity toEntity(ReservaVO vo) {
        ReservaEntity entity = new ReservaEntity();

        SocioEntity socio = socioRepository.findById(vo.getSocioID())
            .orElseThrow(() -> new RuntimeException("Socio no encontrado con id " + vo.getSocioID()));

        EjemplarEntity ejemplar = ejemplarRepository.findById(vo.getEjemplarID())
            .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado con id " + vo.getEjemplarID()));

        ReservaId id = new ReservaId(vo.getSocioID(), vo.getEjemplarID());

        entity.setId(id);
        entity.setSocio(socio);
        entity.setEjemplar(ejemplar);
        entity.setFechaInicio(vo.getFechaInicio());
        entity.setFechaFin(vo.getFechaFin());
        entity.setNombreLibro(vo.getNombreLibro());
        entity.setNombreSocio(socio.getNombre());

        return entity;
    }
}
