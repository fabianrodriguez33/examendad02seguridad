package pe.edu.upeu.msreserva.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="reservas") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reserva {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,length=120) private String cliente;
    @Column(nullable=false,length=120) private String origen;
    @Column(nullable=false,length=120) private String destino;
    @Column(nullable=false) private LocalDateTime fechaHora;
    @Column(nullable=false,length=30) private String estado;
}
