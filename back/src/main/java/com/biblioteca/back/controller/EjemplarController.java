package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.service.EjemplarService;
import com.biblioteca.back.vo.EjemplarVO;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ejemplares")
public class EjemplarController {

	private EjemplarService ejemplarService;

	@Autowired
	public EjemplarController(EjemplarService ejemplarService) {
		this.ejemplarService = ejemplarService;
	}

	// Crear Ejemplar
	@PreAuthorize("hasRole('EMPLEADO')")
	@PostMapping
	public ResponseEntity<EjemplarVO> crearEjemplar(@RequestBody EjemplarVO ejemplarVO) {
		EjemplarVO creado = ejemplarService.addEjemplar(ejemplarVO);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	// Obtener todos los ejemplares
	@GetMapping("/all")
	public ResponseEntity<List<EjemplarVO>> obtenerEjemplares() {
		return ResponseEntity.ok(ejemplarService.listAllEjemplares());
	}

	// Obtener ejemplar por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerEjemplarPorID(@PathVariable Long id) {
		try {
			EjemplarVO ejemplar = ejemplarService.findEjemplarById(id);
			return ResponseEntity.ok(ejemplar);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el ejemplar con id " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}
	}

	// Actualizar Ejemplar
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizarEjemplarPorID(@PathVariable Long id, @RequestBody EjemplarVO actualizado) {
		try {
			ejemplarService.updateEjemplarById(id, actualizado);
			return ResponseEntity.ok().body("Ejemplar actualizado correctamente");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el ejemplar con id " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}
	}
	
	// Borrar Ejemplar
	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarEjemplarPorID(@PathVariable Long id) {
		try {
			ejemplarService.deleteEjemplarById(id);
			return ResponseEntity.ok().body("Ejemplar borrado correctamente");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el ejemplar con id " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado");
		}
	}

}
