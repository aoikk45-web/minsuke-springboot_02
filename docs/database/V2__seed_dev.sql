-- MinSuke Development Seed Data
-- Loop 03 Design — applied only in dev/local profile (Loop 04)
-- WARNING: Replace password hash before any shared environment use

-- Admin user (no household)
-- password: admin123 (BCrypt hash — replace in Loop 05 with proper encoding)
INSERT INTO users (email, password_hash, role, household_id)
VALUES (
    'admin@minsuke.local',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.R9L1gFSSp7p6KxK5K2',
    'ADMIN',
    NULL
);

-- Sample household
INSERT INTO households (name, name_kana, group_name)
VALUES ('サンプル家', 'さんぷるけ', 'A班');

-- Parent user linked to household 1
INSERT INTO users (email, password_hash, role, household_id)
VALUES (
    'parent@minsuke.local',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.R9L1gFSSp7p6KxK5K2',
    'PARENT',
    1
);

INSERT INTO parents (household_id, name, name_kana, phone)
VALUES (1, 'サンプル 太郎', 'さんぷる たろう', '090-0000-0001');

INSERT INTO children (household_id, name, name_kana, birth_date)
VALUES (1, 'サンプル 花子', 'さんぷる はなこ', '2015-04-01');

-- Sample event
INSERT INTO events (title, description, event_date, start_time, end_time, capacity, created_by_user_id)
VALUES (
    'サンプルイベント',
    '開発用のサンプルイベントです。',
    CURRENT_DATE + INTERVAL '7 days',
    '10:00',
    '12:00',
    20,
    1
);
