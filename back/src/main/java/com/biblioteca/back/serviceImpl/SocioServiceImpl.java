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
    public SocioServiceImpl(SocioRepository socioRepository,
                            UsuarioRepository usuarioRepository,
                            SocioConverter socioConverter,
                            PasswordEncoder passwordEncoder) {
        this.socioRepository = socioRepository;
        this.usuarioRepository = usuarioRepository;
        this.socioConverter = socioConverter;
        this.passwordEncoder = passwordEncoder;;
    }

    @Override
    public SocioVO buscarPorIdUsuario(Long idUsuario) {
        SocioEntity socio = socioRepository.findByUsuario_Id(idUsuario)
            .orElseThrow(() -> new RuntimeException("No se encontró un socio para el usuario con ID: " + idUsuario));

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
        return socios.stream()
                     .map(socioConverter::toVO)
                     .toList();
    }


}
