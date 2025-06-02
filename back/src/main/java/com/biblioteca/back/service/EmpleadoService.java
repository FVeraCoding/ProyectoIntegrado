package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.EmpleadoVO;
import com.biblioteca.back.vo.RegistroEmpleadoVO;

public interface EmpleadoService {
    List<EmpleadoVO> obtenerTodos();
    EmpleadoVO buscarPorIdUsuario(Long idUsuario);
    EmpleadoVO crearEmpleadoConUsuario(RegistroEmpleadoVO registroVO);
    void eliminarPorIdUsuario(Long idUsuario);
    EmpleadoVO findById(Long id);
}
