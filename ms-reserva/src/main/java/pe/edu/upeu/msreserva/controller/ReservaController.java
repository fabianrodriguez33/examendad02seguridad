package pe.edu.upeu.msreserva.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.msreserva.dto.request.ReservaRequestDTO;
import pe.edu.upeu.msreserva.dto.response.ReservaResponseDTO;
import pe.edu.upeu.msreserva.service.ReservaService;
import java.util.List;
@RestController @RequestMapping("/api/reservas") @RequiredArgsConstructor
public class ReservaController {
    private final ReservaService service;
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> create(@Valid @RequestBody ReservaRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ReservaResponseDTO>> getAll(){ return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> getById(@PathVariable Long id){ return ResponseEntity.ok(service.getById(id)); }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> update(@PathVariable Long id,@Valid @RequestBody ReservaRequestDTO dto){ return ResponseEntity.ok(service.update(id,dto)); }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }
}
