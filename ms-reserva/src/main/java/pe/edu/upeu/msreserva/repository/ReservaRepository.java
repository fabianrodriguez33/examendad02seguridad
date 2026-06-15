package pe.edu.upeu.msreserva.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.msreserva.entity.Reserva;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsByClienteAndDestino(String cliente, String destino);
}
