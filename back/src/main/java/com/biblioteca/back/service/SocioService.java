package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.RegistroSocioVO;
import com.biblioteca.back.vo.SocioVO;

public interface SocioService {

	List<SocioVO> obtenerTodos();
	
	SocioVO findById(Long id);
	
    SocioVO buscarPorIdUsuario(Long idUsuario);
    
    SocioVO crearSocioConUsuario(RegistroSocioVO registroVO);

    void eliminarPorIdUsuario(Long idUsuario);
    
    void eliminarPorIdSocio(Long idSocio);
    
    void updateSocioById(SocioVO vo);

}
