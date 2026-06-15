package pe.edu.upeu.authservice.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pe.edu.upeu.authservice.entity.User;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.expiration}") private Long expiration;
    private SecretKey getSigningKey(){
        byte[] kb = secret.getBytes(StandardCharsets.UTF_8);
        if(kb.length<32) throw new IllegalStateException("JWT secret debe tener al menos 32 caracteres");
        return Keys.hmacShaKeyFor(kb);
    }
    public String generateToken(UserDetails userDetails){
        User user=(User)userDetails;
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",user.getId().toString());
        claims.put("universityCode",user.getUniversityCode());
        claims.put("roles",new ArrayList<>(user.getRoles()));
        return Jwts.builder().setClaims(claims).setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+expiration))
            .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
    }
    public String extractUsername(String token){ return extractClaim(token,Claims::getSubject); }
    public <T> T extractClaim(String token,Function<Claims,T> resolver){ return resolver.apply(extractAllClaims(token)); }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    public boolean validateToken(String token,UserDetails ud){
        return extractUsername(token).equals(ud.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){ return extractClaim(token,Claims::getExpiration).before(new Date()); }
}
