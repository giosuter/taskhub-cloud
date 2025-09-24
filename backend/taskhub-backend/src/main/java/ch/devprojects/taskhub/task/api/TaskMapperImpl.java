package ch.devprojects.taskhub.task.api;

import ch.devprojects.taskhub.task.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

	@Override
	public Task toEntity(TaskRequest req) {
		Task t = new Task();
		if (req == null) {
			return t;
		}
		t.setTitle(req.getTitle());
		t.setDescription(req.getDescription());
		t.setStatus(req.getStatus());
		t.setDueDate(req.getDueDate());
		return t;
	}

	@Override
	public void update(Task t, TaskRequest req) {
		if (t == null || req == null) {
			return;
		}
		if (req.getTitle() != null) {
			t.setTitle(req.getTitle());
		}
		if (req.getDescription() != null) {
			t.setDescription(req.getDescription());
		}
		if (req.getStatus() != null) {
			t.setStatus(req.getStatus());
		}
		if (req.getDueDate() != null) {
			t.setDueDate(req.getDueDate());
		}
	}

	@Override
	public TaskResponse toResponse(Task t) {
		TaskResponse r = new TaskResponse();
		if (t == null) {
			return r;
		}
		r.setId(t.getId());
		r.setOwnerId(t.getOwnerId());
		r.setTitle(t.getTitle());
		r.setDescription(t.getDescription());
		r.setStatus(t.getStatus());
		r.setCreatedAt(t.getCreatedAt());
		r.setUpdatedAt(t.getUpdatedAt());
		r.setDueDate(t.getDueDate());
		return r;
	}
}