package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.service.ClubService;
import com.biblioteca.back.vo.ClubVO;
import com.biblioteca.back.vo.SocioVO;

@RestController
@RequestMapping("/club")
public class ClubController {

	private ClubService service;
	
	@Autowired
	public ClubController(ClubService service) {
		this.service = service;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ClubVO>> findAll(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClubVO> findClubById(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findClubById(id));
	}
	
	@GetMapping("/organizador/{id}")
	public ResponseEntity<List<ClubVO>> findClubsByEmpleadoOrganizadorId(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findClubsByEmpleadoOrganizadorId(id));
	}
	
	@GetMapping("/socio/{id}")
	public ResponseEntity<List<ClubVO>> findClubsBySocioId(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findClubsBySociosId(id));
	}
	
	@GetMapping("/{idClub}/socios")
	public ResponseEntity<List<SocioVO>> obtenerSociosDelClub(@PathVariable Long idClub) {
	    List<SocioVO> socios = service.obtenerSociosDelClub(idClub);
	    return ResponseEntity.ok(socios);
	}

	
	@PostMapping
	public ResponseEntity<ClubVO> addClub(@RequestBody ClubVO vo){
		ClubVO creado = service.addClub(vo);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClubVO> updateClub(@PathVariable Long id, @RequestBody ClubVO vo){
		
		if(!id.equals(vo.getId())) {
			return ResponseEntity.badRequest().build();
		}
		
		ClubVO actualizado = service.updateClub(vo);
		
		return ResponseEntity.ok(actualizado);
	}
	
	@PutMapping("/{idClub}/{idSocio}")
	public ResponseEntity<ClubVO> addSocio(@PathVariable Long idClub, @PathVariable Long idSocio){
		ClubVO actualizado = service.addSocio(idClub, idSocio);
		return ResponseEntity.ok(actualizado);
	}
	
	@DeleteMapping("/{idClub}")
	public ResponseEntity<Void> deleteClub(@PathVariable Long idClub){
		ClubVO club= service.findClubById(idClub);
		
		boolean borrado = service.deleteClub(club);
				
	    return borrado ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
				
	}
	
	@DeleteMapping("/{idClub}/socio/{idSocio}")
	public ResponseEntity<ClubVO> retirarSocio(@PathVariable Long idClub, @PathVariable Long idSocio) {
	    ClubVO actualizado = service.retirarSocio(idClub, idSocio);
	    return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
	}

	
	
	
}
