-- MySQL Script generated by MySQL Workbench
-- Tue Feb 18 10:19:47 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `paymybuddy` ;

-- -----------------------------------------------------
-- Schema paymybuddy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8 ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`users` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`users` (
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
-- Table `paymybuddy`.`transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`transactions` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`transactions` (
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
    REFERENCES `paymybuddy`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id_receiver`
    FOREIGN KEY (`user_id_receiver`)
    REFERENCES `paymybuddy`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy`.`user_relations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `paymybuddy`.`user_relations` ;

CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_relations` (
  `user_id` INT NOT NULL,
  `friend_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `friend_id`),
  INDEX `fk_users_id_2_idx` (`friend_id` ASC) INVISIBLE,
  INDEX `fk_users_id_1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_friend_id`
    FOREIGN KEY (`friend_id`)
    REFERENCES `paymybuddy`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;