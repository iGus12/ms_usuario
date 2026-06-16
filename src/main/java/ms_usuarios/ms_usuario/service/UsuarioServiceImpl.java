package ms_usuarios.ms_usuario.service;

import ms_usuarios.ms_usuario.dto.UsuarioDTO;
import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario registrar(UsuarioDTO usuarioDto) {
        Usuario usuario = new Usuario();

        usuario.setUsername(usuarioDto.getUsername());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombreCompleto(usuarioDto.getNombreCompleto());

        String rol = usuarioDto.getRol();

        if (rol == null || rol.isBlank()) {
            usuario.setRol("USER");
        } else {
            usuario.setRol(rol.toUpperCase());
        }

        return repository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        String rol = usuario.getRol();

        if (rol == null || rol.isBlank()) {
            usuario.setRol("USER");
        } else {
            usuario.setRol(rol.toUpperCase());
        }

        return repository.save(usuario);
    }
}