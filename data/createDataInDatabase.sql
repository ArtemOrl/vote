DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
USE test;
DROP TABLE IF EXISTS part;
CREATE TABLE `test`.`part` (
`id` INT(8) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`need` BIT(1) NOT NULL DEFAULT b'0',
`quantity` INT(8) NOT NULL,
PRIMARY KEY (`id`))
COLLATE='utf8_general_ci';

INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Материнская плата', 1, 3);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Звуковвая карта', 0, 2);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Процессор Intel Pentium', 1, 2);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Подсветка корпуса', 0, 0);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('HDD диск', 0, 1);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Корпус', 1, 10);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Память', 1, 10);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('SSD диск', 1, 15);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Видеокарта', 0, 7);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('GeForce RTX 2070 8 GB', 0, 3);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Процессор AMD', 1, 7);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Процессор Intel Core i9', 1, 1);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Материнская плата Gyga', 1, 2);
INSERT INTO `test`.`part` (`name`,`need`,`quantity`) VALUES ('Монитор', 1, 23);
