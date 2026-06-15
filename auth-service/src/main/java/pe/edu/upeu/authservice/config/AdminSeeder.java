package pe.edu.upeu.authservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.upeu.authservice.entity.User;
import pe.edu.upeu.authservice.repository.UserRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .fullName("Administrador")
                    .dni("00000000")
                    .email("admin@upeu.edu.pe")
                    .universityCode("000000000")
                    .phone("000000000")
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of("ADMIN"))
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin creado: admin / admin123");
        }
    }
}