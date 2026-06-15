package pe.edu.upeu.authservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    @NotBlank @Size(min=3,max=100) private String fullName;
    @NotBlank @Pattern(regexp="^\\d{8}$") private String dni;
    @NotBlank @Email private String email;
    @NotBlank @Pattern(regexp="^\\d{9}$") private String universityCode;
    @NotBlank @Pattern(regexp="^9\\d{8}$") private String phone;
    @NotBlank @Size(min=3,max=50) private String username;
    @NotBlank @Size(min=6) private String password;
}
