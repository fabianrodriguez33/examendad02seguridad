package pe.edu.upeu.msreserva.filter;
import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.edu.upeu.msreserva.security.JwtUtil;
import java.io.IOException;
import java.util.*;
@Component @RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
        String header=req.getHeader("Authorization");
        if(header==null||!header.startsWith("Bearer ")){chain.doFilter(req,res);return;}
        String token=header.substring(7);
        try {
            if(jwtUtil.isTokenExpired(token)){ sendError(res,401,"TOKEN_EXPIRED","El token expiró"); return; }
            String username=jwtUtil.extractUsername(token);
            Claims claims=jwtUtil.extractClaim(token,c->c);
            List<?> rawRoles=(List<?>)claims.get("roles");
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
            if(rawRoles!=null) rawRoles.forEach(r->authorities.add(new SimpleGrantedAuthority("ROLE_"+r)));
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(username,null,authorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            chain.doFilter(req,res);
        } catch(JwtException ex){ sendError(res,401,"TOKEN_INVALID","Token inválido"); }
    }
    private void sendError(HttpServletResponse res,int s,String err,String msg)throws IOException{
        res.setStatus(s); res.setContentType("application/json");
    }
}
