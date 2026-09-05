-- liquibase formatted: sql

--changeset rusmar21:training-001-create-schema dbms:postgresql
CREATE SCHEMA IF NOT EXISTS training

--rollback DROP SCHEMA IF EXISTS training