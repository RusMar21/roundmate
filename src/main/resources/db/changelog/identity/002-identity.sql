--liquibase formatted: sql

--changeset rusmar21:create-account-table dbms:postgresql runInTransaction:true
CREATE TYPE identity.account_status AS ENUM ('ACTIVE');

CREATE TABLE IF NOT EXISTS identity.account
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY,
    status        identity.account_status NOT NULL DEFAULT 'ACTIVE'::identity.account_status,
    email         TEXT NOT NULL,
    name          TEXT NOT NULL,
    surname       TEXT NOT NULL,
    password_hash TEXT NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_accounts
        PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS account_email_idx ON identity.account(email);