-- Convert tasks.id to VARCHAR(36) so it matches Hibernate expectation
ALTER TABLE tasks
  MODIFY COLUMN id VARCHAR(36) NOT NULL;

-- Optional safety: if 'id' is the PK already, this keeps it; if not, uncomment:
-- ALTER TABLE tasks DROP PRIMARY KEY, ADD PRIMARY KEY (id);
