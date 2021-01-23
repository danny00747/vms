/*
 CREATE DOMAIN email_regex AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN username_regex AS VARCHAR(25)
    CHECK (VALUE ~ '^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$');

 */

CREATE TABLE teacher
(
    id    serial primary key,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id       serial primary key,
    username VARCHAR(25)  NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    name VARCHAR(25) primary key
);

CREATE TABLE user_roles
(
    role_name VARCHAR(25),
    user_id   int,
    PRIMARY KEY (role_name, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (name) ON UPDATE CASCADE ON DELETE CASCADE

);

