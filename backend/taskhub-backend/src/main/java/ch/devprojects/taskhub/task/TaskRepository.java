package ch.devprojects.taskhub.task;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
	List<Task> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}