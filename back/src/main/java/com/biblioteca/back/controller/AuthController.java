package com.biblioteca.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private JwtTokenUtil jwtTokenUtil;

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
}
