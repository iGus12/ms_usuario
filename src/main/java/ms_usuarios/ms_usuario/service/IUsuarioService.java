package ms_usuarios.ms_usuario.service;

import ms_usuarios.ms_usuario.dto.UsuarioDTO; 
import ms_usuarios.ms_usuario.model.Usuario;
import java.util.Optional;

public interface IUsuarioService {
    
    
    Usuario registrar(Usuario usuario);
    
    Optional<Usuario> buscarPorUsername(String username);

    Usuario registrar(UsuarioDTO usuarioDto);
}