package ch.devprojects.taskhub;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HttpSmokeTest {

	@LocalServerPort
	int port;

	@Autowired
	Environment env;

	@Autowired
	TestRestTemplate rest;

	private String baseUrl() {
		String ctx = env.getProperty("server.servlet.context-path", "");
		if (ctx != null && ctx.endsWith("/"))
			ctx = ctx.substring(0, ctx.length() - 1);
		return "http://localhost:" + port + (ctx == null ? "" : ctx);
	}

	@Test
	void actuatorHealth_isUp() {
		ResponseEntity<String> res = rest.getForEntity(baseUrl() + "/actuator/health", String.class);
		assertThat(res.getStatusCode().value()).isEqualTo(200);
		assertThat(res.getBody()).contains("\"status\":\"UP\"");
	}

	@Test
	void apiDocs_shouldReturn200Json() {
		ResponseEntity<String> res = rest.getForEntity(baseUrl() + "/v3/api-docs", String.class);
		assertThat(res.getStatusCode().value()).isEqualTo(200);
		assertThat(res.getHeaders().getContentType().toString()).contains("application/json");
	}

	@Test
	void swaggerUi_index_isReachable() {
		// Avoid redirect flakiness by checking the final index page
		ResponseEntity<String> res = rest.getForEntity(baseUrl() + "/swagger-ui/index.html", String.class);
		assertThat(res.getStatusCode().value()).isEqualTo(200);
		assertThat(res.getBody()).containsIgnoringCase("swagger");
	}
}