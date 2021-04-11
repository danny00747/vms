/*
 CREATE DOMAIN email_regex AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN username_regex AS VARCHAR(25)
    CHECK (VALUE ~ '^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$');

 */

DROP DOMAIN IF EXISTS dRoles CASCADE;
DROP TABLE IF EXISTS teacher CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS town CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE DOMAIN DRoles VARCHAR(25) CHECK (VALUE IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'));

SET timezone = 'Europe/Brussels';

CREATE TABLE teacher
(
    id    uuid DEFAULT uuid_generate_v4() primary key,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
    id       uuid DEFAULT uuid_generate_v4() primary key,
    username VARCHAR(32)  NOT NULL UNIQUE ,
    email    VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    activated BOOLEAN DEFAULT FALSE NOT NULL,
    activation_key VARCHAR(36),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
    name DRoles primary key,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles
(
    role_name VARCHAR(25),
    user_id   uuid,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_name, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (name) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE town
(
    postcode integer primary key,
    name     VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (name)
);

CREATE TABLE address
(
    id           uuid DEFAULT uuid_generate_v4() primary key,
    road         VARCHAR(50) NOT NULL,
    post_box     integer     NOT NULL,
    house_number integer     NOT NULL,
    postcode     integer,
    user_id      uuid,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (postcode) REFERENCES town (postcode) ON UPDATE CASCADE ON DELETE CASCADE
);
