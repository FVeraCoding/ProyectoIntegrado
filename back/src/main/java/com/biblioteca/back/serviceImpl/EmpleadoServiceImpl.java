package com.biblioteca.back.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.back.converter.EmpleadoConverter;
import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.enums.RolUsuario;
import com.biblioteca.back.repository.EmpleadoRepository;
import com.biblioteca.back.repository.UsuarioRepository;
import com.biblioteca.back.service.EmpleadoService;
import com.biblioteca.back.vo.EmpleadoVO;
import com.biblioteca.back.vo.RegistroEmpleadoVO;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpleadoConverter empleadoConverter;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoServiceImpl(
        EmpleadoRepository empleadoRepository,
        UsuarioRepository usuarioRepository,
        EmpleadoConverter empleadoConverter,
        PasswordEncoder passwordEncoder
    ) {
        this.empleadoRepository = empleadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.empleadoConverter = empleadoConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmpleadoVO> obtenerTodos() {
        return empleadoRepository.findAll().stream()
            .map(empleadoConverter::toVO)
            .toList();
    }

    @Override
    public EmpleadoVO buscarPorIdUsuario(Long idUsuario) {
        return empleadoRepository.findByUsuarioId(idUsuario)
                .map(empleadoConverter::toVO)
                .orElse(null);
    }

    @Override
    public EmpleadoVO crearEmpleadoConUsuario(RegistroEmpleadoVO registroVO) {
        if (usuarioRepository.findByNombre(registroVO.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(registroVO.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroVO.getPassword()));
        usuario.setRol(RolUsuario.EMPLEADO);

        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);

        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setNombre(registroVO.getNombre());
        empleado.setApellidos(registroVO.getApellidos());
        empleado.setFechaNacimiento(registroVO.getFechaNacimiento());
        empleado.setCorreoElectronico(registroVO.getCorreoElectronico());
        empleado.setTelefono(registroVO.getTelefono());
        empleado.setDireccion(registroVO.getDireccion());
        empleado.setFechaAlta(LocalDate.now());
        empleado.setCargo(registroVO.getCargo());
        empleado.setUsuario(usuarioGuardado);

        EmpleadoEntity empleadoGuardado = empleadoRepository.save(empleado);
        return empleadoConverter.toVO(empleadoGuardado);
    }
    
    @Override
    public void eliminarPorIdUsuario(Long idUsuario) {
        EmpleadoEntity empleado = empleadoRepository.findByUsuarioId(idUsuario)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID de usuario: " + idUsuario));
        
        empleadoRepository.delete(empleado);
        usuarioRepository.deleteById(idUsuario);
    }

	@Override
	public EmpleadoVO findById(Long id) {
		EmpleadoEntity entity = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("No se ha encontrado el empleado con el id especificado"));
		EmpleadoVO vo = empleadoConverter.toVO(entity);
		return vo;
	}

}
