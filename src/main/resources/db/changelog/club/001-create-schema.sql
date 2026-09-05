-- liquibase formatted: sql

--changeset rusmar21:club-001-create-schema dbms:postgresql
CREATE SCHEMA IF NOT EXISTS club

--rollback DROP SCHEMA IF EXISTS club