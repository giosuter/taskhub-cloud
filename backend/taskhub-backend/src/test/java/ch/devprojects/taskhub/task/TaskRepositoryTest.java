package ch.devprojects.taskhub.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TaskRepositoryTest {

	@Autowired
	private TaskRepository repo;

	@Test
	@DisplayName("findByOwnerIdOrderByCreatedAtDesc returns newest first for an owner")
	void findByOwnerIdOrderByCreatedAtDesc() {
		// given
		Task t1 = new Task();
		t1.setOwnerId("owner-1");
		t1.setTitle("A");
		t1.setDescription("desc A");
		t1.setStatus(TaskStatus.TODO);
		t1.setDueDate(LocalDateTime.now().plusDays(1));
		repo.saveAndFlush(t1);

		Task t2 = new Task();
		t2.setOwnerId("owner-1");
		t2.setTitle("B");
		t2.setDescription("desc B");
		t2.setStatus(TaskStatus.TODO);
		t2.setDueDate(LocalDateTime.now().plusDays(2));
		repo.saveAndFlush(t2);

		Task tOther = new Task();
		tOther.setOwnerId("owner-2");
		tOther.setTitle("X");
		tOther.setDescription("desc X");
		tOther.setStatus(TaskStatus.DONE);
		repo.saveAndFlush(tOther);

		// when
		List<Task> list = repo.findByOwnerIdOrderByCreatedAtDesc("owner-1");

		// then
		assertThat(list).hasSize(2);
		assertThat(list.get(0).getCreatedAt()).isAfterOrEqualTo(list.get(1).getCreatedAt());
		assertThat(list).extracting(Task::getOwnerId).containsOnly("owner-1");
	}
}