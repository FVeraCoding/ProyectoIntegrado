package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.serviceImpl.AsistenciaServiceImpl;
import com.biblioteca.back.vo.AsistenteVO;

@Controller
@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {

	private final AsistenciaServiceImpl asistenciaService;
	
	@Autowired
	public AsistenciaController(AsistenciaServiceImpl asistenciaService) {
		super();
		this.asistenciaService = asistenciaService;
	}
	
	@GetMapping("/{idSocio}")
	public List<AsistenteVO> getAsistenciasBySocioId(@PathVariable Long idSocio){
		return asistenciaService.getAsistenciasSocio(idSocio);
	}


	
}
