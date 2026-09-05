-- liquibase formatted: sql

--changeset rusmar21:identity-001-create-schema dbms:postgresql
CREATE SCHEMA IF NOT EXISTS identity

--rollback DROP SCHEMA IF EXISTS identity