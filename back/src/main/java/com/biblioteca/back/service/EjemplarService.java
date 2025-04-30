package com.biblioteca.back.service;

import java.util.List;
import java.util.Optional;

import com.biblioteca.back.vo.EjemplarVO;

public interface EjemplarService {

	Optional<EjemplarVO> findEjemplarById();
	
	EjemplarVO addEjemplar(EjemplarVO ejemplarVO);
	
	void deleteEjemplarById(Long id);
	
	void updateEjemplarById();
	
	List<EjemplarVO> listAllEjemplares();
	
}
