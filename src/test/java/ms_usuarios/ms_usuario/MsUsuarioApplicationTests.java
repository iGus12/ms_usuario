package ms_usuarios.ms_usuario;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MsUsuarioApplicationTests {

    @Test
    void debeExistirClasePrincipal() {
        assertThat(MsUsuarioApplication.class).isNotNull();
    }
}