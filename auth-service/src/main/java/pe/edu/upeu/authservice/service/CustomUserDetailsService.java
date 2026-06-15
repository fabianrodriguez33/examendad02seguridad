package pe.edu.upeu.authservice.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import pe.edu.upeu.authservice.repository.UserRepository;
import java.util.*;
@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user=userRepository.findByUsername(username)
            .orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado: "+username));
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(String role:user.getRoles()) authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
