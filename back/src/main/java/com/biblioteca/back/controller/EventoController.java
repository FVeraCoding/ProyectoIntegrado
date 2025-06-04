package com.biblioteca.back.controller;

import java.util.List;
import java.util.Map;

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

import com.biblioteca.back.service.EventoService;
import com.biblioteca.back.service.SocioService;
import com.biblioteca.back.vo.AsistenteVO;
import com.biblioteca.back.vo.EventoVO;
import com.biblioteca.back.vo.SocioVO;

@RestController
@RequestMapping("/evento")
public class EventoController {

	private final EventoService eventoService;
	private final SocioService socioService;



	public EventoController(EventoService eventoService, SocioService socioService) {
		this.eventoService = eventoService;
		this.socioService = socioService;
	}

	@GetMapping
	public ResponseEntity<List<EventoVO>> getAllEventos() {
		List<EventoVO> listaEventos = eventoService.findAll();
		return ResponseEntity.ok(listaEventos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventoVO> getEventoById(@PathVariable Long id) {
		EventoVO vo = eventoService.findById(id);
		return ResponseEntity.ok(vo);
	}

	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<EventoVO> getEventoByNombre(@PathVariable String nombre) {
		EventoVO vo = eventoService.findByNombre(nombre);
		return ResponseEntity.ok(vo);
	}

	@PostMapping
	public ResponseEntity<EventoVO> addEvento(@RequestBody EventoVO vo) {
		EventoVO eventoVO = eventoService.addEvento(vo);
		return ResponseEntity.status(HttpStatus.CREATED).body(eventoVO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EventoVO> updateEvento(@PathVariable Long id, @RequestBody EventoVO vo) {
		if (!id.equals(vo.getId())) {
			throw new IllegalArgumentException("El ID de la URL y del cuerpo no coinciden");
		}
		EventoVO eventoVO = eventoService.updateEvento(vo);
		return ResponseEntity.ok(eventoVO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<EventoVO> deleteEventoById(@PathVariable Long id) {
		boolean borrada = eventoService.deleteEventoById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/asistencia")
	public ResponseEntity<EventoVO> addAsistencia(@RequestBody Map<String, Long> ids) {
	    Long idEvento = ids.get("idEvento");
	    Long idSocio = ids.get("idSocio");

	    if (idEvento == null || idSocio == null) {
	        return ResponseEntity.badRequest().build();
	    }

	    EventoVO evento = eventoService.findById(idEvento);
	    SocioVO socio = socioService.findById(idSocio); 

	    EventoVO actualizado = eventoService.addAsistencia(evento, socio);
	    if (actualizado != null) {
	        return ResponseEntity.ok(actualizado);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/{id}/asistentes")
	public List<AsistenteVO> obtenerAsistentes(@PathVariable Long id) {
	    return eventoService.obtenerAsistentesDelEvento(id);
	}

}
