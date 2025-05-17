package com.biblioteca.back.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.repository.LibroRepository;
import com.biblioteca.back.vo.EjemplarVO;

@Component
public class EjemplarConverter {
	
	private LibroRepository libroRepo;

	@Autowired
	public EjemplarConverter(LibroRepository repo) {
		this.libroRepo = repo;
	}
	
	public EjemplarVO toVO(EjemplarEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			
		    System.out.println(">> toVO -> entity.getId(): " + entity.getId());

			
			return new EjemplarVO(
					entity.getId(),
					entity.getReservado(),
					entity.getLibro() != null ? entity.getLibro().getId() : null
					);
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
			
			return new EjemplarEntity(
					vo.isReservado(),
					libroAsociado
					);	
		}
	}
}
