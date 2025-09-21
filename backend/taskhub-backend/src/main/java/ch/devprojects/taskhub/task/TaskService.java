package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskMapper;
import ch.devprojects.taskhub.task.api.TaskRequest;
import ch.devprojects.taskhub.task.api.TaskResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
  private final TaskRepository repo;
  private final TaskMapper mapper;

  public TaskService(TaskRepository repo, TaskMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Transactional
  public TaskResponse create(String ownerId, TaskRequest req) {
    Task t = mapper.toEntity(req);
    t.setOwnerId(ownerId);
    if (t.getStatus() == null) t.setStatus(TaskStatus.TODO);
    return mapper.toResponse(repo.save(t));
  }

  @Transactional(readOnly = true)
  public List<TaskResponse> list(String ownerId) {
    return repo.findByOwnerIdOrderByCreatedAtDesc(ownerId)
               .stream().map(mapper::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public TaskResponse get(String ownerId, String id) {
    Task t = repo.findById(id).orElseThrow();
    if (!t.getOwnerId().equals(ownerId)) throw new IllegalArgumentException("Forbidden");
    return mapper.toResponse(t);
  }

  @Transactional
  public TaskResponse update(String ownerId, String id, TaskRequest req) {
    Task t = repo.findById(id).orElseThrow();
    if (!t.getOwnerId().equals(ownerId)) throw new IllegalArgumentException("Forbidden");
    mapper.update(t, req);
    return mapper.toResponse(repo.save(t));
  }

  @Transactional
  public void delete(String ownerId, String id) {
    Task t = repo.findById(id).orElseThrow();
    if (!t.getOwnerId().equals(ownerId)) throw new IllegalArgumentException("Forbidden");
    repo.delete(t);
  }
}