package pe.edu.upeu.msreserva.dto.response;
import lombok.*;
import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservaResponseDTO {
    private Long id;
    private String cliente;
    private String origen;
    private String destino;
    private LocalDateTime fechaHora;
    private String estado;
}
