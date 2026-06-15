package pe.edu.upeu.msreserva.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.upeu.msreserva.filter.JwtAuthFilter;
@Configuration @EnableWebSecurity @EnableMethodSecurity(prePostEnabled=true) @RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(c->c.disable())
            .authorizeHttpRequests(a->a
                .requestMatchers("/actuator/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
