package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.EjemplarVO;
import com.biblioteca.back.vo.LibroVO;

public interface LibroService {
    List<LibroVO> obtenerTodos();
    List<LibroVO> sincronizarDesdeGoogleBooks(String query);
    LibroVO guardarLibro(LibroVO libroVO);
    LibroVO findById(Long id);
}
