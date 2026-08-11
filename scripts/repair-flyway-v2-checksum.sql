-- Run after V2__seed_dev.sql content changes (local dev only)
UPDATE flyway_schema_history
SET checksum = 1558044216
WHERE version = '2';
