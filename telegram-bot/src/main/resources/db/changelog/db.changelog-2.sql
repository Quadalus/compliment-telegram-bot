--liquibase formatted sql

--changeset quadalus:4
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

--changeset quadalus:7
INSERT INTO telegram_user(chat_id, username, first_name, last_name)
VALUES (439303907, 'AlinaAvd88', 'Алина', 'Авдеева');

--changeset quadalus:8
INSERT INTO telegram_user(chat_id, username, first_name, last_name)
VALUES (436395011, 'quadalus', 'Александр', 'Биккулов');

--changeset quadalus:9
INSERT INTO user_settings(chat_id, is_scheduled, cron_time, source_type, text_param, picture_param)
VALUES (436395011, true, '0 00 23 ? * * *', 'SITE', 'default', 'default');

--changeset quadalus:10
INSERT INTO user_settings(chat_id, is_scheduled, cron_time, source_type, text_param, picture_param)
VALUES (439303907, true, '0 00 06 ? * * *', 'SITE', 'default', 'default');
