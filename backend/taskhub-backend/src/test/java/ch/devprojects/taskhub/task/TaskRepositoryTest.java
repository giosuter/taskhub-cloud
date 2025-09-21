package ch.devprojects.taskhub.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

  @org.springframework.beans.factory.annotation.Autowired
  TaskRepository repo;

  @Test
  void save_and_query_by_owner() {
    Task t = new Task();
    t.setTitle("Repo test");
    t.setOwnerId("owner-1");
    t.setCreatedAt(OffsetDateTime.now());
    t.setUpdatedAt(OffsetDateTime.now());
    repo.save(t);

    List<Task> tasks = repo.findByOwnerIdOrderByCreatedAtDesc("owner-1");
    assertThat(tasks).hasSize(1);
    assertThat(tasks.get(0).getTitle()).isEqualTo("Repo test");
  }
}