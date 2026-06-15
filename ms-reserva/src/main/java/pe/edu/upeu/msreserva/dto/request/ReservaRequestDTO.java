package pe.edu.upeu.msreserva.dto.request;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservaRequestDTO {
    @NotBlank(message="El cliente es obligatorio") @Size(max=120) private String cliente;
    @NotBlank(message="El origen es obligatorio") @Size(max=120) private String origen;
    @NotBlank(message="El destino es obligatorio") @Size(max=120) private String destino;
    @NotNull(message="La fecha y hora es obligatoria") private LocalDateTime fechaHora;
    @NotBlank(message="El estado es obligatorio") @Size(max=30) private String estado;
}
