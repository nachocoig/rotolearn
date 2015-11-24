SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `rotolearnbd` ;
CREATE SCHEMA IF NOT EXISTS `rotolearnbd` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `rotolearnbd` ;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`USUARIO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`USUARIO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`USUARIO` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Tipo` VARCHAR(5) NOT NULL,
  `Nickname` VARCHAR(25) NOT NULL,
  `Nombre` VARCHAR(25) NOT NULL,
  `Apellido1` VARCHAR(35) NOT NULL,
  `Apellido2` VARCHAR(35) NOT NULL,
  `Pass` VARCHAR(25) NOT NULL,
  `Fecha_nac` VARCHAR(10) NULL,
  `Imagen` BLOB NULL,
  `Email` VARCHAR(70) NOT NULL,
  `Telefono` INT NOT NULL,
  `Direccion` VARCHAR(70) NULL,
  `Descripcion` VARCHAR(300) NULL,
  `Intereses` VARCHAR(300) NULL,
  UNIQUE INDEX `Nickname_UNIQUE` (`Nickname` ASC),
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`CURSO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`CURSO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`CURSO` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Titulo` VARCHAR(100) NOT NULL,
  `Profesor` INT NOT NULL,
  `Precio` INT NOT NULL,
  `Horas` INT NOT NULL,
  `Email_paypal` VARCHAR(70) NOT NULL,
  `Dificultad` VARCHAR(11) NOT NULL,
  `Descripcion` VARCHAR(300) NOT NULL,
  `Validado` VARCHAR(2) NOT NULL,
  `Destacado` VARCHAR(2) NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Imagen` BLOB NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_CURSO_1_idx` (`Profesor` ASC),
  CONSTRAINT `fk_CURSO_1`
    FOREIGN KEY (`Profesor`)
    REFERENCES `rotolearnbd`.`USUARIO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`CURSO_ALUMNO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`CURSO_ALUMNO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`CURSO_ALUMNO` (
  `ID_u` INT NOT NULL,
  `ID_c` INT NOT NULL,
  `Estado` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`ID_u`, `ID_c`),
  INDEX `fk_CURSO_ALUMNO_2_idx` (`ID_c` ASC),
  CONSTRAINT `fk_CURSO_ALUMNO_1`
    FOREIGN KEY (`ID_u`)
    REFERENCES `rotolearnbd`.`USUARIO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CURSO_ALUMNO_2`
    FOREIGN KEY (`ID_c`)
    REFERENCES `rotolearnbd`.`CURSO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`ADMIN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`ADMIN` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`ADMIN` (
  `Nickname` VARCHAR(25) NOT NULL,
  `Pass` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`Nickname`))
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
