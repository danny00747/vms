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

