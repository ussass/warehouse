CREATE TABLE users
(
    id         BIGSERIAL    NOT NULL PRIMARY KEY,
    login           VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    roles           VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS category
(
    id   BIGSERIAL    NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS goods
(
    id            BIGSERIAL    NOT NULL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    price         BIGINT       NOT NULL,
    "category_id" BIGINT REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS storage
(
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    row        INT    NOT NULL,
    place      INT    NOT NULL,
    amount     INT    NOT NULL,
    "goods_id" BIGINT REFERENCES goods (id)
);
