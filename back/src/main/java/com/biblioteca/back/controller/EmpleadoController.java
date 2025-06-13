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

import com.biblioteca.back.service.EmpleadoService;
import com.biblioteca.back.vo.EmpleadoVO;
import com.biblioteca.back.vo.RegistroEmpleadoVO;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    
    @PreAuthorize("hasRole('EMPLEADO')")
    @PostMapping
    public ResponseEntity<EmpleadoVO> crearEmpleado(@RequestBody RegistroEmpleadoVO registroEmpleadoVO) {
        EmpleadoVO empleadoCreado = empleadoService.crearEmpleadoConUsuario(registroEmpleadoVO);
        return ResponseEntity.ok(empleadoCreado);
    }


    // Obtener todos los empleados
    @GetMapping("/all")
    public ResponseEntity<List<EmpleadoVO>> obtenerTodos() {
        return ResponseEntity.ok(empleadoService.obtenerTodos());
    }

    // Obtener empleado por ID de usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<EmpleadoVO> obtenerPorIdUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(empleadoService.buscarPorIdUsuario(idUsuario));
    }
    
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long idUsuario) {
        empleadoService.eliminarPorIdUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
