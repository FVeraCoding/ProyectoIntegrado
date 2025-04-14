package com.biblioteca.back.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.entity.EmpleadoEntity;
import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.repository.EmpleadoRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.repository.UsuarioRepository;
import com.biblioteca.back.service.EmailService;
import com.biblioteca.back.service.PasswordResetTokenService;
import com.biblioteca.back.service.UsuarioService;
import com.biblioteca.back.util.JwtTokenUtil;
import com.biblioteca.back.vo.LoginVO;
import com.biblioteca.back.vo.TokenVO;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVO loginVO) {
        UsuarioResponseVO u = usuarioService.login(loginVO.getNombre(), loginVO.getPassword());
        if (u != null) {
            String token = jwtTokenUtil.generateToken(u.getNombre());
            return ResponseEntity.ok(new TokenVO(token));
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRequestVO usuarioVO) {
        UsuarioResponseVO existente = usuarioService.findByNombre(usuarioVO.getNombre());
        if (existente != null) {
            return ResponseEntity.status(400).body("Este nombre de usuario ya está registrado");
        }

        UsuarioResponseVO nuevo = usuarioService.save(usuarioVO);
        String token = jwtTokenUtil.generateToken(nuevo.getNombre());
        return ResponseEntity.ok(new TokenVO(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> enviarCorreoRecuperacion(@RequestBody Map<String, String> body) {
        String username = body.get("username");

        UsuarioEntity usuario = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar si es socio o empleado
        String correo = null;

        Optional<SocioEntity> socioOpt = socioRepository.findByUsuario(usuario);
        if (socioOpt.isPresent()) {
            correo = socioOpt.get().getCorreoElectronico();
        } else {
            Optional<EmpleadoEntity> empleadoOpt = empleadoRepository.findByUsuario(usuario);
            if (empleadoOpt.isPresent()) {
                correo = empleadoOpt.get().getCorreoElectronico();
            }
        }

        if (correo == null) {
            return ResponseEntity.badRequest().body("No se pudo determinar el correo electrónico del usuario.");
        }

        String token = tokenService.generarToken(username);
        String enlace = "http://localhost:4200/reset-password?token=" + token;

        String mensaje = """
            Hola %s,

            Has solicitado restablecer tu contraseña. Por favor haz clic en el siguiente enlace:

            %s

            Si no solicitaste este cambio, ignora este mensaje.
            """.formatted(username, enlace);

        emailService.enviarCorreo(correo, "Recuperar contraseña", mensaje);

        return ResponseEntity.ok("Se ha enviado un correo para restablecer la contraseña.");
    }

}
