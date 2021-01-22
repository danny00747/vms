CREATE DOMAIN email_regex AS VARCHAR(255)
    CHECK(VALUE ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN username_regex AS VARCHAR(25)
    CHECK(VALUE ~ '^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$');


CREATE TABLE course
(
    id       serial primary key,
    NAME     VARCHAR(255),
    rate     INT2 NOT NULL,
    workload INT4 NOT NULL
);

CREATE TABLE teacher
(
    id    serial primary key,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id    serial primary key,
    username VARCHAR(25) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    name    VARCHAR(25) primary key,
    user_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE

);

