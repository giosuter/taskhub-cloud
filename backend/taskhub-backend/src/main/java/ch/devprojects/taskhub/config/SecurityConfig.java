package ch.devprojects.taskhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("!test") // <--- IMPORTANT: do not load this chain when running tests
public class SecurityConfig {

	private static final String[] SWAGGER_WHITELIST = { "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**" };

	private static final String[] ACTUATOR_WHITELIST = { "/actuator/health", "/actuator/info" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(SWAGGER_WHITELIST).permitAll()
						.requestMatchers(ACTUATOR_WHITELIST).permitAll().requestMatchers(HttpMethod.GET, "/api/ping")
						.permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}