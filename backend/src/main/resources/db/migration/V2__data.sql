INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO roles (name)
VALUES ('ROLE_ANONYMOUS');

INSERT INTO users (id, username, email, password)
VALUES (DEFAULT, 'toto', 'toto@gmail.com', '$2y$10$zXH3.kVPYuN2W/Ge2AajoOW.XfsSjlMCOJmoN6GRXDq1I49IBe0/S');

INSERT INTO users (id, username, email, password)
VALUES (DEFAULT, 'tata', 'tata@gmail.com', '$2y$10$5p.uOY9VVrJaLxqVpU6iae3vTzw16SoaBQMv1o1CkKhJLonZRG/Z6');

INSERT INTO users (id, username, email, password)
VALUES (DEFAULT, 'tutu', 'tutu@gmail.com', '$2y$10$WHo/rOI5YCeiepz/0drvfOZXYnyOgwz0OEi9zulO4Vg/EVpbuQl5m');

INSERT INTO user_roles (role_name, user_id)
VALUES ('ROLE_ADMIN', (SELECT id from users WHERE username = 'toto')),
       ('ROLE_USER', (SELECT id from users WHERE username = 'tata')),
       ('ROLE_USER', (SELECT id from users WHERE username = 'tutu'));
