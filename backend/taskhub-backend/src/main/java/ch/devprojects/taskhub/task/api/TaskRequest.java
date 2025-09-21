package ch.devprojects.taskhub.task.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskRequest(
  @NotBlank @Size(max = 150) String title,
  @Size(max = 4000) String description,
  //"TODO" | "IN_PROGRESS" | "DONE" (optional on create)
  String status,
  LocalDate dueDate
) {}