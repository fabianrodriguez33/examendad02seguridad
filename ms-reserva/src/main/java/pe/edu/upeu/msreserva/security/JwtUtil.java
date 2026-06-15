package pe.edu.upeu.msreserva.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    private SecretKey getSigningKey(){
        byte[] kb=secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(kb);
    }
    public String extractUsername(String token){ return extractClaim(token,Claims::getSubject); }
    public <T> T extractClaim(String token,Function<Claims,T> resolver){ return resolver.apply(extractAllClaims(token)); }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    public boolean isTokenExpired(String token){ return extractClaim(token,Claims::getExpiration).before(new Date()); }
}
