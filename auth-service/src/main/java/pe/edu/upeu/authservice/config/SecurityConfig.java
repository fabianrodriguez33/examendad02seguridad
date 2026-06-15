package pe.edu.upeu.authservice.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.upeu.authservice.filter.JwtAuthenticationFilter;
@Configuration @EnableWebSecurity @EnableMethodSecurity(prePostEnabled=true) @RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(c->c.disable())
            .authorizeHttpRequests(a->a
                .requestMatchers("/","/public/**","/auth/**","/api/auth/**","/v3/api-docs/**","/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
