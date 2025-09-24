package ch.devprojects.taskhub.task.api;

import ch.devprojects.taskhub.task.Task;

/**
 * Simple mapper contract
 */
public interface TaskMapper {

	/**
	 * Create a new Task entity from the incoming request.
	 */
	Task toEntity(TaskRequest req);

	/**
	 * Copy non-null fields from the request into an existing entity.
	 */
	void update(Task entity, TaskRequest req);

	/**
	 * Convert an entity to its API response DTO.
	 */
	TaskResponse toResponse(Task entity);
}