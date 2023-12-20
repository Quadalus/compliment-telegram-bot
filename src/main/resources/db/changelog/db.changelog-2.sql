--liquibase formatted sql

--changeset quadalus:3
ALTER TABLE good_morning_wishes
    ADD COLUMN created_on TIMESTAMP WITH TIME ZONE;

ALTER TABLE good_morning_wishes
    ADD COLUMN updated_on TIMESTAMP WITH TIME ZONE;

--changeset quadalus:5
ALTER TABLE good_morning_wishes
    ADD COLUMN source VARCHAR(50) NOT NULL;

--changeset quadalus:6
ALTER TABLE good_morning_wishes
    ADD CONSTRAINT unique_text UNIQUE (text);