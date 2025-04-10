package com.biblioteca.back.service;

import java.util.List;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;

public interface UsuarioService {
    UsuarioResponseVO save(UsuarioRequestVO usuarioRequestVO);
    List<UsuarioResponseVO> findAll();
    UsuarioResponseVO findByEmail(String email);
    UsuarioResponseVO findById(Long id);
    UsuarioResponseVO findByNombre(String nombre);
    UsuarioResponseVO update(UsuarioRequestVO usuarioVO);
    void delete(Long id);
    UsuarioResponseVO login(String email, String password);
}
