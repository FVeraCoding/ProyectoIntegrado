package com.biblioteca.back.service;

import java.util.List;

import com.biblioteca.back.vo.ClubVO;
import com.biblioteca.back.vo.SocioVO;

public interface ClubService {

	ClubVO findClubById(Long id);
	List<ClubVO> findClubsByEmpleadoOrganizadorId(Long id);
	List<ClubVO> findClubsBySociosId(Long id);
	ClubVO addClub(ClubVO vo);
	boolean deleteClub(ClubVO vo);
	ClubVO updateClub(ClubVO vo);
	List<ClubVO> findAll();
	ClubVO addSocio(Long idClub, Long idSocio);
	ClubVO retirarSocio(Long idClub, Long idSocio);
	List<SocioVO> obtenerSociosDelClub(Long idClub);

}
