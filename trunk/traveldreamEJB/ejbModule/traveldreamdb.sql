SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `traveldreamdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `traveldreamdb` ;

-- -----------------------------------------------------
-- Table `traveldreamdb`.`persone`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`persone` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`persone` (
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `dataNascita` DATE NOT NULL,
  `documentoIdentita` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nome`, `cognome`, `dataNascita`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`utenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`utenti` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`utenti` (
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `persone_nome` VARCHAR(45) NULL,
  `persone_cognome` VARCHAR(45) NULL,
  `persone_dataNascita` DATE NULL,
  PRIMARY KEY (`email`),
  INDEX `fk_utenti_persone1_idx` (`persone_nome` ASC, `persone_cognome` ASC, `persone_dataNascita` ASC),
  CONSTRAINT `fk_utenti_persone1`
    FOREIGN KEY (`persone_nome` , `persone_cognome` , `persone_dataNascita`)
    REFERENCES `traveldreamdb`.`persone` (`nome` , `cognome` , `dataNascita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`amici`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`amici` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`amici` (
  `email` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`citta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`citta` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`citta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `regione` VARCHAR(45) NOT NULL,
  `nazione` VARCHAR(45) NOT NULL,
  `lat` FLOAT( 10, 6 ),
  `lon` FLOAT( 10, 6 ),
  `attivo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`nome`, `nazione`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`immagini_citta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`immagini_citta` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`immagini_citta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idCitta` INT NOT NULL,
  `immagine` VARCHAR(255) NOT NULL,  
  PRIMARY KEY (`id`),
  INDEX `fk_immagini_citta_idx` (`idCitta` ASC),
  CONSTRAINT `fk_immagini_citta`
    FOREIGN KEY (`idCitta`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)  
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`hotel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`hotel` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`hotel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `stelle` INT NOT NULL,
  `indirizzo` VARCHAR(255) NOT NULL,
  `telefono` VARCHAR(45) NOT NULL,
  `website` VARCHAR(255) NULL,
  `email` VARCHAR(45) NULL,
  `prezzo` DOUBLE NOT NULL,
  `citta` INT NOT NULL,
  `immagine` VARCHAR(255),
  `attivo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`nome`, `citta`),
  INDEX `fk_Hotel_Citta 1_idx` (`citta` ASC),
  CONSTRAINT `fk_Hotel_Citta 1`
    FOREIGN KEY (`citta`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`pacchetti_predefiniti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`pacchetti_predefiniti` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`pacchetti_predefiniti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `prezzo` DOUBLE NOT NULL,
  `idHotel` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_PacchettoPredefinito_Hotel2_idx` (`idHotel` ASC),
  CONSTRAINT `fk_PacchettoPredefinito_Hotel2`
    FOREIGN KEY (`idHotel`)
    REFERENCES `traveldreamdb`.`hotel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`pacchetti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`pacchetti` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`pacchetti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `numPartecipanti` INT NOT NULL DEFAULT 1,
  `prezzo` DOUBLE NOT NULL,
  `tipoPacchetto` VARCHAR(45) NOT NULL,
  `idCittaOrigine` INT NOT NULL,
  `idPred` INT NULL,
  `emailUtente` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Pacchetto_Citta 2_idx` (`idCittaOrigine` ASC),
  INDEX `fk_Pacchetto_PacchettoPredefinito1_idx` (`idPred` ASC),
  INDEX `fk_pacchetti_utenti1_idx` (`emailUtente` ASC),
  CONSTRAINT `fk_Pacchetto_Citta 2`
    FOREIGN KEY (`idCittaOrigine`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pacchetto_PacchettoPredefinito1`
    FOREIGN KEY (`idPred`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pacchetti_utenti1`
    FOREIGN KEY (`emailUtente`)
    REFERENCES `traveldreamdb`.`utenti` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`condiviso_con`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`condiviso_con` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`condiviso_con` (
  `idPacchetto` INT NOT NULL,
  `emailAmico` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPacchetto`, `emailAmico`),
  INDEX `fk_CondivisoCon_Pacchetto2_idx` (`idPacchetto` ASC),
  INDEX `fk_CondivisoCon_Amico1_idx` (`emailAmico` ASC),
  CONSTRAINT `fk_CondivisoCon_Pacchetto2`
    FOREIGN KEY (`idPacchetto`)
    REFERENCES `traveldreamdb`.`pacchetti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CondivisoCon_Amico1`
    FOREIGN KEY (`emailAmico`)
    REFERENCES `traveldreamdb`.`amici` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`collegamenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`collegamenti` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`collegamenti` (
  `codice` INT NOT NULL AUTO_INCREMENT,
  `tipoCollegamento` VARCHAR(45) NOT NULL,
  `origine` VARCHAR(45) NOT NULL,
  `destinazione` VARCHAR(45) NOT NULL,
  `dataPartenza` DATE NOT NULL,
  `oraPartenza` TIME NOT NULL,
  `oraArrivo` TIME NOT NULL,
  `prezzo` DOUBLE NOT NULL,
  `cittaArrivo` INT NOT NULL,
  `cittaPartenza` INT NOT NULL,
  `attivo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`codice`),
  INDEX `fk_Collegamento_Citta 1_idx` (`cittaArrivo` ASC),
  INDEX `fk_Collegamento_Citta 2_idx` (`cittaPartenza` ASC),
  CONSTRAINT `fk_Collegamento_Citta 1`
    FOREIGN KEY (`cittaArrivo`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Collegamento_Citta 2`
    FOREIGN KEY (`cittaPartenza`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`mezzi_trasporto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`mezzi_trasporto` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`mezzi_trasporto` (
  `idPacchetto` INT NOT NULL,
  `idCollegamento` INT NOT NULL,
  PRIMARY KEY (`idPacchetto`, `idCollegamento`),
  INDEX `fk_MezziTrasporto_Collegamento1_idx` (`idCollegamento` ASC),
  CONSTRAINT `fk_MezziTrasporto_Pacchetto1`
    FOREIGN KEY (`idPacchetto`)
    REFERENCES `traveldreamdb`.`pacchetti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MezziTrasporto_Collegamento1`
    FOREIGN KEY (`idCollegamento`)
    REFERENCES `traveldreamdb`.`collegamenti` (`codice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`dati_partecipanti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`dati_partecipanti` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`dati_partecipanti` (
  `nomePart` VARCHAR(45) NOT NULL,
  `cognomePart` VARCHAR(45) NOT NULL,
  `dataNascitaPart` DATE NOT NULL,
  `idPacchetto` INT NOT NULL,
  PRIMARY KEY (`nomePart`, `cognomePart`, `dataNascitaPart`, `idPacchetto`),
  INDEX `fk_DatiPartecipanti_Persona2_idx` (`nomePart` ASC, `cognomePart` ASC, `dataNascitaPart` ASC),
  INDEX `fk_DatiPartecipanti_Pacchetto1_idx` (`idPacchetto` ASC),
  CONSTRAINT `fk_DatiPartecipanti_Persona2`
    FOREIGN KEY (`nomePart` , `cognomePart` , `dataNascitaPart`)
    REFERENCES `traveldreamdb`.`persone` (`nome` , `cognome` , `dataNascita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DatiPartecipanti_Pacchetto1`
    FOREIGN KEY (`idPacchetto`)
    REFERENCES `traveldreamdb`.`pacchetti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`escursioni`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`escursioni` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`escursioni` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `data` DATE NOT NULL,
  `ora` TIME NOT NULL,
  `durata` INT NOT NULL,
  `categoria` VARCHAR(45) NOT NULL,
  `prezzo` DOUBLE NOT NULL,
  `idCitta` INT NOT NULL,
  `attivo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Escursione_Citta 2_idx` (`idCitta` ASC),
  CONSTRAINT `fk_Escursione_Citta 2`
    FOREIGN KEY (`idCitta`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`destinazioni`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`destinazioni` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`destinazioni` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `dataArrivo` DATE NOT NULL,
  `dataPartenza` DATE NOT NULL,
  `idPacchetto` INT NOT NULL,
  `idHotel` INT NOT NULL,
  `citta` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Destinazione_Pacchetto2_idx` (`idPacchetto` ASC),
  INDEX `fk_Destinazione_Hotel2_idx` (`idHotel` ASC),
  INDEX `fk_Destinazione_Citta 2_idx` (`citta` ASC),
  CONSTRAINT `fk_Destinazione_Pacchetto2`
    FOREIGN KEY (`idPacchetto`)
    REFERENCES `traveldreamdb`.`pacchetti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Destinazione_Hotel2`
    FOREIGN KEY (`idHotel`)
    REFERENCES `traveldreamdb`.`hotel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Destinazione_Citta 2`
    FOREIGN KEY (`citta`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`attivita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`attivita` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`attivita` (
  `numPartecipanti` INT NOT NULL DEFAULT 1,
  `idDestinazione` INT NOT NULL,
  `idEscursione` INT NULL,
  PRIMARY KEY (`idDestinazione`, `idEscursione`),
  INDEX `fk_Attivita _Destinazione2_idx` (`idDestinazione` ASC),
  INDEX `fk_Attivita _Escursione2_idx` (`idEscursione` ASC),
  CONSTRAINT `fk_Attivita _Destinazione2`
    FOREIGN KEY (`idDestinazione`)
    REFERENCES `traveldreamdb`.`destinazioni` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Attivita _Escursione2`
    FOREIGN KEY (`idEscursione`)
    REFERENCES `traveldreamdb`.`escursioni` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`citta_origine_pred`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`citta_origine_pred` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`citta_origine_pred` (
  `idPacchettoPredefinito` INT NOT NULL,
  `idCitta` INT NOT NULL,
  PRIMARY KEY (`idPacchettoPredefinito`, `idCitta`),
  INDEX `fk_Citta OriginePred_PacchettoPredefinito1_idx` (`idPacchettoPredefinito` ASC),
  INDEX `fk_Citta OriginePred_Citta 1_idx` (`idCitta` ASC),
  CONSTRAINT `fk_Citta OriginePred_PacchettoPredefinito1`
    FOREIGN KEY (`idPacchettoPredefinito`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Citta OriginePred_Citta 1`
    FOREIGN KEY (`idCitta`)
    REFERENCES `traveldreamdb`.`citta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`mezzi_trasporto_pred`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`mezzi_trasporto_pred` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`mezzi_trasporto_pred` (
  `idCollegamento` INT NOT NULL,
  `idPacchettoPredefinito` INT NOT NULL,
  PRIMARY KEY (`idCollegamento`, `idPacchettoPredefinito`),
  INDEX `fk_MezziTrasportoPred_PacchettoPredefinito1_idx` (`idPacchettoPredefinito` ASC),
  CONSTRAINT `fk_MezziTrasportoPred_Collegamento1`
    FOREIGN KEY (`idCollegamento`)
    REFERENCES `traveldreamdb`.`collegamenti` (`codice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MezziTrasportoPred_PacchettoPredefinito1`
    FOREIGN KEY (`idPacchettoPredefinito`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`attivita_pred`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`attivita_pred` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`attivita_pred` (
  `idEscursione` INT NOT NULL,
  `idPacchettoPredefinito` INT NOT NULL,
  PRIMARY KEY (`idEscursione`, `idPacchettoPredefinito`),
  INDEX `fk_Attivita Pred_Escursione1_idx` (`idEscursione` ASC),
  INDEX `fk_Attivita Pred_PacchettoPredefinito2_idx` (`idPacchettoPredefinito` ASC),
  CONSTRAINT `fk_Attivita Pred_Escursione1`
    FOREIGN KEY (`idEscursione`)
    REFERENCES `traveldreamdb`.`escursioni` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Attivita Pred_PacchettoPredefinito2`
    FOREIGN KEY (`idPacchettoPredefinito`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`date_partenza`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`date_partenza` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`date_partenza` (
  `data` DATE NOT NULL,
  `idPacchettoPredefinito` INT NOT NULL,
  PRIMARY KEY (`data`, `idPacchettoPredefinito`),
  INDEX `fk_DatePartenza_PacchettoPredefinito1_idx` (`idPacchettoPredefinito` ASC),
  CONSTRAINT `fk_DatePartenza_PacchettoPredefinito1`
    FOREIGN KEY (`idPacchettoPredefinito`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`durate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`durate` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`durate` (
  `durata` INT NOT NULL,
  `idPacchettoPredefinito` INT NOT NULL,
  PRIMARY KEY (`durata`, `idPacchettoPredefinito`),
  INDEX `fk_Durate_PacchettoPredefinito1_idx` (`idPacchettoPredefinito` ASC),
  CONSTRAINT `fk_Durate_PacchettoPredefinito1`
    FOREIGN KEY (`idPacchettoPredefinito`)
    REFERENCES `traveldreamdb`.`pacchetti_predefiniti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `traveldreamdb`.`gruppi`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `traveldreamdb`.`gruppi` ;

CREATE TABLE IF NOT EXISTS `traveldreamdb`.`gruppi` (
  `email` VARCHAR(45) NOT NULL,
  `gruppo` VARCHAR(45) NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_gruppi_utenti1`
    FOREIGN KEY (`email`)
    REFERENCES `traveldreamdb`.`utenti` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
