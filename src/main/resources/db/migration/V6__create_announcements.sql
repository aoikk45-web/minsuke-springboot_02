-- Loop 10: in-app announcements + per-user read tracking (Proposed — approve before migrate)

CREATE TABLE announcements (
    id                BIGSERIAL PRIMARY KEY,
    title             VARCHAR(200) NOT NULL,
    body              TEXT NOT NULL,
    created_by_user_id BIGINT NOT NULL REFERENCES users (id),
    published_at      TIMESTAMPTZ NOT NULL,
    created_at        TIMESTAMPTZ NOT NULL,
    updated_at        TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_announcements_published_at ON announcements (published_at DESC);

CREATE TABLE announcement_reads (
    id               BIGSERIAL PRIMARY KEY,
    announcement_id  BIGINT NOT NULL REFERENCES announcements (id) ON DELETE CASCADE,
    user_id          BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    read_at          TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_announcement_reads_announcement_user UNIQUE (announcement_id, user_id)
);

CREATE INDEX idx_announcement_reads_user_id ON announcement_reads (user_id);
