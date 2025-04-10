package com.biblioteca.back.exception;

public class CodigoDuplicadoException extends RuntimeException {
    
    public CodigoDuplicadoException(String codigo) {
        super("Ya existe un expediente con el código: " + codigo);
    }
}
