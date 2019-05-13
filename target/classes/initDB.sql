
-- PostgreSQL

DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS lunch;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE users
(
   id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
   name             VARCHAR                 NOT NULL,
   email            VARCHAR                 NOT NULL,
   password         VARCHAR                 NOT NULL,
   registered       TIMESTAMP DEFAULT now() NOT NULL,
   enabled          BOOL DEFAULT TRUE       NOT NULL
 );

CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
 (
   user_id INTEGER NOT NULL,
   role    VARCHAR,
   CONSTRAINT user_roles_idx UNIQUE (user_id, role),
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
 );

CREATE TABLE restaurant (
   id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
   name   VARCHAR NOT NULL
 );

CREATE TABLE menu (
   id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
   menu_date   TIMESTAMP NOT NULL,
   restaurant_id INTEGER NOT NULL,
   FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
 );



CREATE TABLE lunch (
   id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
   name   VARCHAR NOT NULL,
   price INTEGER NOT NULL,
   menu_id INTEGER NOT NULL,
   FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
 );

CREATE TABLE vote (
   id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
   date_time   TIMESTAMP NOT NULL,
   user_id INTEGER NOT NULL,
   menu_id INTEGER NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
   FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
 );





INSERT INTO USERS (ID, EMAIL, NAME, PASSWORD) VALUES
 (0, 'user@yandex.ru', 'User', '$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni'),
 (1, 'admin@gmail.com', 'Admin', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju');

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES ('ROLE_USER', 0), ('ROLE_ADMIN', 1);

INSERT INTO RESTAURANT (ID,NAME) VALUES (0,'McDonalds'), (1,'Шаляпин'), (2,'Васаби');

INSERT INTO MENU (ID, MENU_DATE, RESTAURANT_ID) VALUES
 (0, '2019-05-05 10:00:00', 0), (1, '2018-11-19', 0),
 (2, '2019-05-05 10:00:00', 1), (3, '2018-11-19', 1),
 (4, '2019-05-05 10:00:00', 2), (5, '2018-11-19', 2);

INSERT INTO LUNCH (NAME, PRICE, MENU_ID) VALUES
 ('Гамбургер', 350, 1), ('Фри', 95, 1),
 ('Вчерашний Гамбургер', 340, 1), ('Вчерашнее Фри', 90, 1),
 ('Суп', 650, 2), ('Второе', 750, 2),
 ('Вчерашний Суп', 660, 3), ('Вчерашнее Второе', 705, 3),
 ('Суши', 450, 4), ('Лосось', 560, 4),
 ('Вчерашний Суши', 455, 5), ('Вчерашний Лосось', 520, 5);


INSERT INTO VOTE (date_time , user_id, menu_id) VALUES
( '2019-05-11', 1, 1), ('2019-05-11', 1, 2);

-- SELECT * FROM users;
-- select * from user_roles;
-- select * from restaurant;
-- select * from menu;
-- select * from lunch;
-- select * from vote;