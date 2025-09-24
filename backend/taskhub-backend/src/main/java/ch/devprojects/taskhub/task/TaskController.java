package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskRequest;
import ch.devprojects.taskhub.task.api.TaskResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

	private final TaskService service;

	public TaskController(TaskService service) {
		this.service = service;
	}

	@GetMapping
	public List<TaskResponse> list(@RequestParam("ownerId") String ownerId) {
		return service.list(ownerId);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public TaskResponse create(@RequestParam("ownerId") String ownerId, @RequestBody TaskRequest req) {
		return service.create(ownerId, req);
	}

	@GetMapping("/{id}")
	public TaskResponse get(@RequestParam("ownerId") String ownerId, @PathVariable("id") String id) {
		return service.get(ownerId, id);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public TaskResponse update(@RequestParam("ownerId") String ownerId, @PathVariable("id") String id,
			@RequestBody TaskRequest req) {
		return service.update(ownerId, id, req);
	}

	@DeleteMapping("/{id}")
	public void delete(@RequestParam("ownerId") String ownerId, @PathVariable("id") String id) {
		service.delete(ownerId, id);
	}
}