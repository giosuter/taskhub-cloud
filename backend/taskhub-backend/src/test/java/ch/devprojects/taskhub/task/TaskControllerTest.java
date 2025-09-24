package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.config.TestSecurityConfig;
import ch.devprojects.taskhub.task.api.TaskRequest;
import ch.devprojects.taskhub.task.api.TaskResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(controllers = TaskController.class)
@Import(TestSecurityConfig.class) // <-- disable security for this slice
@ActiveProfiles("test")
class TaskControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper om;

	@MockBean
	private TaskService service;

	@Test
	@DisplayName("list requires ownerId param and returns 200 with payload")
	void list_requiresOwner_andReturnsData() throws Exception {
		TaskResponse r1 = new TaskResponse();
		r1.setId("t-1");
		r1.setOwnerId("o-1");
		r1.setTitle("A");
		r1.setStatus(TaskStatus.TODO);
		r1.setCreatedAt(LocalDateTime.now());

		TaskResponse r2 = new TaskResponse();
		r2.setId("t-2");
		r2.setOwnerId("o-1");
		r2.setTitle("B");
		r2.setStatus(TaskStatus.DONE);
		r2.setCreatedAt(LocalDateTime.now().minusSeconds(1));

		when(service.list("o-1")).thenReturn(List.of(r1, r2));

		mvc.perform(get("/api/tasks").param("ownerId", "o-1")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value("t-1")).andExpect(jsonPath("$[1].id").value("t-2"));
	}

	@Test
	@DisplayName("create accepts JSON body and ownerId param, returns created Task")
	void create_acceptsJsonBody_andOwnerParam() throws Exception {
		TaskRequest req = new TaskRequest();
		req.setTitle("New Task");
		req.setDescription("desc");
		req.setStatus(TaskStatus.TODO);
		req.setDueDate(LocalDateTime.now().plusDays(1));

		TaskResponse resp = new TaskResponse();
		resp.setId("gen-123");
		resp.setOwnerId("o-9");
		resp.setTitle("New Task");
		resp.setDescription("desc");
		resp.setStatus(TaskStatus.TODO);
		resp.setCreatedAt(LocalDateTime.now());

		when(service.create(eq("o-9"), any(TaskRequest.class))).thenReturn(resp);

		mvc.perform(post("/api/tasks").param("ownerId", "o-9").contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(req))).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value("gen-123")).andExpect(jsonPath("$.ownerId").value("o-9"))
				.andExpect(jsonPath("$.title").value("New Task"));
	}
}