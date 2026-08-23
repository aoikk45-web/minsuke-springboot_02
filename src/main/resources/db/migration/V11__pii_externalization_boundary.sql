-- Loop 18: PII / billing / mail boundary
-- - users.email → login_id (no contact email in MinSuke)
-- - households: external_member_id + subscription_status
-- - parents.phone removed

ALTER TABLE users RENAME COLUMN email TO login_id;
ALTER TABLE users RENAME CONSTRAINT uk_users_email TO uk_users_login_id;

UPDATE users
SET login_id = split_part(login_id, '@', 1)
WHERE login_id LIKE '%@%';

ALTER TABLE households
    ADD COLUMN external_member_id VARCHAR(100),
    ADD COLUMN subscription_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE households
    ADD CONSTRAINT uk_households_external_member_id UNIQUE (external_member_id),
    ADD CONSTRAINT chk_households_subscription_status
        CHECK (subscription_status IN ('ACTIVE', 'PAST_DUE', 'CANCELED', 'UNKNOWN'));

ALTER TABLE parents DROP COLUMN phone;
