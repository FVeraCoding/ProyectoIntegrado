package com.biblioteca.back.converter;

import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.vo.EjemplarVO;

public class EjemplarConverter {

	public static EjemplarVO toVO(EjemplarEntity entity) {
		
		if(entity == null) {
			return null;
		}else {
			return new EjemplarVO(
					entity.getId(),
					entity.getReservado(),
					entity.getLibro() != null ? entity.getLibro().getId() : null
					);
		}
		
	}
	
}
