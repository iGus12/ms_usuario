package ms_usuarios.ms_usuario.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void debeCrearUsuario() {
        Usuario usuario = new Usuario();

        assertThat(usuario).isNotNull();
    }
}