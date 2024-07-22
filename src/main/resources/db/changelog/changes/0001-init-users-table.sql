-- 0000-init-users-table.sql
-- Initialize users table.

CREATE TABLE users(
    id              BIGSERIAL PRIMARY KEY,
    firstname       VARCHAR(50),
    username        VARCHAR(50),
    email           VARCHAR(100),
    hashed          VARCHAR(256),
    salt            VARCHAR(256)
);
