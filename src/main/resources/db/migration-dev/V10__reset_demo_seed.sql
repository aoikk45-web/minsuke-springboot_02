-- Loop 17: replace local demo data so participation rates are easy to verify.
-- Passwords for all PARENT accounts: password (same BCrypt as V2)

DELETE FROM event_attendances;
DELETE FROM announcement_reads;
DELETE FROM announcements;
DELETE FROM events;
DELETE FROM schedule_weekdays;
DELETE FROM schedules;
DELETE FROM parents;
DELETE FROM children;
DELETE FROM users WHERE role = 'PARENT';
DELETE FROM households;
DELETE FROM instructors;

INSERT INTO instructors (name, name_kana, email, phone, notes, active)
VALUES (
    '山田 講師',
    'やまだ こうし',
    'instructor@minsuke.local',
    '090-0000-1001',
    '開発用サンプル講師',
    TRUE
);

INSERT INTO households (name, name_kana, group_name) VALUES
    ('サンプル家', 'さんぷるけ', 'A班'),
    ('中村家', 'なかむらけ', 'B班'),
    ('佐藤家', 'さとうけ', 'C班');

INSERT INTO users (email, password_hash, role, household_id)
SELECT
    'parent@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'PARENT',
    id
FROM households WHERE name = 'サンプル家';

INSERT INTO users (email, password_hash, role, household_id)
SELECT
    'parent-b@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'PARENT',
    id
FROM households WHERE name = '中村家';

INSERT INTO users (email, password_hash, role, household_id)
SELECT
    'parent-c@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'PARENT',
    id
FROM households WHERE name = '佐藤家';

INSERT INTO parents (household_id, name, name_kana, phone)
SELECT id, 'サンプル 太郎', 'さんぷる たろう', '090-0000-0001'
FROM households WHERE name = 'サンプル家';

INSERT INTO parents (household_id, name, name_kana, phone)
SELECT id, '中村 一郎', 'なかむら いちろう', '090-0000-0002'
FROM households WHERE name = '中村家';

INSERT INTO parents (household_id, name, name_kana, phone)
SELECT id, '佐藤 次郎', 'さとう じろう', '090-0000-0003'
FROM households WHERE name = '佐藤家';

INSERT INTO children (household_id, name, name_kana, birth_date)
SELECT id, 'サンプル 花子', 'さんぷる はなこ', DATE '2015-04-01'
FROM households WHERE name = 'サンプル家';

INSERT INTO children (household_id, name, name_kana, birth_date)
SELECT id, '中村 美咲', 'なかむら みさき', DATE '2016-08-15'
FROM households WHERE name = '中村家';

INSERT INTO schedules (
    title, description, schedule_type, start_time, end_time,
    valid_from, valid_until, capacity, instructor_id, participation_unit, active,
    created_by_user_id, created_at, updated_at
)
SELECT
    '旗当番',
    '週次の旗当番（開発用）。参加率の確認用データです。',
    'WEEKLY',
    TIME '08:00',
    TIME '08:30',
    (CURRENT_DATE - ((EXTRACT(ISODOW FROM CURRENT_DATE)::int - 1)) - 28),
    (CURRENT_DATE - ((EXTRACT(ISODOW FROM CURRENT_DATE)::int - 1)) + 28),
    5,
    i.id,
    'PARENT',
    TRUE,
    (SELECT id FROM users WHERE email = 'admin@minsuke.local'),
    now(),
    now()
FROM instructors i
WHERE i.name = '山田 講師';

INSERT INTO schedule_weekdays (schedule_id, day_of_week)
SELECT id, 1 FROM schedules WHERE title = '旗当番';

INSERT INTO events (
    title, description, event_date, start_time, end_time, capacity,
    instructor_id, schedule_id, participation_unit, created_by_user_id
)
SELECT
    '旗当番',
    '週次の旗当番です。',
    (CURRENT_DATE - ((EXTRACT(ISODOW FROM CURRENT_DATE)::int - 1)) + (n - 4) * 7),
    TIME '08:00',
    TIME '08:30',
    5,
    (SELECT id FROM instructors WHERE name = '山田 講師'),
    (SELECT id FROM schedules WHERE title = '旗当番'),
    'PARENT',
    (SELECT id FROM users WHERE email = 'admin@minsuke.local')
FROM generate_series(0, 7) AS g(n);

INSERT INTO events (
    title, description, event_date, start_time, end_time, capacity,
    participation_unit, created_by_user_id
)
VALUES (
    '手作り運動会',
    'スケジュールに紐づかない手作りイベント（充足確認用）。',
    CURRENT_DATE + 3,
    TIME '10:00',
    TIME '12:00',
    10,
    'PARENT',
    (SELECT id FROM users WHERE email = 'admin@minsuke.local')
);

-- サンプル家: 旗当番 6/8 回（75%）
INSERT INTO event_attendances (
    event_id, participant_type, parent_id, child_id, household_id,
    registered_by_user_id, status
)
SELECT
    x.event_id,
    'PARENT',
    p.id,
    NULL,
    h.id,
    u.id,
    'REGISTERED'
FROM (
    SELECT e.id AS event_id
    FROM events e
    WHERE e.schedule_id = (SELECT id FROM schedules WHERE title = '旗当番')
      AND e.event_date <= (CURRENT_DATE - ((EXTRACT(ISODOW FROM CURRENT_DATE)::int - 1)) + 14)
    ORDER BY e.event_date
    LIMIT 6
) x
JOIN households h ON h.name = 'サンプル家'
JOIN parents p ON p.household_id = h.id
JOIN users u ON u.email = 'parent@minsuke.local';

-- 中村家: 旗当番 3/8 回（38%）
INSERT INTO event_attendances (
    event_id, participant_type, parent_id, child_id, household_id,
    registered_by_user_id, status
)
SELECT
    x.event_id,
    'PARENT',
    p.id,
    NULL,
    h.id,
    u.id,
    'REGISTERED'
FROM (
    SELECT e.id AS event_id
    FROM events e
    WHERE e.schedule_id = (SELECT id FROM schedules WHERE title = '旗当番')
    ORDER BY e.event_date
    LIMIT 3
) x
JOIN households h ON h.name = '中村家'
JOIN parents p ON p.household_id = h.id
JOIN users u ON u.email = 'parent-b@minsuke.local';

-- 佐藤家: 0 回（0%）— 意図的に登録なし

-- 手作り運動会にサンプル家を登録（月次充足の確認用）
INSERT INTO event_attendances (
    event_id, participant_type, parent_id, child_id, household_id,
    registered_by_user_id, status
)
SELECT
    e.id,
    'PARENT',
    p.id,
    NULL,
    h.id,
    u.id,
    'REGISTERED'
FROM events e
JOIN households h ON h.name = 'サンプル家'
JOIN parents p ON p.household_id = h.id
JOIN users u ON u.email = 'parent@minsuke.local'
WHERE e.title = '手作り運動会';

INSERT INTO announcements (title, body, created_by_user_id, published_at, created_at, updated_at)
SELECT
    '開発用お知らせ',
    'シード入れ替え後の確認用お知らせです。',
    id,
    now(),
    now(),
    now()
FROM users
WHERE email = 'admin@minsuke.local';
