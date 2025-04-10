package com.biblioteca.back.converter;

import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;

public class UsuarioConverter {

    public static UsuarioEntity toEntity(UsuarioRequestVO vo) {
        if (vo == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setEmail(vo.getEmail());
        e.setPassword(vo.getPassword());
        e.setNombre(vo.getNombre());
 
        return e;
    }

    public static UsuarioResponseVO toResponseVO(UsuarioEntity e) {
        if (e == null) return null;
        return new UsuarioResponseVO(
            e.getId(),
            e.getEmail(),
            e.getNombre(),
            e.getRol() 
        );
    }
}
