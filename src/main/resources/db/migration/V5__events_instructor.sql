-- Loop 09: assign optional instructor to events (Approved DD-09)

ALTER TABLE events
    ADD COLUMN instructor_id BIGINT NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_instructor
        FOREIGN KEY (instructor_id) REFERENCES instructors (id) ON DELETE SET NULL;

CREATE INDEX idx_events_instructor_id ON events (instructor_id);
