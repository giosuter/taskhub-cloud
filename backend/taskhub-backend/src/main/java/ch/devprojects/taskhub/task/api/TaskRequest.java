package ch.devprojects.taskhub.task.api;

import ch.devprojects.taskhub.task.TaskStatus;
import java.time.LocalDateTime;

public class TaskRequest {
	private String title;
	private String description;
	private TaskStatus status;
	private LocalDateTime dueDate;

	public TaskRequest() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
}