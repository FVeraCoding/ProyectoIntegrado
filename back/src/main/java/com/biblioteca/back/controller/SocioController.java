package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.back.service.SocioService;
import com.biblioteca.back.vo.RegistroSocioVO;
import com.biblioteca.back.vo.SocioVO;

@RestController
@RequestMapping("/socios")
public class SocioController {

    private final SocioService socioService;

    @Autowired
    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }
    
    // Obtener todos los socios
    @GetMapping("/all")
    public ResponseEntity<List<SocioVO>> obtenerTodosLosSocios() {
        List<SocioVO> socios = socioService.obtenerTodos();
        return ResponseEntity.ok(socios);
    }

    // Obtener socio por ID de usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<SocioVO> obtenerPorIdUsuario(@PathVariable Long idUsuario) {
        SocioVO socioVO = socioService.buscarPorIdUsuario(idUsuario);
        return ResponseEntity.ok(socioVO);
    }
    
    @GetMapping("/socio/{id}")
    public ResponseEntity<SocioVO>obtenerSocioPorId(@PathVariable Long id){
    	SocioVO socioVO = socioService.findById(id);
    	return ResponseEntity.ok(socioVO);
    }

    @PostMapping
    public ResponseEntity<SocioVO> crearSocio(@RequestBody RegistroSocioVO registroVO) {
        SocioVO socioCreado = socioService.crearSocioConUsuario(registroVO);
        return ResponseEntity.ok(socioCreado);
    }
    
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long idUsuario) {
        socioService.eliminarPorIdUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }


}
