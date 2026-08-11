-- MinSuke Loop 08 — instructors (Approved)
-- Mirror of src/main/resources/db/migration/V3__create_instructors.sql

CREATE TABLE instructors (
    id           BIGSERIAL    PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    name_kana    VARCHAR(100) NOT NULL,
    email        VARCHAR(255),
    phone        VARCHAR(20),
    notes        TEXT,
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX idx_instructors_active ON instructors (active);
CREATE INDEX idx_instructors_name_kana ON instructors (name_kana);
