package Spring.Home7.Spring.webSecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .requestMatchers("/", "/home").permitAll() // Разрешает доступ без аутентификации
                        .anyRequest().authenticated() // Требует аутентификацию для всех остальных запросов
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Указывает страницу для входа
                        .permitAll() // Разрешает доступ к форме входа всем
                )
                .logout(logout -> logout.permitAll()); // Разрешает всем выполнить выход
        return http.build(); // Возвращает сконфигурированный экземпляр HttpSecurity
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
