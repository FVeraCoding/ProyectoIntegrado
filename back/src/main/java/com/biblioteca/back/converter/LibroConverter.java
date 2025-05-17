package com.biblioteca.back.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.vo.LibroVO;
import com.biblioteca.back.vo.EjemplarVO;
import com.biblioteca.back.converter.EjemplarConverter;

@Component
public class LibroConverter {
	
	private EjemplarConverter ejemplarConverter;

    public LibroVO toVO(LibroEntity entity) {
        if (entity == null) {
            return null;
        }

        LibroVO vo = new LibroVO();
        vo.setId(entity.getId());
        vo.setTitulo(entity.getTitulo());
        vo.setAutor(entity.getAutor());
        vo.setEditorial(entity.getEditorial());
        vo.setGenero(entity.getGenero());
        vo.setIsbn(entity.getIsbn());
        vo.setAnioPublicacion(entity.getAnioPublicacion());
        vo.setImagenUrl(entity.getImagenUrl());
        vo.setDescripcion(entity.getDescripcion());

        if (entity.getEjemplares() != null) {
            List<EjemplarVO> ejemplaresVO = entity.getEjemplares().stream()
                    .map(ejemplarConverter::toVO) 
                    .collect(Collectors.toList());
            vo.setEjemplares(ejemplaresVO);
        }

        return vo;
    }

    public LibroEntity toEntity(LibroVO vo) {
        if (vo == null) {
            return null;
        }

        LibroEntity entity = new LibroEntity();
        entity.setId(vo.getId());
        entity.setTitulo(vo.getTitulo());
        entity.setAutor(vo.getAutor());
        entity.setEditorial(vo.getEditorial());
        entity.setGenero(vo.getGenero());
        entity.setIsbn(vo.getIsbn());
        entity.setAnioPublicacion(vo.getAnioPublicacion());
        entity.setImagenUrl(vo.getImagenUrl());
        entity.setDescripcion(vo.getDescripcion());


        return entity;
    }

}
