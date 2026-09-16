--liquibase formatted: sql

--changeset rusmar21:create-account-table dbms:postgresql runInTransaction:true
CREATE TYPE IF NOT EXISTS identity.account_status AS ENUM ('ACTIVE');

CREATE TABLE IF NOT EXISTS identity.account
(
    id            BIGSERIAL PRIMARYKEY,
    stasus        account_status NOT NULL,
    email         TEXT NOT NULL,
    name          TEXT NOT NULL,
    surname       TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL,
    updated_at    TIMESTAMPTZ NOT NULL,
    uuid          UUID NOT NULL,
);

CREATE UNIQUE INDEX IF NOT EXISTS account_uuid_idx ON account(uuid);
CREATE UNIQUE INDEX IF NOT EXISTS account_email_idx ON account(email);