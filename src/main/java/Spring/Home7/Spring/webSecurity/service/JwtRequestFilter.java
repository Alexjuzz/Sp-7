package Spring.Home7.Spring.webSecurity.service;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenService service;
    @Autowired
    public JwtRequestFilter(JwtTokenService service){
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = httpServletRequest.getHeader("Authorization");
            if(token != null &&  token.startsWith("Bearer ")){
                String tokenWith = token.substring(7);
                Authentication authentication = service.getAuth(tokenWith);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        filterChain.doFilter(httpServletRequest,response);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http )throws Exception{
        http.addFilterBefore(this, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
