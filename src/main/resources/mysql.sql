
CREATE DATABASE if not exists vote;
use vote;

DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS lunch;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;


CREATE TABLE users
(
    id               INT(8) not null AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255)                 NOT NULL,
    email            VARCHAR(255)                 NOT NULL,
    password         VARCHAR(255)                 NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    enabled          BOOL DEFAULT TRUE       NOT NULL
);

CREATE TABLE user_roles
(
    user_id INT(8) not null AUTO_INCREMENT PRIMARY KEY,
    role    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant (
                            id          INT(8) not null AUTO_INCREMENT PRIMARY KEY,
                            name   VARCHAR(255) NOT NULL
);

CREATE TABLE menu (
                      id          INT(8) not null AUTO_INCREMENT PRIMARY KEY,
                      menu_date   TIMESTAMP NOT NULL,
                      restaurant_id INTEGER NOT NULL,
                      FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);



CREATE TABLE lunch (
                       id          INT(8) not null AUTO_INCREMENT PRIMARY KEY,
                       name   VARCHAR(255) NOT NULL,
                       price INTEGER NOT NULL,
                       menu_id INTEGER NOT NULL,
                       FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE vote (
                      id          INT(8) not null AUTO_INCREMENT PRIMARY KEY,
                      date_time   TIMESTAMP NOT NULL,
                      user_id INTEGER NOT NULL,
                      menu_id INTEGER NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                      FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

INSERT INTO USERS (ID, EMAIL, NAME, PASSWORD) VALUES
(1, 'user@yandex.ru', 'User', '$2a$10$Sh0ZD2NFrzRRJJEKEWn8l.92ROEuzlVyzB9SV1AM8fdluPR0aC1ni'),
(2, 'admin@gmail.com', 'Admin', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju');

-- select * from users;

INSERT INTO USER_ROLES (ROLE, USER_ID) VALUES ('ROLE_USER', 1), ('ROLE_ADMIN', 2);

-- select * from user_roles;

INSERT INTO RESTAURANT (ID,NAME) VALUES (1,'McDonalds'), (2,'Шаляпин'), (3,'Васаби');

-- select * from restaurant;

INSERT INTO MENU (ID, MENU_DATE, RESTAURANT_ID) VALUES
(1, '2019-05-11', 1), (2, '2019-05-11', 1),
(3, '2019-05-11', 2), (4, '2019-05-11', 2),
(5, '2019-05-11', 3), (6, '2019-05-11', 3);

-- select * from menu;

INSERT INTO LUNCH (ID, NAME, PRICE, MENU_ID) VALUES
( 1, 'Гамбургер', 350, 1), (2, 'Фри', 95, 1),
(3, 'Вчерашний Гамбургер', 340, 1), (4, 'Вчерашнее Фри', 90, 1),
(5, 'Суп', 650, 2), (6, 'Второе', 750, 2),
(7, 'Вчерашний Суп', 660, 3), (8, 'Вчерашнее Второе', 705, 3),
(9, 'Суши', 450, 4), (10, 'Лосось', 560, 4),
(11, 'Вчерашний Суши', 455, 5), (12, 'Вчерашний Лосось', 520, 5);

-- select * from lunch;

INSERT INTO VOTE (ID, date_time , user_id, menu_id) VALUES
( 1, '2019-05-11', 1, 1), (2, '2019-05-11', 1, 2);

-- select * from vote;