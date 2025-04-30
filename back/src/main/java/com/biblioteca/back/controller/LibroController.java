package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.service.LibroService;
import com.biblioteca.back.vo.LibroVO;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
    
    @GetMapping
    public ResponseEntity<List<LibroVO>> obtenerTodos() {
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/buscar-google")
    public ResponseEntity<List<LibroVO>> buscarGoogleBooks(@RequestParam String query) {
        return ResponseEntity.ok(libroService.sincronizarDesdeGoogleBooks(query));
    }

    @PostMapping
    public ResponseEntity<LibroVO> guardarLibro(@RequestBody LibroVO libroVO) {
        return ResponseEntity.ok(libroService.guardarLibro(libroVO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LibroVO> obtenerLibroPorId(@PathVariable Long id) {
        LibroVO libro = libroService.findById(id);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(libro);
    }

}

    

