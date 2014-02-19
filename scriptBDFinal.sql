SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `cmdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `cmdb` ;

-- -----------------------------------------------------
-- Table `cmdb`.`area`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`area` (
  `are_idArea` VARCHAR(30) NOT NULL ,
  `are_nombre` VARCHAR(45) NULL ,
  `are_despcripcion` VARCHAR(100) NULL ,
  PRIMARY KEY (`are_idArea`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`empleado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`empleado` (
  `emp_NoEmpleado` VARCHAR(20) NOT NULL ,
  `emp_nombre` VARCHAR(45) NOT NULL ,
  `emp_paterno` VARCHAR(45) NOT NULL ,
  `emp_materno` VARCHAR(45) NOT NULL ,
  `emp_telefono` VARCHAR(20) NULL ,
  `emp_celular` VARCHAR(20) NULL ,
  `emp_email` VARCHAR(45) NULL ,
  `emp_usrMensajeria` VARCHAR(45) NULL ,
  `emp_ubicacion` VARCHAR(45) NULL ,
  `emp_lenguaje` VARCHAR(45) NULL ,
  `emp_utc` VARCHAR(45) NULL ,
  `emp_disponibilidad` VARCHAR(45) NULL ,
  `emp_servicios` VARCHAR(45) NULL ,
  `emp_intereses` VARCHAR(45) NULL ,
  `emp_certificaciones` VARCHAR(45) NULL ,
  `emp_habilidades` VARCHAR(45) NULL ,
  `emp_responsabilidades` VARCHAR(45) NULL ,
  `area_are_idArea` VARCHAR(30) NOT NULL ,
  PRIMARY KEY (`emp_NoEmpleado`) ,
  INDEX `fk_empleado_area1_idx` (`area_are_idArea` ASC) ,
  CONSTRAINT `fk_empleado_area1`
    FOREIGN KEY (`area_are_idArea` )
    REFERENCES `cmdb`.`area` (`are_idArea` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`lenguaje`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`lenguaje` (
  `len_lenguaje` VARCHAR(5) NOT NULL ,
  `len_nombre` VARCHAR(45) NULL ,
  PRIMARY KEY (`len_lenguaje`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`IT_item`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`IT_item` (
  `it_serie` VARCHAR(30) NOT NULL ,
  `it_marca` VARCHAR(45) NOT NULL ,
  `modelo` VARCHAR(45) NULL ,
  PRIMARY KEY (`it_serie`, `it_marca`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`depto`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`depto` (
  `dep_departamento` VARCHAR(30) NOT NULL ,
  `dep_nombre` VARCHAR(45) NULL ,
  `dep_descripcion` VARCHAR(100) NULL ,
  `area_are_idArea` VARCHAR(30) NOT NULL ,
  PRIMARY KEY (`dep_departamento`) ,
  INDEX `fk_depto_area1_idx` (`area_are_idArea` ASC) ,
  CONSTRAINT `fk_depto_area1`
    FOREIGN KEY (`area_are_idArea` )
    REFERENCES `cmdb`.`area` (`are_idArea` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`roles` (
  `rol_idRol` VARCHAR(45) NOT NULL ,
  `rol_nombre` VARCHAR(45) NULL ,
  `rol_Descripcion` VARCHAR(100) NULL ,
  PRIMARY KEY (`rol_idRol`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`servidor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`servidor` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idServidor` VARCHAR(45) NOT NULL ,
  `procesardor` VARCHAR(45) NULL ,
  `memoria` VARCHAR(45) NULL ,
  `discoDuro` VARCHAR(45) NULL ,
  `unidadOptica` VARCHAR(45) NULL ,
  `bateriaReserva` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  INDEX `fk_servidor_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idServidor`) ,
  CONSTRAINT `fk_servidor_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Workstation`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Workstation` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idWorkstation` VARCHAR(45) NOT NULL ,
  `procesador` VARCHAR(45) NULL ,
  `memoria` VARCHAR(45) NULL ,
  `discoDuro` VARCHAR(45) NULL ,
  `tarjetaVideo` VARCHAR(45) NULL ,
  `puertos` VARCHAR(45) NULL ,
  `resolucionMonitor` VARCHAR(45) NULL ,
  `unidadOptica` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(45) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  INDEX `fk_Workstation_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idWorkstation`) ,
  CONSTRAINT `fk_Workstation_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Software`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Software` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idSoftware` VARCHAR(45) NOT NULL ,
  `nombreSoftware` VARCHAR(45) NULL ,
  `licencia` VARCHAR(45) NULL ,
  `cantidadUsuarios` VARCHAR(45) NULL ,
  `version` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  INDEX `fk_Software_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idSoftware`) ,
  CONSTRAINT `fk_Software_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Laptops`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Laptops` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idLaptop` VARCHAR(45) NOT NULL ,
  `procesador` VARCHAR(45) NULL ,
  `memoria` VARCHAR(45) NULL ,
  `duracionBateria` VARCHAR(45) NULL ,
  `discoDuro` VARCHAR(45) NULL ,
  `resolucionPantalla` VARCHAR(45) NULL ,
  `tarjetaVideo` VARCHAR(45) NULL ,
  `puerto` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  INDEX `fk_Laptops_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idLaptop`) ,
  CONSTRAINT `fk_Laptops_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Perifericos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Perifericos` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idPeriferico` VARCHAR(45) NOT NULL ,
  `categoria` VARCHAR(45) NOT NULL ,
  `descripcion` VARCHAR(100) NOT NULL ,
  INDEX `fk_Perifericos_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idPeriferico`) ,
  CONSTRAINT `fk_Perifericos_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Telecommunications`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Telecommunications` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `idTelecom` VARCHAR(45) NOT NULL ,
  `numeroPuertosSalida` VARCHAR(45) NULL ,
  `numeroPuertosEntrada` VARCHAR(45) NULL ,
  `tipoInterfaz` VARCHAR(45) NULL ,
  `direccionFisica` VARCHAR(45) NULL ,
  `observaciones` VARCHAR(100) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  INDEX `fk_Telecommunications_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  PRIMARY KEY (`idTelecom`) ,
  CONSTRAINT `fk_Telecommunications_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`empleado_has_lenguaje`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`empleado_has_lenguaje` (
  `empleado_emp_NoEmpleado` VARCHAR(20) NOT NULL ,
  `lenguaje_len_lenguaje` VARCHAR(5) NOT NULL ,
  INDEX `fk_empleado_has_lenguaje_lenguaje1_idx` (`lenguaje_len_lenguaje` ASC) ,
  INDEX `fk_empleado_has_lenguaje_empleado1_idx` (`empleado_emp_NoEmpleado` ASC) ,
  CONSTRAINT `fk_empleado_has_lenguaje_empleado1`
    FOREIGN KEY (`empleado_emp_NoEmpleado` )
    REFERENCES `cmdb`.`empleado` (`emp_NoEmpleado` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_empleado_has_lenguaje_lenguaje1`
    FOREIGN KEY (`lenguaje_len_lenguaje` )
    REFERENCES `cmdb`.`lenguaje` (`len_lenguaje` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`IT_item_has_empleado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`IT_item_has_empleado` (
  `IT_item_it_serie` VARCHAR(30) NOT NULL ,
  `IT_item_it_marca` VARCHAR(45) NOT NULL ,
  `empleado_emp_NoEmpleado` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`IT_item_it_serie`, `IT_item_it_marca`, `empleado_emp_NoEmpleado`) ,
  INDEX `fk_IT_item_has_empleado_empleado1_idx` (`empleado_emp_NoEmpleado` ASC) ,
  INDEX `fk_IT_item_has_empleado_IT_item1_idx` (`IT_item_it_serie` ASC, `IT_item_it_marca` ASC) ,
  CONSTRAINT `fk_IT_item_has_empleado_IT_item1`
    FOREIGN KEY (`IT_item_it_serie` , `IT_item_it_marca` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` , `it_marca` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IT_item_has_empleado_empleado1`
    FOREIGN KEY (`empleado_emp_NoEmpleado` )
    REFERENCES `cmdb`.`empleado` (`emp_NoEmpleado` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`empleado_has_roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`empleado_has_roles` (
  `empleado_emp_NoEmpleado` VARCHAR(20) NOT NULL ,
  `roles_rol_idRol` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`empleado_emp_NoEmpleado`, `roles_rol_idRol`) ,
  INDEX `fk_empleado_has_roles_roles1_idx` (`roles_rol_idRol` ASC) ,
  INDEX `fk_empleado_has_roles_empleado1_idx` (`empleado_emp_NoEmpleado` ASC) ,
  CONSTRAINT `fk_empleado_has_roles_empleado1`
    FOREIGN KEY (`empleado_emp_NoEmpleado` )
    REFERENCES `cmdb`.`empleado` (`emp_NoEmpleado` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_empleado_has_roles_roles1`
    FOREIGN KEY (`roles_rol_idRol` )
    REFERENCES `cmdb`.`roles` (`rol_idRol` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `cmdb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
