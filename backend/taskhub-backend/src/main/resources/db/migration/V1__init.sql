CREATE TABLE IF NOT EXISTS tasks (
  id           VARCHAR(36)  NOT NULL,
  title        VARCHAR(255) NOT NULL,
  description  TEXT,
  status       VARCHAR(32)  NOT NULL,
  owner_id     VARCHAR(64)  NOT NULL,
  created_at   TIMESTAMP    NOT NULL,
  updated_at   TIMESTAMP    NOT NULL,
  due_date     TIMESTAMP    NULL,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_tasks_owner_created ON tasks(owner_id, created_at DESC);