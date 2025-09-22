-- Align owner_id type with JPA (String UUID -> VARCHAR(36))
ALTER TABLE tasks
  MODIFY COLUMN owner_id VARCHAR(36) NULL;

-- If owner_id must be NOT NULL in your model, use:
-- ALTER TABLE tasks MODIFY COLUMN owner_id VARCHAR(36) NOT NULL;

-- If you plan a foreign key to a users table later, you can add it then, e.g.:
-- ALTER TABLE tasks ADD CONSTRAINT fk_tasks_owner
--   FOREIGN KEY (owner_id) REFERENCES users(id);
