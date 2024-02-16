package Spring.Home7.Spring.webSecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenService {
    @Value("${jwt.token.validity}")
    private long JWT_TOKEN_VALIDITY;
    @Value("${jwt.secret}")
    private String SECRET;

    public String generateToken(int userId,String role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }
    public Authentication getAuth(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        int userId = Integer.parseInt(claims.getSubject());
        String role = claims.get("role", String.class);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return new UsernamePasswordAuthenticationToken(userId,null,authorities);

    }

    public int getUserIdFromToken(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        String tokenWithOutBearer = token.substring(7);
        return Integer.parseInt(getClaimFromToken(tokenWithOutBearer,Claims::getSubject));

    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
