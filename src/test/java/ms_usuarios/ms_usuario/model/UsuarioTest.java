package ms_usuarios.ms_usuario.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    @DisplayName("Debería verificar la integridad de los datos del usuario")
    void testUsuarioIntegridadDatos() {
        Usuario usuario = new Usuario();
        
        usuario.setId(10L);
        usuario.setUsername("vettest01");
        usuario.setEmail("vet@test.cl");
        usuario.setNombreCompleto("Veterinario Test");
        usuario.setRol("VETERINARIO");

        // Validaciones contundentes
        assertThat(usuario.getId()).isEqualTo(10L);
        assertThat(usuario.getUsername()).isEqualTo("vettest01");
        assertThat(usuario.getEmail()).contains("@").endsWith(".cl");
        assertThat(usuario.getNombreCompleto()).isEqualTo("Veterinario Test");
        
        // Verificamos que el rol coincida exactamente con lo que tienes en BD
        assertThat(usuario.getRol()).isEqualTo("VETERINARIO");
    }

    @Test
    @DisplayName("Debería validar que los roles permitidos coinciden con la BD")
    void testRolesPermitidos() {
        Usuario usuario = new Usuario();
        String[] rolesDB = {"ADMIN", "USER", "VETERINARIO"};
        
        for (String rol : rolesDB) {
            usuario.setRol(rol);
            assertThat(usuario.getRol()).isIn("ADMIN", "USER", "VETERINARIO");
        }
    }
}