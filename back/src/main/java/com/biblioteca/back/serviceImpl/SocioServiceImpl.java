package com.biblioteca.back.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.SocioConverter;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.enums.RolUsuario;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.repository.UsuarioRepository;
import com.biblioteca.back.service.SocioService;
import com.biblioteca.back.vo.RegistroSocioVO;
import com.biblioteca.back.vo.SocioVO;

@Service
public class SocioServiceImpl implements SocioService {

	private final SocioRepository socioRepository;
	private final UsuarioRepository usuarioRepository;
	private final SocioConverter socioConverter;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SocioServiceImpl(SocioRepository socioRepository, UsuarioRepository usuarioRepository,
			SocioConverter socioConverter, PasswordEncoder passwordEncoder) {
		this.socioRepository = socioRepository;
		this.usuarioRepository = usuarioRepository;
		this.socioConverter = socioConverter;
		this.passwordEncoder = passwordEncoder;
		;
	}

	@Override
	public SocioVO buscarPorIdUsuario(Long idUsuario) {
		SocioEntity socio = socioRepository.findByUsuarioId(idUsuario).orElseThrow(
				() -> new RuntimeException("No se encontró un socio para el usuario con ID: " + idUsuario));

		return socioConverter.toVO(socio);
	}

	@Override
	public SocioVO crearSocioConUsuario(RegistroSocioVO registroVO) {
		if (usuarioRepository.findByNombre(registroVO.getUsername()).isPresent()) {
			throw new RuntimeException("El nombre de usuario ya existe");
		}

		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setNombre(registroVO.getUsername());
		usuario.setPassword(passwordEncoder.encode(registroVO.getPassword()));
		usuario.setRol(RolUsuario.SOCIO);

		UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);

		SocioEntity socio = new SocioEntity();
		socio.setNombre(registroVO.getNombre());
		socio.setApellidos(registroVO.getApellidos());
		socio.setFechaNacimiento(registroVO.getFechaNacimiento());
		socio.setCorreoElectronico(registroVO.getCorreoElectronico());
		socio.setTelefono(registroVO.getTelefono());
		socio.setDireccion(registroVO.getDireccion());
		socio.setFechaAlta(LocalDate.now());
		socio.setUsuario(usuarioGuardado);

		SocioEntity socioGuardado = socioRepository.save(socio);

		return socioConverter.toVO(socioGuardado);
	}

	@Override
	public List<SocioVO> obtenerTodos() {
		List<SocioEntity> socios = socioRepository.findAll();
		return socios.stream().map(socioConverter::toVO).toList();
	}

	@Override
	public void eliminarPorIdUsuario(Long idUsuario) {
	    SocioEntity socio = socioRepository.findByUsuarioId(idUsuario)
	        .orElseThrow(() -> new RuntimeException("Socio no encontrado con ID de usuario: " + idUsuario));

	    if (socio.getListaClubes() != null) {
	        socio.getListaClubes().forEach(club -> club.getSocios().remove(socio));
	        socio.getListaClubes().clear();
	        socioRepository.save(socio);
	    }

	    socioRepository.delete(socio);
	    usuarioRepository.deleteById(idUsuario);
	}


	@Override
	public SocioVO findById(Long id) {
		SocioEntity entity = socioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Socio no encontrado"));
		SocioVO vo = socioConverter.toVO(entity);
		return vo;
	}

	@Override
	public void eliminarPorIdSocio(Long idSocio) {
	    SocioEntity socio = socioRepository.findById(idSocio)
	        .orElseThrow(() -> new RuntimeException("Socio no encontrado con ID: " + idSocio));

	    if (socio.getListaClubes() != null) {
	        socio.getListaClubes().forEach(club -> club.getSocios().remove(socio));
	        socio.getListaClubes().clear();
	        socioRepository.save(socio);
	    }

	    socioRepository.delete(socio);
	}



	@Override
	public void updateSocioById(SocioVO vo) {
	    SocioEntity socio = socioRepository.findById(vo.getId())
	            .orElseThrow(() -> new RuntimeException("Socio no encontrado con ID: " + vo.getId()));

	    socio.setNombre(vo.getNombre());
	    socio.setApellidos(vo.getApellidos());
	    socio.setCorreoElectronico(vo.getCorreoElectronico());
	    socio.setFechaNacimiento(vo.getFechaNacimiento());
	    socio.setTelefono(vo.getTelefono());
	    socio.setDireccion(vo.getDireccion());

	    socioRepository.save(socio);
	}


}
