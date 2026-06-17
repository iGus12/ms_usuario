package ms_usuarios.ms_usuario.service;

import ms_usuarios.ms_usuario.dto.UsuarioDTO;
import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private static final String AUTH_REGISTRAR_URL = "http://localhost:8082/api/auth/registrar";

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Usuario registrar(UsuarioDTO usuarioDto) {
        String passwordPlano = usuarioDto.getPassword();
        String rolNormalizado = normalizarRol(usuarioDto.getRol());

        Usuario usuario = new Usuario();

        usuario.setUsername(usuarioDto.getUsername());
        usuario.setPassword(passwordEncoder.encode(passwordPlano));
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombreCompleto(usuarioDto.getNombreCompleto());
        usuario.setRol(rolNormalizado);

        Usuario usuarioGuardado = repository.save(usuario);

        sincronizarConAuth(
                usuarioDto.getUsername(),
                passwordPlano,
                usuarioDto.getEmail(),
                usuarioDto.getNombreCompleto(),
                rolNormalizado
        );

        return usuarioGuardado;
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        String passwordPlano = usuario.getPassword();
        String rolNormalizado = normalizarRol(usuario.getRol());

        usuario.setPassword(passwordEncoder.encode(passwordPlano));
        usuario.setRol(rolNormalizado);

        Usuario usuarioGuardado = repository.save(usuario);

        sincronizarConAuth(
                usuario.getUsername(),
                passwordPlano,
                usuario.getEmail(),
                usuario.getNombreCompleto(),
                rolNormalizado
        );

        return usuarioGuardado;
    }

    private String normalizarRol(String rol) {
        if (rol == null || rol.isBlank()) {
            return "USER";
        }

        String rolNormalizado = rol.trim().toUpperCase();

        if (rolNormalizado.equals("VETERINARIA")) {
            return "VETERINARIO";
        }

        return rolNormalizado;
    }

    private void sincronizarConAuth(
            String username,
            String passwordPlano,
            String email,
            String nombreCompleto,
            String rol
    ) {
        Map<String, Object> usuarioAuth = new HashMap<>();

        usuarioAuth.put("username", username);
        usuarioAuth.put("password", passwordPlano);
        usuarioAuth.put("email", email);
        usuarioAuth.put("nombreCompleto", nombreCompleto);
        usuarioAuth.put("rol", rol);

        try {
            restTemplate.postForObject(
                    AUTH_REGISTRAR_URL,
                    usuarioAuth,
                    Object.class
            );

            System.out.println("Usuario sincronizado con ms_login: " + username + " - Rol: " + rol);
        } catch (Exception error) {
            System.out.println("Error al sincronizar usuario con ms_login: " + error.getMessage());

            throw new RuntimeException(
                    "El usuario se guardó en ms_usuarios, pero no se pudo sincronizar con ms_login. " +
                    "Revisa que el microservicio Auth esté encendido en el puerto 8082."
            );
        }
    }
}