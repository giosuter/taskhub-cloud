package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskRequest;
import ch.devprojects.taskhub.task.api.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService service;
  // MVP “logged in user” placeholder (replace with JWT later)
  private static final String DEMO_USER = UUID.fromString("00000000-0000-0000-0000-000000000001").toString();

  public TaskController(TaskService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest req) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(DEMO_USER, req));
  }

  @GetMapping
  public List<TaskResponse> list() {
    return service.list(DEMO_USER);
  }

  @GetMapping("/{id}")
  public TaskResponse get(@PathVariable String id) {
    return service.get(DEMO_USER, id);
  }

  @PutMapping("/{id}")
  public TaskResponse update(@PathVariable String id, @RequestBody @Valid TaskRequest req) {
    return service.update(DEMO_USER, id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    service.delete(DEMO_USER, id);
  }
}