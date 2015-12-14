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
  `Imagen` LONGBLOB NULL,
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
  `Imagen` LONGBLOB NULL,
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

-- -----------------------------------------------------
-- Table `rotolearnbd`.`DESCUENTO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`DESCUENTO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`DESCUENTO` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Tipo` VARCHAR(5) NOT NULL,
  `ID_c` INT NOT NULL,
  `Cupon` VARCHAR(30) NOT NULL,
  `Profesor` VARCHAR(25) NOT NULL,
  `Validez` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_c_UNIQUE` (`ID_c` ASC),
  CONSTRAINT `fk_DESCUENTO_1`
    FOREIGN KEY (`ID_c`)
    REFERENCES `rotolearnbd`.`CURSO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`PROMOCION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`PROMOCION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`PROMOCION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ID_c` INT NOT NULL,
  `Descuento` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_PROMOCION_1_idx` (`ID_c` ASC),
  CONSTRAINT `fk_PROMOCION_1`
    FOREIGN KEY (`ID_c`)
    REFERENCES `rotolearnbd`.`CURSO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`PROFESOR_ASOCIADO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`PROFESOR_ASOCIADO` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`PROFESOR_ASOCIADO` (
  `ID_c` INT NOT NULL,
  `ID_p` INT NOT NULL,
  `Validado` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`ID_c`, `ID_p`),
  INDEX `fk_PROFESOR_ASOCIADO_2_idx` (`ID_p` ASC),
  CONSTRAINT `fk_PROFESOR_ASOCIADO_1`
    FOREIGN KEY (`ID_c`)
    REFERENCES `rotolearnbd`.`CURSO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_PROFESOR_ASOCIADO_2`
    FOREIGN KEY (`ID_p`)
    REFERENCES `rotolearnbd`.`USUARIO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`SECCION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`SECCION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`SECCION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `CURSO_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_SECCION_CURSO1_idx` (`CURSO_ID` ASC),
  CONSTRAINT `fk_SECCION_CURSO1`
    FOREIGN KEY (`CURSO_ID`)
    REFERENCES `rotolearnbd`.`CURSO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`LECCION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`LECCION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`LECCION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Descripcion` VARCHAR(300) NULL,
  `SECCION_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_LECCION_SECCION1_idx` (`SECCION_ID` ASC),
  CONSTRAINT `fk_LECCION_SECCION1`
    FOREIGN KEY (`SECCION_ID`)
    REFERENCES `rotolearnbd`.`SECCION` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`MATERIAL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`MATERIAL` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`MATERIAL` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NULL,
  `Tipo` VARCHAR(45) NULL,
  `Contenido` LONGBLOB NULL,
  `LECCION_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_MATERIAL_LECCION1_idx` (`LECCION_ID` ASC),
  CONSTRAINT `fk_MATERIAL_LECCION1`
    FOREIGN KEY (`LECCION_ID`)
    REFERENCES `rotolearnbd`.`LECCION` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`NOTIFICACION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`NOTIFICACION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`NOTIFICACION` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Destinatario` INT NOT NULL,
  `Descripcion` VARCHAR(100) NOT NULL,
  `Leido` INT NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Notificaciones_1_idx` (`Destinatario` ASC),
  CONSTRAINT `fk_Notificaciones_1`
    FOREIGN KEY (`Destinatario`)
    REFERENCES `rotolearnbd`.`USUARIO` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rotolearnbd`.`CONCILIACION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rotolearnbd`.`CONCILIACION` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rotolearnbd`.`CONCILIACION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Cobrador` INT NOT NULL,
  `Anio` VARCHAR(4) NOT NULL,
  `Mes` VARCHAR(15) NOT NULL,
  `Importe` INT NOT NULL,
  `CodOp` VARCHAR(45) NOT NULL,
  `CodPed` VARCHAR(45) NOT NULL,
  `Descuento` VARCHAR(45) NULL,
  `Promocion` INT NULL,
  `Pagado` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_CONCILIACION_1_idx` (`Cobrador` ASC),
  INDEX `fk_CONCILIACION_2_idx` (`Promocion` ASC),
  CONSTRAINT `fk_CONCILIACION_1`
    FOREIGN KEY (`Cobrador`)
    REFERENCES `rotolearnbd`.`USUARIO` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CONCILIACION_2`
    FOREIGN KEY (`Promocion`)
    REFERENCES `rotolearnbd`.`PROMOCION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
