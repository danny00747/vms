INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_ANONYMOUS');

INSERT INTO users (id, username, email, password, activated, activation_key, created_at)
VALUES (DEFAULT, 'toto', 'toto@gmail.com', '$2y$10$zXH3.kVPYuN2W/Ge2AajoOW.XfsSjlMCOJmoN6GRXDq1I49IBe0/S', true, null,
        DEFAULT),
       (DEFAULT, 'tata', 'tata@gmail.com', '$2y$10$5p.uOY9VVrJaLxqVpU6iae3vTzw16SoaBQMv1o1CkKhJLonZRG/Z6', true, null,
        DEFAULT),
       (DEFAULT, 'tutu', 'tutu@gmail.com', '$2y$10$WHo/rOI5YCeiepz/0drvfOZXYnyOgwz0OEi9zulO4Vg/EVpbuQl5m', false,
        '54947df8-0e9e-4471-a2f9-9af509fb5889',
        '2020-03-17 19:10:21-07'),
       (DEFAULT, 'titi', 'titi@gmail.com', '$2y$10$dU.y3rblsNc/rg4b24jw9u0u7ULgt6l84z6WJ3iSEdQov5wjK/WUm', false,
        '12737df8-0e9e-4471-a2f9-9af509fb5114',
        '2019-07-22 22:33:25-07');

INSERT INTO user_roles (role_name, user_id)
VALUES ('ROLE_ADMIN', (SELECT id FROM users WHERE username = 'toto')),
       ('ROLE_USER', (SELECT id FROM users WHERE username = 'tata')),
       ('ROLE_USER', (SELECT id FROM users WHERE username = 'tutu')),
       ('ROLE_USER', (SELECT id FROM users WHERE username = 'titi'));

INSERT INTO town (postcode, name)
VALUES (1348, 'Louvain-la-Neuve'),
       (1300, 'Wavre'),
       (1370, 'Jodoigne'),
       (1400, 'Nivelles'),
       (1480, 'Tubize');

INSERT INTO address (id, road, post_box, house_number, postcode, user_id, created_at)
VALUES (DEFAULT, 'rue de la place', 7, 7, 1300, (SELECT id FROM users WHERE username = 'toto'), DEFAULT),
       (DEFAULT, 'avenue des francs de la roi', 21, 21, 1348, (SELECT id FROM users WHERE username = 'tata'), DEFAULT),
       (DEFAULT, 'rue saint reine', 13, 13, 1370, (SELECT id FROM users WHERE username = 'tutu'), DEFAULT),
       (DEFAULT, 'place des fontaines', 34, 34, 1480, (SELECT id FROM users WHERE username = 'titi'), DEFAULT);

INSERT INTO models_options (option_code, seats_number, has_air_conditioner, is_automatic, bags_number, created_at)
VALUES ('S4-CT-AT-B2', 4, true, true, 2, DEFAULT),
       ('S5-CT-AF-B2', 5, true, false, 2, DEFAULT),
       ('S7-CT-AF-B4', 7, true, false, 4, DEFAULT),
       ('S4-CT-AT-B1', 4, true, true, 1, DEFAULT),
       ('S4-CT-AF-B1', 4, true, false, 1, DEFAULT);


INSERT INTO models (id, model_type, brand, model_option, created_at)
VALUES (DEFAULT, 'Focus', 'Ford', 'S4-CT-AF-B1', DEFAULT),
       (DEFAULT, 'Corsa', 'Opel', 'S5-CT-AF-B2', DEFAULT),
       (DEFAULT, 'Zafira', 'Opel', 'S7-CT-AF-B4', DEFAULT),
       (DEFAULT, 'Fiesta', 'Ford', 'S4-CT-AT-B2', DEFAULT),
       (DEFAULT, 'Civic', 'Honda', 'S5-CT-AF-B2', DEFAULT),
       (DEFAULT, 'Polo', 'Volkswagen', 'S4-CT-AF-B1', DEFAULT);

INSERT INTO cars (id, license_plate, made_in_year, is_damaged, purchased_price, model_id, created_at)
VALUES (DEFAULT, '1-ABC-003', 2018, false, 14217.99,
        (SELECT id FROM models WHERE model_type = 'Zafira' AND brand = 'Opel'),
        DEFAULT),
       (DEFAULT, '1-BDC-113', 2018, false, 21444.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'),
        DEFAULT),
       (DEFAULT, '2-KAE-012', 2019, false, 19231.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'),
        DEFAULT),
       (DEFAULT, '3-QVC-413', 2018, false, 24184.99,
        (SELECT id FROM models WHERE model_type = 'Fiesta' AND brand = 'Ford'),
        DEFAULT),
       (DEFAULT, '5-WTS-191', 2016, false, 12444.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'),
        DEFAULT),
       (DEFAULT, '7-CUF-191', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Civic' AND brand = 'Honda'),
        DEFAULT),
       (DEFAULT, '8-SBE-018', 2017, false, 17274.99,
        (SELECT id FROM models WHERE model_type = 'Focus' AND brand = 'Ford'),
        DEFAULT);
