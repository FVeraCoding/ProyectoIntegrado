package com.biblioteca.back.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.EjemplarConverter;
import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.repository.EjemplarRepository;
import com.biblioteca.back.repository.LibroRepository;
import com.biblioteca.back.service.EjemplarService;
import com.biblioteca.back.vo.EjemplarVO;

@Service
public class EjemplarServiceImpl implements EjemplarService {

	private EjemplarRepository ejemplarRepo;
	private EjemplarConverter ejemplarConverter;
	private LibroRepository libroRepository;

	
	@Autowired
	public EjemplarServiceImpl(EjemplarRepository ejemplarRepo, EjemplarConverter ejemplarConverter, LibroRepository libroRepo) {
		super();
		this.ejemplarRepo = ejemplarRepo;
		this.ejemplarConverter = ejemplarConverter;
		this.libroRepository = libroRepo;
	}
	
	@Override
	public EjemplarVO findEjemplarById(Long id) {

		return ejemplarRepo.findById(id).map(ejemplarConverter::toVO)
				.orElseThrow(() -> new RuntimeException("No se ha encontrado el ejemplar con el ID " + id));
	}


	@Override
	public EjemplarVO addEjemplar(EjemplarVO ejemplarVO) {
	    if (ejemplarVO == null || ejemplarVO.getIdLibro() == null) {
	        throw new IllegalArgumentException("El ejemplar o el ID del libro no pueden ser nulos");
	    }

	    // Cargar el libro desde el repositorio
	    LibroEntity libro = libroRepository.findById(ejemplarVO.getIdLibro())
	        .orElseThrow(() -> new RuntimeException("No se ha encontrado el libro con ID " + ejemplarVO.getIdLibro()));

	    // Crear el ejemplar y asociarlo al libro
	    EjemplarEntity ejemplar = new EjemplarEntity();
	    ejemplar.setReservado(ejemplarVO.isReservado());
	    ejemplar.setLibro(libro);

	    // Guardar y devolver el VO
	    EjemplarEntity guardado = ejemplarRepo.save(ejemplar);
	    return ejemplarConverter.toVO(guardado);
	}


	@Override
	public void deleteEjemplarById(Long id) {

		if (ejemplarRepo.existsById(id)) {
			ejemplarRepo.deleteById(id);
		} else {
			throw new RuntimeException("No se ha encontrado el ejemplar con ID " + id);
		}
	}

	@Override
	public void updateEjemplarById(Long id, EjemplarVO ejemplarVO) {

		if (ejemplarRepo.existsById(id)) {
			EjemplarEntity actualizado = ejemplarConverter.toEntity(ejemplarVO);
			actualizado.setId(id);
			ejemplarRepo.save(actualizado);
		} else {
			throw new RuntimeException("No existe ejemplar con ID " + id);
		}

	}

	@Override
	public List<EjemplarVO> listAllEjemplares() {
		return ejemplarRepo.findAll().stream().map(ejemplarConverter::toVO).toList();
	}

}
