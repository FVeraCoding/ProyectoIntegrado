package com.biblioteca.back.controller;

import java.util.List;

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

import com.biblioteca.back.service.ReservaService;
import com.biblioteca.back.vo.ReservaVO;
import com.biblioteca.backend.id.ReservaId;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private ReservaService reservaService;

	public ReservaController(ReservaService reservaService) {
		super();
		this.reservaService = reservaService;
	}

	@GetMapping("/{socioId}/{ejemplarId}")
	public ResponseEntity<ReservaVO> findReservaByID(@PathVariable Long socioId, @PathVariable Long ejemplarId) {

		ReservaId id = new ReservaId(socioId, ejemplarId);
		return ResponseEntity.ok(reservaService.findReservaById(id));
	}

	@GetMapping("/{socioId}")
	public ResponseEntity<List<ReservaVO>> findReservasBySocioId(@PathVariable Long socioId) {
		return ResponseEntity.ok(reservaService.findReservasBySocioId(socioId));
	}

	@PostMapping("/crear-reserva")
	public ResponseEntity<Boolean> crearReserva(@RequestBody ReservaVO reserva) {

		boolean creada = reservaService.addReserva(reserva);
		return ResponseEntity.status(creada ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(creada);

	}

	@PutMapping("/{socioId}/{ejemplarId}")
	public ResponseEntity<Void> actualizarReserva(@PathVariable Long socioId, @PathVariable Long ejemplarId,
			@RequestBody ReservaVO vo) {
		reservaService.updateReserva(vo);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{socioId}/{ejemplarId}")
	public ResponseEntity<Void> borrarReserva(@PathVariable Long socioId, @PathVariable Long ejemplarId) {
		ReservaId id = new ReservaId(socioId, ejemplarId);

		boolean borrada = reservaService.deleteReservaById(id);
		
		return borrada ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
