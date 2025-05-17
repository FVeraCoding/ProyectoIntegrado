package com.biblioteca.back.service;

import java.util.List;
import java.util.Optional;

import com.biblioteca.back.vo.EjemplarVO;


public interface EjemplarService {

	EjemplarVO findEjemplarById(Long id);
	
	EjemplarVO addEjemplar(EjemplarVO ejemplarVO);
	
	void deleteEjemplarById(Long id);
	
	void updateEjemplarById(Long id, EjemplarVO ejemplarVO);
	
	List<EjemplarVO> listAllEjemplares();
	
}
