-- MinSuke MVP Schema
-- Loop 03 Design — applied in Loop 04 via Flyway
-- PostgreSQL 16

-- =============================================================================
-- households
-- =============================================================================
CREATE TABLE households (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    name_kana   VARCHAR(100) NOT NULL,
    group_name  VARCHAR(50),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT now()
);

-- =============================================================================
-- users
-- =============================================================================
CREATE TABLE users (
    id            BIGSERIAL    PRIMARY KEY,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(20)  NOT NULL,
    household_id  BIGINT,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT fk_users_household
        FOREIGN KEY (household_id) REFERENCES households (id) ON DELETE RESTRICT,
    CONSTRAINT chk_users_role
        CHECK (role IN ('ADMIN', 'PARENT')),
    CONSTRAINT chk_users_household_by_role
        CHECK (
            (role = 'ADMIN' AND household_id IS NULL)
            OR (role = 'PARENT' AND household_id IS NOT NULL)
        )
);

CREATE INDEX idx_users_household_id ON users (household_id);

-- =============================================================================
-- parents
-- =============================================================================
CREATE TABLE parents (
    id           BIGSERIAL    PRIMARY KEY,
    household_id BIGINT       NOT NULL,
    name         VARCHAR(100) NOT NULL,
    name_kana    VARCHAR(100) NOT NULL,
    phone        VARCHAR(20),
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT fk_parents_household
        FOREIGN KEY (household_id) REFERENCES households (id) ON DELETE CASCADE
);

CREATE INDEX idx_parents_household_id ON parents (household_id);

-- =============================================================================
-- children
-- =============================================================================
CREATE TABLE children (
    id           BIGSERIAL    PRIMARY KEY,
    household_id BIGINT       NOT NULL,
    name         VARCHAR(100) NOT NULL,
    name_kana    VARCHAR(100) NOT NULL,
    birth_date   DATE,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT fk_children_household
        FOREIGN KEY (household_id) REFERENCES households (id) ON DELETE CASCADE
);

CREATE INDEX idx_children_household_id ON children (household_id);

-- =============================================================================
-- events
-- =============================================================================
CREATE TABLE events (
    id                 BIGSERIAL    PRIMARY KEY,
    title              VARCHAR(200) NOT NULL,
    description        TEXT         NOT NULL,
    event_date         DATE         NOT NULL,
    start_time         TIME,
    end_time           TIME,
    capacity           INT,
    created_by_user_id BIGINT       NOT NULL,
    created_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT fk_events_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT chk_events_capacity
        CHECK (capacity IS NULL OR capacity > 0),
    CONSTRAINT chk_events_time_range
        CHECK (
            start_time IS NULL OR end_time IS NULL OR start_time <= end_time
        )
);

CREATE INDEX idx_events_event_date ON events (event_date);

-- =============================================================================
-- event_attendances
-- =============================================================================
CREATE TABLE event_attendances (
    id                    BIGSERIAL    PRIMARY KEY,
    event_id              BIGINT       NOT NULL,
    participant_type      VARCHAR(10)  NOT NULL,
    parent_id             BIGINT,
    child_id              BIGINT,
    household_id          BIGINT       NOT NULL,
    registered_by_user_id BIGINT       NOT NULL,
    status                VARCHAR(20)  NOT NULL DEFAULT 'REGISTERED',
    registered_at         TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT now(),

    CONSTRAINT fk_event_attendances_event
        FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_attendances_parent
        FOREIGN KEY (parent_id) REFERENCES parents (id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_attendances_child
        FOREIGN KEY (child_id) REFERENCES children (id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_attendances_household
        FOREIGN KEY (household_id) REFERENCES households (id) ON DELETE RESTRICT,
    CONSTRAINT fk_event_attendances_registered_by
        FOREIGN KEY (registered_by_user_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT chk_event_attendances_participant_type
        CHECK (participant_type IN ('PARENT', 'CHILD')),
    CONSTRAINT chk_event_attendances_status
        CHECK (status IN ('REGISTERED', 'CANCELLED')),
    CONSTRAINT chk_event_attendances_participant
        CHECK (
            (participant_type = 'PARENT' AND parent_id IS NOT NULL AND child_id IS NULL)
            OR (participant_type = 'CHILD' AND child_id IS NOT NULL AND parent_id IS NULL)
        )
);

CREATE INDEX idx_event_attendances_event_id ON event_attendances (event_id);
CREATE INDEX idx_event_attendances_household_id ON event_attendances (household_id);
CREATE INDEX idx_event_attendances_parent_id ON event_attendances (parent_id);
CREATE INDEX idx_event_attendances_child_id ON event_attendances (child_id);

CREATE UNIQUE INDEX uk_event_attendances_event_parent_registered
    ON event_attendances (event_id, parent_id)
    WHERE parent_id IS NOT NULL AND status = 'REGISTERED';

CREATE UNIQUE INDEX uk_event_attendances_event_child_registered
    ON event_attendances (event_id, child_id)
    WHERE child_id IS NOT NULL AND status = 'REGISTERED';
