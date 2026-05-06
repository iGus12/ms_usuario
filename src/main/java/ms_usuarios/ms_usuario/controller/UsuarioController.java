package ms_usuarios.ms_usuario.controller;

import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.registrar(usuario));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable String username) {
        return service.buscarPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}