-- MinSuke Development Seed Data (local profile only)
-- Dev passwords: password (BCrypt)
-- Hash verified with BCryptPasswordEncoder.matches("password", ...)
-- Rich demo (旗当番・複数家庭・参加率) is loaded by V10__reset_demo_seed.sql

INSERT INTO users (email, password_hash, role, household_id)
VALUES (
    'admin@minsuke.local',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'ADMIN',
    NULL
);
