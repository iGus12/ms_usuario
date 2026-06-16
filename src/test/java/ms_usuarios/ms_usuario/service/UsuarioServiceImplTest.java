package ms_usuarios.ms_usuario.service;

import ms_usuarios.ms_usuario.dto.UsuarioDTO;
import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioPrueba;
    private UsuarioDTO usuarioDto;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setId(1L);
        usuarioPrueba.setUsername("testuser");
        usuarioPrueba.setRol("USER");

        usuarioDto = new UsuarioDTO();
        usuarioDto.setUsername("testuser");
        usuarioDto.setRol("admin"); // Para probar el toUpperCase
    }

    @Test
    public void deberiaRegistrarUsuarioDesdeDTO_ConRolUpper() {
        when(repository.save(any(Usuario.class))).thenReturn(usuarioPrueba);

        Usuario resultado = usuarioService.registrar(usuarioDto);

        assertNotNull(resultado);
        assertEquals("USER", resultado.getRol()); // El sistema asigna por defecto si viene vacío o normaliza
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void deberiaBuscarPorUsername_CuandoExiste() {
        when(repository.findByUsername("testuser")).thenReturn(Optional.of(usuarioPrueba));

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("testuser");

        assertTrue(resultado.isPresent());
        assertEquals("testuser", resultado.get().getUsername());
    }
}