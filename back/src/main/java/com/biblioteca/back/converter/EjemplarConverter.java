package com.biblioteca.back.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biblioteca.back.controller.ReservaController;
import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.repository.LibroRepository;
import com.biblioteca.back.vo.EjemplarVO;

@Component
public class EjemplarConverter {

    private final LibroRepository libroRepo;
    private final ReservaConverter reservaConverter;

    @Autowired
    public EjemplarConverter(LibroRepository repo, ReservaConverter reservaConverter) {
        this.libroRepo = repo;
        this.reservaConverter = reservaConverter;
    }

	
	public EjemplarVO toVO(EjemplarEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			
			EjemplarVO vo = new EjemplarVO(entity.getReservado());
			
			vo.setId(entity.getId());
			vo.setIdLibro(entity.getId());
			vo.setReservas(
				    entity.getReservasEjemplar().stream()
				        .map(reservaConverter::toVO)
				        .toList()
				);
			
			return vo;
		}
		
	}
	
	public EjemplarEntity toEntity(EjemplarVO vo) {
		
		if(vo == null) {
			return null;
		}else {
			
			LibroEntity libroAsociado = null;
			
			if(vo.getIdLibro() != null) {
				libroAsociado = libroRepo.findById(vo.getIdLibro()).orElseThrow(() -> new RuntimeException("No se ha encontrado el libro"));
			}
			
			EjemplarEntity entity = new EjemplarEntity();
			
			entity.setId(vo.getId());
			entity.setReservado(vo.isReservado());
			entity.setLibro(libroAsociado);
			entity.setReservasEjemplar(
				    vo.getReservas().stream()
				        .map(reservaConverter::toEntity)
				        .toList()
				);			
			return entity;
		}
	}
}
