package com.biblioteca.back.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.ClubConverter;
import com.biblioteca.back.converter.SocioConverter;
import com.biblioteca.back.entity.ClubEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.repository.ClubRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.service.ClubService;
import com.biblioteca.back.vo.ClubVO;

@Service
public class ClubServiceImpl implements ClubService{

	private ClubRepository repo;
	private ClubConverter converter;
	private SocioRepository socioRepo;
	private SocioConverter socioConverter;
	
	
	@Autowired
	public ClubServiceImpl(ClubRepository repo, ClubConverter converter, SocioRepository socioRepo,
			SocioConverter socioConverter) {
		super();
		this.repo = repo;
		this.converter = converter;
		this.socioRepo = socioRepo;
		this.socioConverter = socioConverter;
	}
	

	@Override
	public ClubVO findClubById(Long id) {
		ClubEntity entity = repo.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el club con el id: "+id));
		ClubVO vo = converter.toVO(entity);
		return vo;
	}



	@Override
	public List<ClubVO> findClubsByEmpleadoOrganizadorId(Long id) {
		return repo.findByEmpleadoOrganizadorId(id).stream().map(converter::toVO).toList();
	}

	@Override
	public ClubVO addClub(ClubVO vo) {
		
		if(vo != null) {
			ClubEntity nuevo = repo.save(converter.toEntity(vo));
			return converter.toVO(nuevo);
		}
		
		return null;
	}

	@Override
	public boolean deleteClub(ClubVO vo) {

		ClubEntity entity = repo.findById(vo.getId()).orElseThrow(() -> new RuntimeException("No se ha encontrado el club introducido"));
		repo.delete(entity);
		return true;
	}

	@Override
	public ClubVO updateClub(ClubVO vo) {
		if (vo == null || vo.getId() == null) {
			throw new IllegalArgumentException("ClubVO o su ID no pueden ser nulos");
		}
		
		ClubEntity existente = repo.findById(vo.getId())
			.orElseThrow(() -> new RuntimeException("No se ha encontrado el club con ID: " + vo.getId()));
		
		ClubEntity actualizado = converter.toEntity(vo);
		actualizado.setId(existente.getId()); 
		repo.save(actualizado);
		
		return converter.toVO(actualizado);
	}



	@Override
	public List<ClubVO> findAll() {
		List<ClubVO> allClubs = repo.findAll().stream().map(converter::toVO).toList();		
		return allClubs;
	}



	@Override
	public List<ClubVO> findClubsBySociosId(Long id) {
		return repo.findBySociosId(id).stream().map(converter::toVO).toList();
	}



	@Override
	public ClubVO addSocio(Long idClub, Long idSocio) {
		ClubEntity clubEntity = repo.findById(idClub).orElseThrow(() -> new RuntimeException("No se ha encontrado el club especificado"));
		SocioEntity socioEntity = socioRepo.findById(idSocio).orElseThrow(() -> new RuntimeException("No se ha encontrado el socio especificado"));
		
		if(!clubEntity.getSocios().contains(socioEntity)) {
			clubEntity.getSocios().add(socioEntity);
		}
		
		ClubVO actualizado = converter.toVO(clubEntity);
		
		this.updateClub(actualizado);
		
		return actualizado;
	}


	@Override
	public ClubVO retirarSocio(Long idClub, Long idSocio) {
	    ClubEntity club = repo.findById(idClub).orElse(null);
	    SocioEntity socio = socioRepo.findById(idSocio).orElse(null);


	    if (club == null || socio == null) return null;

	    boolean eliminado = club.getSocios().removeIf(s -> s.getId().equals(socio.getId()));

	    ClubEntity actualizado = repo.save(club);
	    return converter.toVO(actualizado);
	}
	
	
}
