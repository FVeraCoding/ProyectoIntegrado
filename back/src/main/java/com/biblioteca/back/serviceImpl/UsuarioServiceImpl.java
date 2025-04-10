package com.biblioteca.back.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.UsuarioConverter;
import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.enums.RolUsuario;
import com.biblioteca.back.repository.UsuarioRepository;
import com.biblioteca.back.service.UsuarioService;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UsuarioResponseVO save(UsuarioRequestVO vo) {
        UsuarioEntity usuario = UsuarioConverter.toEntity(vo);
        usuario.setPassword(passwordEncoder.encode(vo.getPassword()));
        usuario.setRol(RolUsuario.SOCIO);
        return UsuarioConverter.toResponseVO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResponseVO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioConverter::toResponseVO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseVO findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioConverter::toResponseVO)
                .orElse(null);
    }
    
    @Override
    public UsuarioResponseVO findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre)
                .map(UsuarioConverter::toResponseVO)
                .orElse(null);
    }

    @Override
    public UsuarioResponseVO findById(Long id) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        return usuario.map(UsuarioConverter::toResponseVO).orElse(null);
    }

    @Override
    public UsuarioResponseVO update(UsuarioRequestVO vo) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(vo.getId());
        if (usuarioOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();
            usuario.setEmail(vo.getEmail());
            usuario.setNombre(vo.getNombre());
            if (vo.getPassword() != null && !vo.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(vo.getPassword()));
            }
            return UsuarioConverter.toResponseVO(usuarioRepository.save(usuario));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseVO login(String nombre, String password) {
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByNombre(nombre);
        if (usuarioOpt.isPresent()) {
            UsuarioEntity usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return UsuarioConverter.toResponseVO(usuario);
            }
        }
        return null;
    }



}
