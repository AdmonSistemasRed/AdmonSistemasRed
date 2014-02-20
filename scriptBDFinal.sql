SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `cmdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `cmdb` ;

-- -----------------------------------------------------
-- Table `cmdb`.`Sucursal`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Sucursal` (
  `Sucursal_idSucursal` INT NOT NULL ,
  `Sucursal_Nombre` VARCHAR(90) NULL ,
  `Sucursal_Ubicacion` VARCHAR(50) NULL ,
  `Sucursal_Observacion` VARCHAR(100) NULL ,
  `Sucursalcol` VARCHAR(45) NULL ,
  PRIMARY KEY (`Sucursal_idSucursal`) )
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
  `Sucursal_Sucursal_idSucursal` INT NOT NULL ,
  `Rol_Rol_idRol` VARCHAR(20) NOT NULL ,
  `emp_nombreUser` VARCHAR(45) NOT NULL ,
  `emp_Password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`emp_NoEmpleado`) ,
  INDEX `fk_empleado_Sucursal1_idx` (`Sucursal_Sucursal_idSucursal` ASC) ,
  CONSTRAINT `fk_empleado_Sucursal1`
    FOREIGN KEY (`Sucursal_Sucursal_idSucursal` )
    REFERENCES `cmdb`.`Sucursal` (`Sucursal_idSucursal` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
-- Table `cmdb`.`Perifericos`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Perifericos` (
  `idPeriferico` VARCHAR(45) NOT NULL ,
  `categoria` VARCHAR(45) NOT NULL ,
  `marca` VARCHAR(45) NULL ,
  `modelo` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`idPeriferico`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Telecommunications`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Telecommunications` (
  `idTelecom` VARCHAR(45) NOT NULL ,
  `numeroPuertosSalida` VARCHAR(45) NULL ,
  `numeroPuertosEntrada` VARCHAR(45) NULL ,
  `tipoInterfaz` VARCHAR(45) NULL ,
  `direccionFisica` VARCHAR(45) NULL ,
  `modelo` VARCHAR(45) NULL ,
  `marca` VARCHAR(45) NULL ,
  `observaciones` VARCHAR(100) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  PRIMARY KEY (`idTelecom`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Workstation`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Workstation` (
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
  `CostoTotal` VARCHAR(45) NULL ,
  `depreciacion` VARCHAR(45) NULL ,
  PRIMARY KEY (`idWorkstation`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Laptops`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Laptops` (
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
  `costoTotal` VARCHAR(45) NULL ,
  `depreciacion` VARCHAR(45) NULL ,
  PRIMARY KEY (`idLaptop`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`servidor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`servidor` (
  `idServidor` VARCHAR(45) NOT NULL ,
  `procesardor` VARCHAR(45) NULL ,
  `memoria` VARCHAR(45) NULL ,
  `discoDuro` VARCHAR(45) NULL ,
  `unidadOptica` VARCHAR(45) NULL ,
  `bateriaReserva` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NULL ,
  `direccionIP` VARCHAR(45) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  `costoTotal` VARCHAR(45) NULL ,
  `depreciacion` VARCHAR(45) NULL ,
  PRIMARY KEY (`idServidor`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`Computadora`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Computadora` (
  `idComputadora` VARCHAR(45) NOT NULL ,
  `modelo` VARCHAR(45) NULL ,
  `fechaAdquisicion` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(45) NULL ,
  `Workstation_idWorkstation` VARCHAR(45) NOT NULL ,
  `Laptops_idLaptop` VARCHAR(45) NOT NULL ,
  `servidor_idServidor` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idComputadora`) ,
  INDEX `fk_Computadora_Workstation1_idx` (`Workstation_idWorkstation` ASC) ,
  INDEX `fk_Computadora_Laptops1_idx` (`Laptops_idLaptop` ASC) ,
  INDEX `fk_Computadora_servidor1_idx` (`servidor_idServidor` ASC) ,
  CONSTRAINT `fk_Computadora_Workstation1`
    FOREIGN KEY (`Workstation_idWorkstation` )
    REFERENCES `cmdb`.`Workstation` (`idWorkstation` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Computadora_Laptops1`
    FOREIGN KEY (`Laptops_idLaptop` )
    REFERENCES `cmdb`.`Laptops` (`idLaptop` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Computadora_servidor1`
    FOREIGN KEY (`servidor_idServidor` )
    REFERENCES `cmdb`.`servidor` (`idServidor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`IT_item`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`IT_item` (
  `it_serie` VARCHAR(30) NOT NULL ,
  `Perifericos_idPeriferico` VARCHAR(45) NOT NULL ,
  `Telecommunications_idTelecom` VARCHAR(45) NOT NULL ,
  `Computadora_idComputadora` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`it_serie`) ,
  INDEX `fk_IT_item_Perifericos1_idx` (`Perifericos_idPeriferico` ASC) ,
  INDEX `fk_IT_item_Telecommunications1_idx` (`Telecommunications_idTelecom` ASC) ,
  INDEX `fk_IT_item_Computadora1_idx` (`Computadora_idComputadora` ASC) ,
  CONSTRAINT `fk_IT_item_Perifericos1`
    FOREIGN KEY (`Perifericos_idPeriferico` )
    REFERENCES `cmdb`.`Perifericos` (`idPeriferico` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IT_item_Telecommunications1`
    FOREIGN KEY (`Telecommunications_idTelecom` )
    REFERENCES `cmdb`.`Telecommunications` (`idTelecom` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_IT_item_Computadora1`
    FOREIGN KEY (`Computadora_idComputadora` )
    REFERENCES `cmdb`.`Computadora` (`idComputadora` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cmdb`.`area`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`area` (
  `are_idArea` VARCHAR(30) NOT NULL ,
  `are_nombre` VARCHAR(45) NULL ,
  `are_despcripcion` VARCHAR(100) NULL ,
  `Sucursal_Sucursal_idSucursal` INT NOT NULL ,
  PRIMARY KEY (`are_idArea`) ,
  INDEX `fk_area_Sucursal1_idx` (`Sucursal_Sucursal_idSucursal` ASC) ,
  CONSTRAINT `fk_area_Sucursal1`
    FOREIGN KEY (`Sucursal_Sucursal_idSucursal` )
    REFERENCES `cmdb`.`Sucursal` (`Sucursal_idSucursal` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
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
-- Table `cmdb`.`Software`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Software` (
  `idSoftware` VARCHAR(45) NOT NULL ,
  `nombreSoftware` VARCHAR(45) NULL ,
  `licencia` VARCHAR(45) NULL ,
  `cantidadUsuarios` VARCHAR(45) NULL ,
  `version` VARCHAR(45) NULL ,
  `arquitectura` VARCHAR(45) NULL ,
  `costoUnitario` VARCHAR(45) NULL ,
  `costoPorVolumen` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(100) NULL ,
  PRIMARY KEY (`idSoftware`) )
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
    FOREIGN KEY (`IT_item_it_serie` )
    REFERENCES `cmdb`.`IT_item` (`it_serie` )
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


-- -----------------------------------------------------
-- Table `cmdb`.`Software_has_Computadora`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `cmdb`.`Software_has_Computadora` (
  `Software_idSoftware` VARCHAR(45) NOT NULL ,
  `Computadora_idComputadora` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`Software_idSoftware`, `Computadora_idComputadora`) ,
  INDEX `fk_Software_has_Computadora_Computadora1_idx` (`Computadora_idComputadora` ASC) ,
  INDEX `fk_Software_has_Computadora_Software1_idx` (`Software_idSoftware` ASC) ,
  CONSTRAINT `fk_Software_has_Computadora_Software1`
    FOREIGN KEY (`Software_idSoftware` )
    REFERENCES `cmdb`.`Software` (`idSoftware` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Software_has_Computadora_Computadora1`
    FOREIGN KEY (`Computadora_idComputadora` )
    REFERENCES `cmdb`.`Computadora` (`idComputadora` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `cmdb` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
