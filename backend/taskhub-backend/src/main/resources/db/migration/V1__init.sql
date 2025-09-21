CREATE TABLE IF NOT EXISTS tasks (
  id CHAR(36) PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description TEXT NULL,
  status VARCHAR(20) NOT NULL,
  due_date DATE NULL,
  owner_id CHAR(36) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  updated_at DATETIME(6) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_tasks_owner_created ON tasks(owner_id, created_at);
