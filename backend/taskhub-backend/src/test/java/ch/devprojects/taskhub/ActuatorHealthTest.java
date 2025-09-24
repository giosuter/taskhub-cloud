package ch.devprojects.taskhub;

import ch.devprojects.taskhub.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class ActuatorHealthTest {

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate rest;

	@Test
	void health_isUp() {
		String url = "http://localhost:" + port + "/taskhub/actuator/health";
		ResponseEntity<String> resp = rest.getForEntity(url, String.class);
		assertThat(resp.getStatusCode().value()).isEqualTo(200);
		assertThat(resp.getBody()).contains("\"status\":\"UP\"");
	}
}