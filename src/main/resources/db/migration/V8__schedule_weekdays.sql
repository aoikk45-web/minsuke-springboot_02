-- Loop 11: WEEKLY 複数曜日（DD-18）
-- V7 の schedules.day_of_week（単一）を schedule_weekdays へ移行する。

CREATE TABLE schedule_weekdays (
    schedule_id BIGINT NOT NULL REFERENCES schedules (id) ON DELETE CASCADE,
    day_of_week SMALLINT NOT NULL,
    PRIMARY KEY (schedule_id, day_of_week),
    CONSTRAINT chk_schedule_weekdays_day CHECK (day_of_week BETWEEN 1 AND 7)
);

INSERT INTO schedule_weekdays (schedule_id, day_of_week)
SELECT id, day_of_week
FROM schedules
WHERE day_of_week IS NOT NULL;

ALTER TABLE schedules DROP CONSTRAINT chk_schedules_weekly_day;
ALTER TABLE schedules DROP COLUMN day_of_week;
