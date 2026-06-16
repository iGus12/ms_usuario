package ms_usuarios.ms_usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms_usuarios.ms_usuario.dto.UsuarioDTO;
import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.service.IUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- ¡La llave que abre la puerta!
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /registrar - Debería registrar un nuevo usuario real (User ca.vasquezl)")
    void testRegistrarExitoso() throws Exception {
        // Datos reales de tu BD (ID 9)
        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsername("carla.vasquez");
        dto.setPassword("Carla1234");
        dto.setEmail("ca.vasquezl@duocuc.cl");
        dto.setNombreCompleto("Carla Vasquez");
        dto.setRol("USER");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setUsername("carla.vasquez");

        when(service.registrar(any(UsuarioDTO.class))).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("carla.vasquez"));
    }

    @Test
    @DisplayName("POST /registrar - Debería fallar con datos inválidos (Ej: rol inventado)")
    void testRegistrarFallido_Validacion() throws Exception {
        UsuarioDTO dtoInvalido = new UsuarioDTO();
        dtoInvalido.setUsername("user");
        dtoInvalido.setPassword("Pass1234");
        dtoInvalido.setRol("INVITADO"); // Este rol NO existe en tu BD

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest()); 
    }

    @Test
    @DisplayName("GET /obtenerPerfil - Debería retornar datos del admin (Lucía Puentes)")
    void testObtenerPerfilExistente() throws Exception {
        // Datos reales de tu BD (ID 7)
        Usuario usuario = new Usuario();
        usuario.setUsername("luc.puentes");
        usuario.setRol("ADMIN");
        
        when(service.buscarPorUsername("luc.puentes")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/luc.puentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("luc.puentes"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }
}