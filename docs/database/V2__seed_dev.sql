-- MinSuke Development Seed Data (local profile only)
-- Mirror of src/main/resources/db/migration-dev/V2__seed_dev.sql
-- Dev passwords: password (BCrypt)
-- Hash verified with BCryptPasswordEncoder.matches("password", ...)
-- WARNING: Development only — do not use in shared or production environments

INSERT INTO users (email, password_hash, role, household_id)
VALUES (
    'admin@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'ADMIN',
    NULL
);

INSERT INTO households (name, name_kana, group_name)
VALUES ('サンプル家', 'さんぷるけ', 'A班');

INSERT INTO users (email, password_hash, role, household_id)
VALUES (
    'parent@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'PARENT',
    1
);

INSERT INTO parents (household_id, name, name_kana, phone)
VALUES (1, 'サンプル 太郎', 'さんぷる たろう', '090-0000-0001');

INSERT INTO children (household_id, name, name_kana, birth_date)
VALUES (1, 'サンプル 花子', 'さんぷる はなこ', '2015-04-01');

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
