-- Loop 11: schedule master + optional link from events (Proposed — approve before migrate)

CREATE TABLE schedules (
    id                  BIGSERIAL PRIMARY KEY,
    title               VARCHAR(200) NOT NULL,
    description         TEXT NOT NULL,
    schedule_type       VARCHAR(20) NOT NULL,
    day_of_week         SMALLINT NULL,
    start_time          TIME NULL,
    end_time            TIME NULL,
    one_off_date        DATE NULL,
    valid_from          DATE NULL,
    valid_until         DATE NULL,
    capacity            INTEGER NULL,
    instructor_id       BIGINT NULL REFERENCES instructors (id) ON DELETE SET NULL,
    active              BOOLEAN NOT NULL DEFAULT TRUE,
    created_by_user_id  BIGINT NOT NULL REFERENCES users (id),
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL,
    CONSTRAINT chk_schedules_type CHECK (schedule_type IN ('ONE_OFF', 'WEEKLY')),
    CONSTRAINT chk_schedules_weekly_day CHECK (
        schedule_type <> 'WEEKLY' OR (day_of_week BETWEEN 1 AND 7)
    ),
    CONSTRAINT chk_schedules_one_off_date CHECK (
        schedule_type <> 'ONE_OFF' OR one_off_date IS NOT NULL
    )
);

CREATE INDEX idx_schedules_active ON schedules (active);
CREATE INDEX idx_schedules_instructor_id ON schedules (instructor_id);

ALTER TABLE events
    ADD COLUMN schedule_id BIGINT NULL;

ALTER TABLE events
    ADD CONSTRAINT fk_events_schedule
        FOREIGN KEY (schedule_id) REFERENCES schedules (id) ON DELETE SET NULL;

CREATE INDEX idx_events_schedule_id ON events (schedule_id);
