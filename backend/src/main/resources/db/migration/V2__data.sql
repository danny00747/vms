INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO roles (name)
VALUES ('ROLE_ANONYMOUS');

INSERT INTO users (id, username, email, password, created_at)
VALUES (DEFAULT, 'toto', 'toto@gmail.com', '$2y$10$zXH3.kVPYuN2W/Ge2AajoOW.XfsSjlMCOJmoN6GRXDq1I49IBe0/S', DEFAULT);

INSERT INTO users (id, username, email, password, created_at)
VALUES (DEFAULT, 'tata', 'tata@gmail.com', '$2y$10$5p.uOY9VVrJaLxqVpU6iae3vTzw16SoaBQMv1o1CkKhJLonZRG/Z6', DEFAULT);

INSERT INTO users (id, username, email, password, created_at)
VALUES (DEFAULT, 'tutu', 'tutu@gmail.com', '$2y$10$WHo/rOI5YCeiepz/0drvfOZXYnyOgwz0OEi9zulO4Vg/EVpbuQl5m', DEFAULT);

INSERT INTO users (id, username, email, password, created_at)
VALUES (DEFAULT, 'titi', 'titi@gmail.com', '$2y$10$dU.y3rblsNc/rg4b24jw9u0u7ULgt6l84z6WJ3iSEdQov5wjK/WUm', DEFAULT);

INSERT INTO user_roles (role_name, user_id)
VALUES ('ROLE_ADMIN', (SELECT id from users WHERE username = 'toto')),
       ('ROLE_USER', (SELECT id from users WHERE username = 'tata')),
       ('ROLE_USER', (SELECT id from users WHERE username = 'tutu')),
       ('ROLE_USER', (SELECT id from users WHERE username = 'toto'));;

INSERT INTO town (postcode, name)
VALUES (1348, 'Louvain-la-Neuve'),
       (1300, 'Wavre'),
       (1370, 'Jodoigne'),
       (1400, 'Nivelles'),
       (1480, 'Tubize');

INSERT INTO address (id, road, post_box, house_number, postcode, user_id, created_at)
VALUES (DEFAULT, 'rue de la place', 7, 7, 1300, (SELECT id from users WHERE username = 'toto'), DEFAULT),
       (DEFAULT, 'avenue des francs de la roi', 21, 21, 1348, (SELECT id from users WHERE username = 'tata'), DEFAULT),
       (DEFAULT, 'rue saint reine', 13, 13, 1370, (SELECT id from users WHERE username = 'tutu'), DEFAULT),
       (DEFAULT, 'place des fontaines', 34, 34, 1480, (SELECT id from users WHERE username = 'titi'), DEFAULT);
