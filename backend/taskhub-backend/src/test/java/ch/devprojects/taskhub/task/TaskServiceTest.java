package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskMapper;
import ch.devprojects.taskhub.task.api.TaskRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TaskServiceTest {

  TaskRepository repo;
  TaskMapper mapper;
  TaskService service;

  @BeforeEach
  void setUp() {
    repo = mock(TaskRepository.class);
    mapper = new TaskMapper();
    service = new TaskService(repo, mapper);
  }

  @Test
  void create_setsDefaultStatusAndOwner() {
    String owner = "00000000-0000-0000-0000-000000000001";
    TaskRequest req = new TaskRequest("x", null, null, null);

    ArgumentCaptor<Task> cap = ArgumentCaptor.forClass(Task.class);
    given(repo.save(any())).willAnswer(inv -> inv.getArgument(0));

    var res = service.create(owner, req);

    verify(repo).save(cap.capture());
    Task saved = cap.getValue();
    assertThat(saved.getStatus()).isEqualTo(TaskStatus.TODO);
    assertThat(saved.getOwnerId()).isEqualTo(owner);
    assertThat(res.status()).isEqualTo("TODO");
  }

  @Test
  void list_mapsToResponses() {
    Task t = new Task();
    t.setTitle("A"); t.setOwnerId("o");
    given(repo.findByOwnerIdOrderByCreatedAtDesc("o")).willReturn(List.of(t));
    var out = service.list("o");
    assertThat(out).hasSize(1);
    assertThat(out.get(0).title()).isEqualTo("A");
  }
}