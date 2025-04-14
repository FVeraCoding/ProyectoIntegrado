package com.biblioteca.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Crear empleado con usuario
    @PostMapping
    public ResponseEntity<EmpleadoVO> crearEmpleado(@RequestBody RegistroEmpleadoVO registroVO) {
        EmpleadoVO empleadoCreado = empleadoService.crearEmpleadoConUsuario(registroVO);
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
}
