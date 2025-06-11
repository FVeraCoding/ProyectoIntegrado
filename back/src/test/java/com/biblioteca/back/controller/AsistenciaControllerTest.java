package com.biblioteca.back.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.biblioteca.back.serviceImpl.AsistenciaServiceImpl;
import com.biblioteca.back.vo.AsistenteVO;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AsistenciaControllerTest.MockServiceConfig.class)
public class AsistenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AsistenciaServiceImpl asistenciaService;

    @Test
    @WithMockUser(roles = "SOCIO")
    public void testGetAsistenciasBySocioId() throws Exception {
        // Simular respuesta del servicio
        List<AsistenteVO> asistencias = List.of(
                new AsistenteVO(1L, 100L),
                new AsistenteVO(1L, 101L)
        );

        when(asistenciaService.getAsistenciasSocio(1L)).thenReturn(asistencias);

        mockMvc.perform(get("/asistencia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idEvento").value(100));
    }

    @TestConfiguration
    static class MockServiceConfig {

        @Bean
        @Primary
        public AsistenciaServiceImpl asistenciaService() {
            return Mockito.mock(AsistenciaServiceImpl.class);
        }
    }
}
