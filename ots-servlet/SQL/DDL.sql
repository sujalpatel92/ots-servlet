-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema oil
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema oil
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `oil` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `oil` ;

-- -----------------------------------------------------
-- Table `oil`.`Client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oil`.`Client` ;

CREATE TABLE IF NOT EXISTS `oil`.`Client` (
  `Cid` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `First_name` VARCHAR(45) NULL COMMENT '',
  `Last_name` VARCHAR(45) NULL COMMENT '',
  `Street_addr` VARCHAR(45) NULL COMMENT '',
  `City` VARCHAR(25) NULL COMMENT '',
  `State` VARCHAR(25) NULL COMMENT '',
  `Zip` INT NULL COMMENT '',
  `Email` VARCHAR(45) NULL COMMENT '',
  `Cell_number` VARCHAR(10) NULL COMMENT '',
  `Ph_number` VARCHAR(10) NULL COMMENT '',
  `Level` VARCHAR(15) NULL DEFAULT 'Silver' COMMENT '',
  `Oil_owned` DOUBLE NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`Cid`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oil`.`Auth`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oil`.`Auth` ;

CREATE TABLE IF NOT EXISTS `oil`.`Auth` (
  `uid` INT NOT NULL COMMENT '',
  `Password` VARCHAR(45) NULL COMMENT '',
  `role` int NOT NULL COMMENT '',
  PRIMARY KEY (`uid`,`role`)
  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oil`.`Trader`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oil`.`Trader` ;

CREATE TABLE IF NOT EXISTS `oil`.`Trader` (
  `Trader_id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `Trader_name` VARCHAR(45) NULL COMMENT '',
  PRIMARY KEY (`Trader_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oil`.`Transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oil`.`Transaction` ;

CREATE TABLE IF NOT EXISTS `oil`.`Transaction` (
  `Transid` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `Cid` INT NOT NULL COMMENT '',
  `Oil_amt` BIGINT(20) NULL COMMENT '',
  `Oil_owed` BIGINT(20) NULL COMMENT '',
  `Trans_date` DATE NULL COMMENT '',
  `Cash_owed` FLOAT NULL COMMENT '',
  `Trader_id` INT NULL COMMENT '',
  `Oil_paid` FLOAT NULL COMMENT '',
  `Cash_paid` FLOAT NULL COMMENT '',
  `Status` VARCHAR(15) NULL COMMENT '',
  `Trans_type` VARCHAR(15) NULL COMMENT '',
  `dues_settled` VARCHAR(15) NULL COMMENT '',
  PRIMARY KEY (`Transid`, `Cid`)  COMMENT '',
  INDEX `FK_TRANS_idx` (`Cid` ASC)  COMMENT '',
  INDEX `FK_TRader_idx` (`Trader_id` ASC)  COMMENT '',
  CONSTRAINT `FK_TRANS`
    FOREIGN KEY (`Cid`)
    REFERENCES `oil`.`Client` (`Cid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRader`
    FOREIGN KEY (`Trader_id`)
    REFERENCES `oil`.`Trader` (`Trader_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oil`.`Commision`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `oil`.`Commision` ;

CREATE TABLE IF NOT EXISTS `oil`.`Commision` (
  `Transid` INT NOT NULL COMMENT '',
  `Commision_amount` FLOAT NULL COMMENT '',
  `Curr_dt_oil_price` FLOAT NULL COMMENT '',
  `commision_type` varchar(10) NULL COMMENT '',
  PRIMARY KEY (`Transid`)  COMMENT '',
  CONSTRAINT `FK_COM_TID`
    FOREIGN KEY (`Transid`)
    REFERENCES `oil`.`Transaction` (`Transid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

