package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskMapper;
import ch.devprojects.taskhub.task.api.TaskMapperImpl;
import ch.devprojects.taskhub.task.api.TaskRequest;
import ch.devprojects.taskhub.task.api.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({ TaskMapperImpl.class })
class TaskServiceTest {

	@Autowired
	private TaskRepository repo;

	@Autowired
	private TaskMapper mapper;

	private TaskService service;

	@BeforeEach
	void setUp() {
		service = new TaskService(repo, mapper);
	}

	@Test
	@DisplayName("create stores a new task and returns response with id")
	void create_storesTask() {
		TaskRequest req = new TaskRequest();
		req.setTitle("Create me");
		req.setDescription("desc");
		req.setStatus(TaskStatus.TODO);
		req.setDueDate(LocalDateTime.now().plusDays(3));

		TaskResponse resp = service.create("owner-123", req);

		assertThat(resp.getId()).isNotBlank();
		assertThat(resp.getOwnerId()).isEqualTo("owner-123");
		assertThat(resp.getTitle()).isEqualTo("Create me");
	}

	@Test
	@DisplayName("list returns tasks for owner ordered by createdAt desc")
	void list_returnsTasks() {
		// seed
		Task a = new Task();
		a.setOwnerId("owner-L");
		a.setTitle("A");
		a.setStatus(TaskStatus.TODO);
		repo.save(a);

		Task b = new Task();
		b.setOwnerId("owner-L");
		b.setTitle("B");
		b.setStatus(TaskStatus.TODO);
		repo.save(b);

		List<TaskResponse> list = service.list("owner-L");
		assertThat(list).hasSize(2);
		assertThat(list.get(0).getCreatedAt()).isAfterOrEqualTo(list.get(1).getCreatedAt());
	}
}