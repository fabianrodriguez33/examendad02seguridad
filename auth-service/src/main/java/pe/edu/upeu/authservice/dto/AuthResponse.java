package pe.edu.upeu.authservice.dto;
import lombok.*;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String token;
    private List<String> roles;
    private String username;
    private String message;
}
