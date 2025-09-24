package ch.devprojects.taskhub.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@Profile("test") // only load for tests
public class TestSecurityConfig {

	@Bean(name = "testSecurityFilterChain")
	SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
		// Simplest: open everything in tests (MockMvc etc. get out of your way)
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}
}