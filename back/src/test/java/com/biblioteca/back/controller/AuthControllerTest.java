package com.biblioteca.back.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.biblioteca.back.entity.SocioEntity;
import com.biblioteca.back.entity.UsuarioEntity;
import com.biblioteca.back.enums.RolUsuario;
import com.biblioteca.back.repository.EmpleadoRepository;
import com.biblioteca.back.repository.SocioRepository;
import com.biblioteca.back.repository.UsuarioRepository;
import com.biblioteca.back.service.EmailService;
import com.biblioteca.back.service.PasswordResetTokenService;
import com.biblioteca.back.service.UsuarioService;
import com.biblioteca.back.util.JwtTokenUtil;
import com.biblioteca.back.vo.LoginVO;
import com.biblioteca.back.vo.UsuarioRequestVO;
import com.biblioteca.back.vo.UsuarioResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UsuarioService usuarioService;
    @MockBean private UsuarioRepository usuarioRepository;
    @MockBean private JwtTokenUtil jwtTokenUtil;
    @MockBean private SocioRepository socioRepository;
    @MockBean private EmpleadoRepository empleadoRepository;
    @MockBean private PasswordResetTokenService tokenService;
    @MockBean private EmailService emailService;
    @MockBean private PasswordEncoder passwordEncoder;

    private final ObjectMapper mapper = new ObjectMapper();
    
    

    @Test
    void login_exitoso() throws Exception {
        LoginVO loginVO = new LoginVO("usuario", "claveSegura");
        UsuarioResponseVO response = new UsuarioResponseVO(1L, "usuario", RolUsuario.SOCIO);

        when(usuarioService.login("usuario", "claveSegura")).thenReturn(response);
        when(socioRepository.findByUsuarioId(1L)).thenReturn(Optional.of(new SocioEntity()));
        when(jwtTokenUtil.generateToken(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString()))
            .thenReturn("token-prueba");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginVO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-prueba"));
    }

    @Test
    void register_usuarioNuevo() throws Exception {
        UsuarioRequestVO nuevoUsuario = new UsuarioRequestVO("claveSegura", "nuevoUsuario", null);
        UsuarioResponseVO guardado = new UsuarioResponseVO(2L, "nuevoUsuario", RolUsuario.SOCIO);

        when(usuarioService.findByNombre("nuevoUsuario")).thenReturn(null);
        when(usuarioService.save(Mockito.any())).thenReturn(guardado);
        when(jwtTokenUtil.generateToken(2L, "nuevoUsuario", "SOCIO")).thenReturn("token-nuevo");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isOk());
    }

    @Test
    void forgotPassword_socio() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre("usuario");
        SocioEntity socio = new SocioEntity();
        socio.setUsuario(usuario);

        when(socioRepository.findByCorreoElectronico("test@mail.com")).thenReturn(Optional.of(socio));
        when(tokenService.generarToken("usuario")).thenReturn("token-reset");

        mockMvc.perform(post("/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("email", "test@mail.com"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha enviado un correo para restablecer la contraseña."));
    }

    @Test
    void resetPassword_exitoso() throws Exception {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre("usuario");

        when(tokenService.getUsernameFromToken("token123")).thenReturn("usuario");
        when(usuarioRepository.findByNombre("usuario")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("nuevaClave")).thenReturn("hash");

        mockMvc.perform(post("/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("token", "token123", "nuevaPassword", "nuevaClave"))))
                .andExpect(status().isOk())
                .andExpect(content().string("Contraseña actualizada correctamente."));
    }

}
