-- Loop 12: participation unit (Proposed — approve before migrate)

ALTER TABLE schedules
    ADD COLUMN participation_unit VARCHAR(20) NULL;

ALTER TABLE schedules
    ADD CONSTRAINT chk_schedules_participation_unit
        CHECK (participation_unit IS NULL OR participation_unit IN ('HOUSEHOLD', 'PARENT', 'CHILD'));

ALTER TABLE events
    ADD COLUMN participation_unit VARCHAR(20) NULL;

ALTER TABLE events
    ADD CONSTRAINT chk_events_participation_unit
        CHECK (participation_unit IS NULL OR participation_unit IN ('HOUSEHOLD', 'PARENT', 'CHILD'));

ALTER TABLE event_attendances
    DROP CONSTRAINT chk_event_attendances_participant_type;

ALTER TABLE event_attendances
    ADD CONSTRAINT chk_event_attendances_participant_type
        CHECK (participant_type IN ('PARENT', 'CHILD', 'HOUSEHOLD'));

ALTER TABLE event_attendances
    DROP CONSTRAINT chk_event_attendances_participant;

ALTER TABLE event_attendances
    ADD CONSTRAINT chk_event_attendances_participant
        CHECK (
            (participant_type = 'PARENT' AND parent_id IS NOT NULL AND child_id IS NULL)
            OR (participant_type = 'CHILD' AND child_id IS NOT NULL AND parent_id IS NULL)
            OR (participant_type = 'HOUSEHOLD' AND parent_id IS NULL AND child_id IS NULL)
        );

CREATE UNIQUE INDEX uk_event_attendances_event_household_registered
    ON event_attendances (event_id, household_id)
    WHERE status = 'REGISTERED' AND participant_type = 'HOUSEHOLD';
