-------------------------------------------------
-- Scripts for database creation
-- November 2020 - olvd
-- 2021 olvd
--------------------------------------------------

-------------------------------------------------
--   Create domains & extensions
-------------------------------------------------
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE DOMAIN d_roles VARCHAR(25) CHECK (VALUE IN ('ROLE_ADMIN', 'ROLE_USER', 'ROLE_ANONYMOUS'));
CREATE DOMAIN d_price numeric(8, 2) CHECK (VALUE > 0.0);

SET timezone = 'Europe/Brussels';

CREATE TABLE teacher
(
    id         uuid        DEFAULT uuid_generate_v4() PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-------------------------------------------------
--   Create users table
-------------------------------------------------
CREATE TABLE users
(
    id             uuid        DEFAULT uuid_generate_v4() PRIMARY KEY,
    username       VARCHAR(32)               NOT NULL UNIQUE,
    email          VARCHAR(255)              NOT NULL UNIQUE,
    password       VARCHAR(255)              NOT NULL,
    activated      BOOLEAN     DEFAULT FALSE NOT NULL,
    activation_key VARCHAR(36),
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-------------------------------------------------
--   Create roles table
-------------------------------------------------
CREATE TABLE roles
(
    name       d_roles PRIMARY KEY,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-------------------------------------------------
--   Create user_roles table
-------------------------------------------------
CREATE TABLE user_roles
(
    role_name  VARCHAR(25),
    user_id    uuid,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_name, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES roles (name) ON UPDATE CASCADE ON DELETE CASCADE

);

-------------------------------------------------
--   Create town table
-------------------------------------------------
CREATE TABLE town
(
    postcode   integer primary key,
    name       VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (name)
);

-------------------------------------------------
--   Create address table
-------------------------------------------------
CREATE TABLE address
(
    id           uuid        DEFAULT uuid_generate_v4() PRIMARY KEY,
    road         VARCHAR(50) NOT NULL,
    post_box     INTEGER     NOT NULL,
    house_number INTEGER     NOT NULL,
    postcode     INTEGER,
    user_id      uuid,
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (postcode) REFERENCES town (postcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-------------------------------------------------
--   Create models table
-------------------------------------------------
CREATE TABLE models
(
    id         uuid        DEFAULT uuid_generate_v4() PRIMARY KEY,
    model_type VARCHAR(128) NOT NULL,
    brand      VARCHAR(128) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (model_type, brand)
);

-------------------------------------------------
--   Create car table
-------------------------------------------------
CREATE TABLE cars
(
    id              uuid        DEFAULT uuid_generate_v4() PRIMARY KEY,
    license_plate   VARCHAR(9)                NOT NULL UNIQUE,
    made_in_year    SMALLINT                  NOT NULL,
    purchased_price d_price                   NOT NULL,
    is_damaged      BOOLEAN     DEFAULT FALSE NOT NULL,
    model_id        uuid,
    created_at      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (model_id) REFERENCES models (id) ON UPDATE CASCADE ON DELETE CASCADE

);

-------------------------------------------------
--   Create models_options table
-------------------------------------------------
CREATE TABLE models_options
(
    option_code         VARCHAR(36) PRIMARY KEY,
    seats_number        SMALLINT NOT NULL,
    has_air_conditioner BOOLEAN  NOT NULL,
    is_automatic        BOOLEAN  NOT NULL,
    bags_number         SMALLINT NOT NULL,
    created_at          TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-------------------------------------------------
--   Create cars_models_options table
-------------------------------------------------
CREATE TABLE cars_models_options
(
    model_id    uuid,
    option_code VARCHAR(36),
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (model_id, option_code),
    FOREIGN KEY (model_id) REFERENCES models (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (option_code) REFERENCES models_options (option_code) ON UPDATE CASCADE ON DELETE CASCADE
);

/*
 CREATE DOMAIN email_regex AS VARCHAR(255)
    CHECK (VALUE ~ '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE DOMAIN username_regex AS VARCHAR(25)
    CHECK (VALUE ~ '^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$');

*/

