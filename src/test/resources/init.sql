-- MySQL Script generated by MySQL Workbench
-- Tue Feb 18 10:19:47 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema integration-tests-db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `integration-tests-db` ;

-- -----------------------------------------------------
-- Schema integration-tests-db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `integration-tests-db` DEFAULT CHARACTER SET utf8 ;
USE `integration-tests-db` ;

-- -----------------------------------------------------
-- Table `integration-tests-db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `integration-tests-db`.`users` ;

CREATE TABLE IF NOT EXISTS `integration-tests-db`.`users` (
                                                              `user_id` INT NOT NULL AUTO_INCREMENT,
                                                              `username` VARCHAR(45) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` CHAR(60) NOT NULL,
    `solde` DOUBLE NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `integration-tests-db`.`transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `integration-tests-db`.`transactions` ;

CREATE TABLE IF NOT EXISTS `integration-tests-db`.`transactions` (
                                                                     `transaction_id` INT NOT NULL AUTO_INCREMENT,
                                                                     `user_id_sender` INT NOT NULL,
                                                                     `user_id_receiver` INT NOT NULL,
                                                                     `description` VARCHAR(45) NOT NULL,
    `amount` DOUBLE NOT NULL,
    PRIMARY KEY (`transaction_id`),
    INDEX `fk_user_id_sender_idx` (`user_id_sender` ASC) VISIBLE,
    INDEX `fk_user_id_receiver_idx` (`user_id_receiver` ASC) VISIBLE,
    CONSTRAINT `fk_user_id_sender`
    FOREIGN KEY (`user_id_sender`)
    REFERENCES `integration-tests-db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_user_id_receiver`
    FOREIGN KEY (`user_id_receiver`)
    REFERENCES `integration-tests-db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `integration-tests-db`.`user_relations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `integration-tests-db`.`user_relations` ;

CREATE TABLE IF NOT EXISTS `integration-tests-db`.`user_relations` (
                                                                       `user_id` INT NOT NULL,
                                                                       `friend_id` INT NOT NULL,
                                                                       PRIMARY KEY (`user_id`, `friend_id`),
    INDEX `fk_users_id_2_idx` (`friend_id` ASC) INVISIBLE,
    INDEX `fk_users_id_1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `integration-tests-db`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_friend_id`
    FOREIGN KEY (`friend_id`)
    REFERENCES `integration-tests-db`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Data for table `integration-tests-db`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `integration-tests-db`;
INSERT INTO `integration-tests-db`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (1, 'alice', 'alice@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 10000);
INSERT INTO `integration-tests-db`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (2, 'bob', 'bob@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 20000);
INSERT INTO `integration-tests-db`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (3, 'charlie', 'charlie@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 1000);
INSERT INTO `integration-tests-db`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (4, 'emma', 'emma@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 5000);
INSERT INTO `integration-tests-db`.`users` (`user_id`, `username`, `email`, `password`, `solde`) VALUES (5, 'david', 'david@mail.fr', '$2a$10$sUlcQ2JEzBvNmEhHA/2gJeBuRYKDyETYT2fzYB6csZBaR7K8I7UXS', 4500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `integration-tests-db`.`transactions`
-- -----------------------------------------------------
START TRANSACTION;
USE `integration-tests-db`;
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Cadeau', 12);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 3, 1, 'Concert', 31);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 2, 4, 'Cinéma', 24);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 4, 2, 'Restaurant', 42);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Café', 12.01);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 1, 2, 'Transport', 12.02);
INSERT INTO `integration-tests-db`.`transactions` (`transaction_id`, `user_id_sender`, `user_id_receiver`, `description`, `amount`) VALUES (DEFAULT, 2, 1, 'Diner', 21);

COMMIT;


-- -----------------------------------------------------
-- Data for table `integration-tests-db`.`user_relations`
-- -----------------------------------------------------
START TRANSACTION;
USE `integration-tests-db`;
INSERT INTO `integration-tests-db`.`user_relations` (`user_id`, `friend_id`) VALUES (1, 2);
INSERT INTO `integration-tests-db`.`user_relations` (`user_id`, `friend_id`) VALUES (1, 3);
INSERT INTO `integration-tests-db`.`user_relations` (`user_id`, `friend_id`) VALUES (2, 4);

COMMIT;

