-- 0000-init-users-table.sql
-- Initialize users table.

CREATE TABLE users(
    id              BIGSERIAL PRIMARY KEY,
    firstname       VARCHAR(50),
    username        VARCHAR(50) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    hashed          VARCHAR(255) NOT NULL
);
