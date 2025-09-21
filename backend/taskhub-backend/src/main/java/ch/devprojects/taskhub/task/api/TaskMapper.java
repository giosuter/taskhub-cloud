package ch.devprojects.taskhub.task.api;

import ch.devprojects.taskhub.task.Task;
import ch.devprojects.taskhub.task.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
  public Task toEntity(TaskRequest req) {
    Task t = new Task();
    t.setTitle(req.title());
    t.setDescription(req.description());
    if (req.status() != null) t.setStatus(TaskStatus.valueOf(req.status()));
    t.setDueDate(req.dueDate());
    return t;
  }

  public TaskResponse toResponse(Task t) {
    return new TaskResponse(
      t.getId(), t.getTitle(), t.getDescription(),
      t.getStatus().name(), t.getDueDate(), t.getOwnerId(),
      t.getCreatedAt(), t.getUpdatedAt()
    );
  }

  public void update(Task t, TaskRequest req) {
    if (req.title() != null) t.setTitle(req.title());
    if (req.description() != null) t.setDescription(req.description());
    if (req.status() != null) t.setStatus(TaskStatus.valueOf(req.status()));
    if (req.dueDate() != null) t.setDueDate(req.dueDate());
  }
}