package com.biblioteca.back.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.AsistenciaConverter;
import com.biblioteca.back.repository.AsistenciaRepository;
import com.biblioteca.back.service.AsistenciaService;
import com.biblioteca.back.vo.AsistenteVO;

@Service
public class AsistenciaServiceImpl implements AsistenciaService	{

	private final AsistenciaRepository asistenciaRepo;
	private final AsistenciaConverter asistenciaConverter;
	
	
	@Autowired
	public AsistenciaServiceImpl(AsistenciaRepository asistenciaRepo, AsistenciaConverter asistenciaConverter) {
		this.asistenciaRepo = asistenciaRepo;
		this.asistenciaConverter = asistenciaConverter;
	}



	@Override
	public List<AsistenteVO> getAsistenciasSocio(Long idSocio) {
		return asistenciaRepo.findByIdIdSocio(idSocio)
				.stream()
				.map( asistenciaConverter::toVO )
				.toList();
	}

}
