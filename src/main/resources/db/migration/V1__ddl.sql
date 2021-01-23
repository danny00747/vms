/*
 CREATE DOMAIN email_regex AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN username_regex AS VARCHAR(25)
    CHECK (VALUE ~ '^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$');

 */

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE DOMAIN DRoles VARCHAR(25) CHECK (VALUE IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'));

CREATE TABLE teacher
(
    id    uuid DEFAULT uuid_generate_v4() primary key,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id       uuid DEFAULT uuid_generate_v4() primary key,
    username VARCHAR(32)  NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    name DRoles primary key
);

CREATE TABLE user_roles
(
    role_name VARCHAR(25),
    user_id   uuid,
    PRIMARY KEY (role_name, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (name) ON UPDATE CASCADE ON DELETE CASCADE

);

