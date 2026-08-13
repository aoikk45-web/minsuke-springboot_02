-- Loop 09 — event instructor assignment (Approved DD-09)
-- Mirror of src/main/resources/db/migration/V5__events_instructor.sql

ALTER TABLE events
    ADD COLUMN instructor_id BIGINT NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_instructor
        FOREIGN KEY (instructor_id) REFERENCES instructors (id) ON DELETE SET NULL;

CREATE INDEX idx_events_instructor_id ON events (instructor_id);
