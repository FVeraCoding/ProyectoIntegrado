package com.biblioteca.back.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.ReservaConverter;
import com.biblioteca.back.entity.EjemplarEntity;
import com.biblioteca.back.entity.LibroEntity;
import com.biblioteca.back.entity.ReservaEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.EjemplarRepository;
import com.biblioteca.back.repository.ReservaRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.service.ReservaService;
import com.biblioteca.back.vo.ReservaVO;
import com.biblioteca.backend.id.ReservaId;

@Service
public class ReservaServiceImpl implements ReservaService {

	private final EjemplarRepository ejemplarRepository;
	private final SocioRepository socioRepository;
	private final ReservaRepository reservaRepository;
	private final ReservaConverter reservaConverter;

	@Autowired
	public ReservaServiceImpl(
	    ReservaRepository reservaRepository,
	    ReservaConverter reservaConverter,
	    EjemplarRepository ejemplarRepository,
	    SocioRepository socioRepository
	) {
	    this.reservaRepository = reservaRepository;
	    this.reservaConverter = reservaConverter;
	    this.ejemplarRepository = ejemplarRepository;
	    this.socioRepository = socioRepository;
	}


	@Override
	public boolean addReserva(ReservaVO vo) {
	    if (vo != null) {
	        EjemplarEntity ejemplar = ejemplarRepository.findById(vo.getEjemplarID())
	            .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado"));
	        LibroEntity libro = ejemplar.getLibro();
	        vo.setNombreLibro(libro.getTitulo());

	        SocioEntity socio = socioRepository.findById(vo.getSocioID())
	            .orElseThrow(() -> new RuntimeException("Socio no encontrado"));
	        vo.setNombreSocio(socio.getNombre()); // o getNombreCompleto(), según el modelo

	        ReservaEntity entity = reservaConverter.toEntity(vo);
	        entity.setFechaInicio(LocalDate.now());
	        entity.setFechaFin(LocalDate.now().plusDays(14));

	        reservaRepository.save(entity);
	        return true;
	    }
	    return false;
	}


	@Override
	public ReservaVO findReservaById(ReservaId reservaId) {

		ReservaEntity entity = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new RuntimeException("No se ha encontrado una reserva con el id especificado"));
		ReservaVO vo = reservaConverter.toVO(entity);

		return vo;
	}

	@Override
	public List<ReservaVO> findReservasBySocioId(Long id) {

		List<ReservaEntity> reservasSocio = reservaRepository.findByIdSocioId(id);

		List<ReservaVO> listaReservas = reservasSocio.stream().map(reservaConverter::toVO).toList();

		return listaReservas;
	}

	@Override
	public void updateReserva(ReservaVO vo) {

		if (reservaRepository.existsById(vo.getReservaID())) {

			ReservaEntity entity = reservaConverter.toEntity(vo);
			reservaRepository.save(entity);
		}

	}

	@Override
	public boolean deleteReservaById(ReservaId id) {

		ReservaEntity entity = reservaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se ha encontrado la reserva con el id especificado"));

		reservaRepository.delete(entity);
		return true;
	}

}
