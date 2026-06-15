package pe.edu.upeu.authservice.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.authservice.dto.*;
import pe.edu.upeu.authservice.entity.User;
import pe.edu.upeu.authservice.repository.UserRepository;
import pe.edu.upeu.authservice.security.JwtUtil;
import java.util.*;
@RestController @RequestMapping({"/auth","/api/auth"}) @RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register") @Transactional
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        if(userRepository.existsByDni(req.getDni()))
            return ResponseEntity.badRequest().body(new AuthResponse(null,null,null,"El DNI ya está registrado"));
        if(userRepository.existsByEmail(req.getEmail()))
            return ResponseEntity.badRequest().body(new AuthResponse(null,null,null,"El email ya está registrado"));
        if(userRepository.existsByUsername(req.getUsername()))
            return ResponseEntity.badRequest().body(new AuthResponse(null,null,null,"El username ya existe"));
        if(userRepository.existsByUniversityCode(req.getUniversityCode()))
            return ResponseEntity.badRequest().body(new AuthResponse(null,null,null,"Código universitario ya registrado"));
        if(userRepository.existsByPhone(req.getPhone()))
            return ResponseEntity.badRequest().body(new AuthResponse(null,null,null,"Celular ya registrado"));
        User user=User.builder()
            .fullName(req.getFullName()).dni(req.getDni()).email(req.getEmail())
            .universityCode(req.getUniversityCode()).phone(req.getPhone())
            .username(req.getUsername()).password(passwordEncoder.encode(req.getPassword()))
            .roles(Set.of("USER")).enabled(true).build();
        userRepository.save(user);
        String token=jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token,new ArrayList<>(user.getRoles()),user.getUsername(),"Registro exitoso"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
        } catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null,null,null,"Credenciales inválidas"));
        }
        User user=userRepository.findByUsername(req.getUsername()).orElseThrow();
        String token=jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token,new ArrayList<>(user.getRoles()),user.getUsername(),"Login exitoso"));
    }
}
