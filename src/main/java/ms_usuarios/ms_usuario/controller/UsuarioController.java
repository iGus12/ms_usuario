package ms_usuarios.ms_usuario.controller;

import ms_usuarios.ms_usuario.dto.UsuarioDTO; // Importante: estamos usando el DTO
import ms_usuarios.ms_usuario.model.Usuario;
import ms_usuarios.ms_usuario.service.IUsuarioService;
import jakarta.validation.Valid; // Importante: para validar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult; // Importante: para capturar errores
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDTO usuarioDto, BindingResult result) {
        
     
        if (result.hasErrors()) {
            String mensajesError = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            
            return ResponseEntity.badRequest().body("Error en validación: " + mensajesError);
        }

        return ResponseEntity.ok(service.registrar(usuarioDto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> obtenerPerfil(@PathVariable String username) {
        return service.buscarPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}