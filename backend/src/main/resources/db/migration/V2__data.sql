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
       ('S5-CT-AF-B3', 5, true, false, 3, DEFAULT),
       ('S5-CT-AF-B4', 5, true, false, 4, DEFAULT),
       ('S7-CT-AF-B4', 7, true, false, 4, DEFAULT),
       ('S4-CT-AT-B1', 4, true, true, 1, DEFAULT),
       ('S9-CT-AF-B3', 9, true, false, 3, DEFAULT),
       ('S9-CT-AF-B4', 9, true, false, 4, DEFAULT),
       ('S4-CT-AF-B1', 4, true, false, 1, DEFAULT);

INSERT INTO pricing_class (class_name, daily_fine, price_by_km, allowed_km_per_day, cost_per_day, created_at)
VALUES ('CLASS_A', 25, 20, 40, 300, DEFAULT),
       ('CLASS_B', 20, 15, 40, 230, DEFAULT),
       ('CLASS_C', 15, 10, 40, 110, DEFAULT);

INSERT INTO models (id, model_type, brand, model_option, pricing_class, created_at)
VALUES (DEFAULT, 'Corsa', 'Opel', 'S5-CT-AF-B2', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Zafira', 'Opel', 'S7-CT-AF-B4', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Fiesta', 'Ford', 'S4-CT-AT-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Aygo', 'Toyota', 'S4-CT-AT-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Captur', 'Renault', 'S5-CT-AF-B2', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Twingo', 'Renault', 'S4-CT-AT-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Mokka', 'Opel', 'S5-CT-AF-B3', 'CLASS_B', DEFAULT),
       (DEFAULT, '5008', 'Peugeot', 'S7-CT-AF-B4', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Stonic', 'Kia', 'S5-CT-AF-B3', 'CLASS_A', DEFAULT),
       (DEFAULT, 'V40', 'Volvo', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, '318', 'BMW', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'C-Class', 'Mercedes-Benz', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'C3', 'Citroen', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'CR-V', 'Honda', 'S5-CT-AF-B3', 'CLASS_C', DEFAULT),
       (DEFAULT, 'C-Max', 'Ford', 'S5-CT-AF-B2', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Vivaro', 'Opel', 'S9-CT-AF-B3', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Pulsar', 'Nissan', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Cooper Cabriao', 'Mini', 'S4-CT-AT-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Traffic', 'Renault', 'S9-CT-AF-B4', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Qashqai', 'Nissan', 'S5-CT-AF-B3', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Renegade', 'Jeep', 'S5-CT-AF-B3', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Megane', 'Renault', 'S5-CT-AF-B3', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Juke', 'Nissan', 'S5-CT-AF-B3', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Yaris', 'Toyota', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Espace', 'Renault', 'S5-CT-AF-B4', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Astra', 'Opel', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Golf', 'Volkswagen', 'S5-CT-AF-B2', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Touran', 'Volkswagen', 'S5-CT-AF-B4', 'CLASS_A', DEFAULT),
       (DEFAULT, 'T-ROC', 'Volkswagen', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Camry', 'Toyata', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Mondeo', 'Ford', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'A4', 'Audi', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Focus', 'Ford', 'S5-CT-AF-B4', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Caddy', 'Volkswagen', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Baleno', 'Suzuki', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Avensis', 'Toyota', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Spark', 'Chevrolet', 'S4-CT-AT-B2', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Passat', 'Volkswagen', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Evalia', 'Nissan', 'S7-CT-AF-B4', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Corolla', 'Toyota', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'X1', 'BMW', 'S5-CT-AF-B3', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Auris', 'Toyota', 'S5-CT-AF-B3', 'CLASS_C', DEFAULT),
       (DEFAULT, 'Punto', 'Fiat', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Venga', 'Kia', 'S5-CT-AF-B2', 'CLASS_A', DEFAULT),
       (DEFAULT, 'Civic', 'Honda', 'S5-CT-AF-B2', 'CLASS_B', DEFAULT),
       (DEFAULT, 'Polo', 'Volkswagen', 'S4-CT-AF-B1', 'CLASS_C', DEFAULT);

INSERT INTO cars (id, license_plate, made_in_year, is_damaged, purchased_price, model_id, created_at)
VALUES (DEFAULT, '1-ABC-001', 2018, false, 14217.99,
        (SELECT id FROM models WHERE model_type = 'Zafira' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '1-BDC-123', 2018, false, 21444.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '2-AAS-012', 2019, false, 19231.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '3-QVW-23', 2018, false, 24184.99,
        (SELECT id FROM models WHERE model_type = 'Fiesta' AND brand = 'Ford'), DEFAULT),

       (DEFAULT, '5-WTS-191', 2020, false, 12444.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '7-CAH-11', 2019, false, 17914.99,
        (SELECT id FROM models WHERE model_type = 'Aygo' AND brand = 'Toyota'), DEFAULT),

       (DEFAULT, '7-AUQ-192', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Captur' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '3-EUK-192', 2019, false, 11924.99,
        (SELECT id FROM models WHERE model_type = 'Twingo' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '1-XZF-92', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Mokka' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '2-ZEX-987', 2019, false, 19231.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '3-GTF-16', 2016, false, 12744.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '7-CXF-112', 2019, false, 17914.99,
        (SELECT id FROM models WHERE model_type = 'Mokka' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '7-CLF-153', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = '5008' AND brand = 'Peugeot'), DEFAULT),

       (DEFAULT, '9-WHS-121', 2016, false, 12444.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '2-KAE-012', 2019, false, 19231.99,
        (SELECT id FROM models WHERE model_type = 'Polo' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '7-FRT-32', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'C-Class' AND brand = 'Mercedes-Benz'), DEFAULT),

       (DEFAULT, '7-CYU-392', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'V40' AND brand = 'Volvo'), DEFAULT),

       (DEFAULT, '5-ZRT-45', 2016, false, 12444.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '2-MLQ-109', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Mokka' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '7-CVZ-122', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'C3' AND brand = 'Citroen'), DEFAULT),

       (DEFAULT, '3-VVZ-872', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Stonic' AND brand = 'Kia'), DEFAULT),

       (DEFAULT, '1-AZR-871', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = '318' AND brand = 'BMW'), DEFAULT),

       (DEFAULT, '5-KNB-82', 2016, false, 12444.99,
        (SELECT id FROM models WHERE model_type = 'Corsa' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '3-CLK-81', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Vivaro' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '3-KJH-281', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Vivaro' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, 'Z-ZLO-871', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Pulsar' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '4-ZEC-981', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Cooper Cabriao' AND brand = 'Mini'), DEFAULT),

       (DEFAULT, '2-VBZ-152', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'CR-V' AND brand = 'Honda'), DEFAULT),

       (DEFAULT, '1-KJW-132', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Traffic' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '4-CZA-992', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Qashqai' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '7-MQW-173', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Renegade' AND brand = 'Jeep'), DEFAULT),

       (DEFAULT, '2-CVM-234', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Vivaro' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '4-CSQ-242', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Vivaro' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '6-CUF-192', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Megane' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '2-ZCS-111', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Juke' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '1-ERT-12', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Yaris' AND brand = 'Toyota'), DEFAULT),

       (DEFAULT, '3-ZSQ-156', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Espace' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '9-KJU-432', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Espace' AND brand = 'Renault'), DEFAULT),

       (DEFAULT, '4-CZA-44', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Passat' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '3-PLM-131', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Auris' AND brand = 'Toyota'), DEFAULT),

       (DEFAULT, '2-CGF-872', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Spark' AND brand = 'Chevrolet'), DEFAULT),

       (DEFAULT, '4-CDS-55', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Astra' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '4-CSA-223', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Astra' AND brand = 'Opel'), DEFAULT),

       (DEFAULT, '7-FDE-221', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Evalia' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '7-XSQ-771', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Avensis' AND brand = 'Toyota'), DEFAULT),

       (DEFAULT, '7-CUF-192', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Spark' AND brand = 'Chevrolet'), DEFAULT),

       (DEFAULT, '7-ZWQ-982', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Caddy' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '7-KJX-125', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Caddy' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '5-CVK-982', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Corolla' AND brand = 'Toyota'), DEFAULT),

       (DEFAULT, '2-CVR-621', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Golf' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '3-CVM-129', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Touran' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '7-OIZ-236', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Touran' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '4-KJN-231', 2019, false, 11354.99,
        (SELECT id FROM models WHERE model_type = 'Focus' AND brand = 'Ford'), DEFAULT),

       (DEFAULT, '2-CVW-982', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'T-ROC' AND brand = 'Volkswagen'), DEFAULT),

       (DEFAULT, '2-MLZ-542', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'A4' AND brand = 'Audi'), DEFAULT),

       (DEFAULT, '3-MPO-873', 2019, false, 17944.99,
        (SELECT id FROM models WHERE model_type = 'Mondeo' AND brand = 'Ford'), DEFAULT),

       (DEFAULT, '4-BNK-341', 2019, false, 12944.99,
        (SELECT id FROM models WHERE model_type = 'C-Max' AND brand = 'Ford'), DEFAULT),

       (DEFAULT, '2-VBA-531', 2017, false, 1124.99,
        (SELECT id FROM models WHERE model_type = 'Focus' AND brand = 'Ford'), DEFAULT),

       (DEFAULT, '5-LOI-33', 2019, false, 17233.99,
        (SELECT id FROM models WHERE model_type = 'Punto' AND brand = 'Fiat'), DEFAULT),

       (DEFAULT, '2-HYI-432', 2019, false, 12244.99,
        (SELECT id FROM models WHERE model_type = 'Spark' AND brand = 'Chevrolet'), DEFAULT),

       (DEFAULT, '2-CVS-542', 2019, false, 17344.99,
        (SELECT id FROM models WHERE model_type = 'A4' AND brand = 'Audi'), DEFAULT),

       (DEFAULT, '2-GTU-144', 2019, false, 11244.99,
        (SELECT id FROM models WHERE model_type = 'X1' AND brand = 'BMW'), DEFAULT),

       (DEFAULT, '3-FRE-342', 2019, false, 12922.99,
        (SELECT id FROM models WHERE model_type = 'Punto' AND brand = 'Fiat'), DEFAULT),

       (DEFAULT, '7-VCF-242', 2019, false, 12924.99,
        (SELECT id FROM models WHERE model_type = 'Venga' AND brand = 'Kia'), DEFAULT),

       (DEFAULT, '4-CZD-93', 2019, false, 1344.99,
        (SELECT id FROM models WHERE model_type = 'Baleno' AND brand = 'Suzuki'), DEFAULT),

       (DEFAULT, '1-REZ-99', 2019, false, 13924.99,
        (SELECT id FROM models WHERE model_type = 'Evalia' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '2-CUF-88', 2019, false, 15421.99,
        (SELECT id FROM models WHERE model_type = 'Evalia' AND brand = 'Nissan'), DEFAULT),

       (DEFAULT, '8-SBE-018', 2017, false, 12254.99,
        (SELECT id FROM models WHERE model_type = 'Focus' AND brand = 'Ford'), DEFAULT);

INSERT INTO booking (id, cancellation_date, booking_state, withdrawal_date, return_date, user_id, car_id, created_at)
VALUES (DEFAULT, NULL, 'OPEN', '2021-06-06T23:00:00.000Z', '2021-06-09T23:00:00.000Z',
        (SELECT id FROM users WHERE username = 'toto'),
        (SELECT id FROM cars WHERE license_plate = '3-FRE-342'), DEFAULT),

       (DEFAULT, NULL, 'OPEN', '2021-10-20T23:00:00.000Z', '2021-10-25T23:00:00.000Z',
        (SELECT id FROM users WHERE username = 'tata'),
        (SELECT id FROM cars WHERE license_plate = '7-VCF-242'), DEFAULT),

       (DEFAULT, NULL, 'OPEN', '2021-09-15T23:00:00.000Z', '2021-09-18T23:00:00.000Z',
        (SELECT id FROM users WHERE username = 'titi'),
        (SELECT id FROM cars WHERE license_plate = '2-CUF-88'), DEFAULT);
