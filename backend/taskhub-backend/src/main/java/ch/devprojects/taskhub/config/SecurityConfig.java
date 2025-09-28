package ch.devprojects.taskhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 1) Disable CSRF for simplicity (enables POST/PUT/DELETE without tokens)
        http.csrf(csrf -> csrf.disable());

        // 2) Allow H2 console (if you use it) to render inside a frame
        http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        // 3) Open everything (no auth required)
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // 4) Keep basic/form configured (harmless while everything is open)
        http.httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());

        return http.build();
    }
}