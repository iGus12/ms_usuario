package ms_usuarios.ms_usuario.model;
import jakarta.persistence.*;

@Entity 
public class Usuario {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;          
    private String nombreCompleto; 
    private String rol;

    public Usuario() {}

    
    public Usuario(Long id, String username, String password, String email, String nombreCompleto, String rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

   
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; }

    public String getNombreCompleto() { return nombreCompleto; } 
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}