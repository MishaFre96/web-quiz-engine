package engine.config;

import engine.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author MishaFre96
 *
 * Configuracion de seguridad de la aplicacion.
 * Define endpoints publicos y los que requieren autenticacion.
 * Usa Basic Auth y BCrypt para codificar contraseñas.
 */
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Codificador de contraseñas BCrypt.
     * @return el codificador que usara Spring Security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * @param http objeto HttpSecurity para configurar
     * @return el SecurityFilterChain construido
     * @throws Exception si hay algun error en la configuracion
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .build();
    }
}