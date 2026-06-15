package pe.edu.upeu.msreserva.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.msreserva.dto.request.ReservaRequestDTO;
import pe.edu.upeu.msreserva.dto.response.ReservaResponseDTO;
import pe.edu.upeu.msreserva.entity.Reserva;
import pe.edu.upeu.msreserva.exception.ReservaNotFoundException;
import pe.edu.upeu.msreserva.repository.ReservaRepository;
import java.util.List;
@Service @RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository repo;
    @Transactional
    public ReservaResponseDTO create(ReservaRequestDTO dto){
        return toResponse(repo.save(toEntity(dto)));
    }
    @Transactional(readOnly=true)
    public ReservaResponseDTO getById(Long id){ return toResponse(getEntity(id)); }
    @Transactional(readOnly=true)
    public List<ReservaResponseDTO> getAll(){ return repo.findAll().stream().map(this::toResponse).toList(); }
    @Transactional
    public ReservaResponseDTO update(Long id, ReservaRequestDTO dto){
        Reserva r=getEntity(id);
        r.setCliente(dto.getCliente()); r.setOrigen(dto.getOrigen());
        r.setDestino(dto.getDestino()); r.setFechaHora(dto.getFechaHora()); r.setEstado(dto.getEstado());
        return toResponse(repo.save(r));
    }
    @Transactional
    public void delete(Long id){
        if(!repo.existsById(id)) throw new ReservaNotFoundException("Reserva no encontrada: "+id);
        repo.deleteById(id);
    }
    private Reserva getEntity(Long id){ return repo.findById(id).orElseThrow(()->new ReservaNotFoundException("Reserva no encontrada: "+id)); }
    private Reserva toEntity(ReservaRequestDTO d){ return Reserva.builder().cliente(d.getCliente()).origen(d.getOrigen()).destino(d.getDestino()).fechaHora(d.getFechaHora()).estado(d.getEstado()).build(); }
    private ReservaResponseDTO toResponse(Reserva e){ return ReservaResponseDTO.builder().id(e.getId()).cliente(e.getCliente()).origen(e.getOrigen()).destino(e.getDestino()).fechaHora(e.getFechaHora()).estado(e.getEstado()).build(); }
}
