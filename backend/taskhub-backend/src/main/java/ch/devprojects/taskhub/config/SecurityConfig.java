package ch.devprojects.taskhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      @Value("${app.security.open:false}") boolean open
  ) throws Exception {

    // If you want CSRF off entirely, keep this; otherwise remove it
    http.csrf(csrf -> csrf.disable());

    // H2 console needs CSRF ignored (if CSRF is enabled) and to allow frames
    http
      .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
      .headers(h -> h
        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-ancestors 'self'"))
      );

    http.authorizeHttpRequests(auth -> {
      // IMPORTANT: matchers DO NOT include the context path (/taskhub)
      auth.requestMatchers(
          "/",                // welcome page if any
          "/index.html",
          "/default-ui.css",
          // dev utilities
          "/h2-console/**",
          "/jacoco/**",
          // actuator bits you want public
          "/actuator/health", "/actuator/health/**", "/actuator/info",
          // swagger
          "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**",
          // ping
          "/api/ping", "/ping"
      ).permitAll();

      if (open) {
        // dev/test: everything open
        auth.anyRequest().permitAll();
      } else {
        // prod: lock the rest
        auth.anyRequest().authenticated();
      }
    });

    // basic auth + default login form for restricted endpoints
    http.httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }
}