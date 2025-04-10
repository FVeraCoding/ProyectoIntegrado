package com.biblioteca.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.back.service.UsuarioService;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;

import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/findEmail/{email}")
    public ResponseEntity<?> findEmail(@PathVariable("email") String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio.");
        }

        UsuarioResponseVO usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario con email " + email + " no encontrado.");
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/findId/{id}")
    public ResponseEntity<?> findId(@PathVariable("id") Long id) {
        UsuarioResponseVO usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario con ID " + id + " no encontrado.");
        }

        return ResponseEntity.ok(usuario);
    }
    
    @GetMapping("/findNombre/{nombre}")
    public ResponseEntity<?> findNombre(@PathVariable("nombre") String nombre) {
        UsuarioResponseVO usuario = usuarioService.findByNombre(nombre);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuario con nombre " + nombre + " no encontrado.");
        }

        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UsuarioRequestVO usuarioVO) {
        if (usuarioVO.getId() == null) {
            return ResponseEntity.badRequest().body("El ID del usuario es obligatorio.");
        }

        UsuarioResponseVO usuarioExistente = usuarioService.findById(usuarioVO.getId());
        if (usuarioExistente == null) {
            return ResponseEntity.status(404).body("No se encontró el usuario con ID " + usuarioVO.getId());
        }

        return ResponseEntity.ok(usuarioService.update(usuarioVO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        UsuarioResponseVO usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("No se encontró el usuario con ID " + id);
        }

        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

}
