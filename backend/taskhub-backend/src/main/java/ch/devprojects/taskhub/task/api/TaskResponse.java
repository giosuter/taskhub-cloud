package ch.devprojects.taskhub.task.api;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record TaskResponse(
  String id,
  String title,
  String description,
  String status,
  LocalDate dueDate,
  String ownerId,
  OffsetDateTime createdAt,
  OffsetDateTime updatedAt
) {}