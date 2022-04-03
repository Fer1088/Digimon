-- MariaDB dump 10.19  Distrib 10.5.13-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: Digimon
-- ------------------------------------------------------
-- Server version	10.5.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Digimon`
--

DROP TABLE IF EXISTS `Digimon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Digimon` (
  `NomDig` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `AtacDig` int(11) DEFAULT NULL,
  `DefDig` int(11) DEFAULT NULL,
  `TipoDig` enum('ANIMAL','PLANTA','VIRUS','VACUNA','ELEMENTAL') COLLATE utf8_spanish_ci DEFAULT NULL,
  `NivDig` int(11) DEFAULT NULL,
  `NomDigEvo` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`NomDig`),
  KEY `fk_dig_dig` (`NomDigEvo`),
  CONSTRAINT `fk_dig_dig` FOREIGN KEY (`NomDigEvo`) REFERENCES `Digimon` (`NomDig`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Digimon`
--

LOCK TABLES `Digimon` WRITE;
/*!40000 ALTER TABLE `Digimon` DISABLE KEYS */;
INSERT INTO `Digimon` VALUES ('Felipomon',65,58,'ANIMAL',1,NULL),('Josepomon',28,28,'VIRUS',1,NULL),('Sambon',69,23,'VACUNA',1,NULL);
/*!40000 ALTER TABLE `Digimon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tiene`
--

DROP TABLE IF EXISTS `Tiene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tiene` (
  `NomDig` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `NomUsu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `EstaEquipo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`NomDig`,`NomUsu`),
  KEY `fk_usuario_tiene` (`NomUsu`),
  CONSTRAINT `fk_digimon_tiene` FOREIGN KEY (`NomDig`) REFERENCES `Digimon` (`NomDig`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_usuario_tiene` FOREIGN KEY (`NomUsu`) REFERENCES `Usuario` (`NomUsu`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tiene`
--

LOCK TABLES `Tiene` WRITE;
/*!40000 ALTER TABLE `Tiene` DISABLE KEYS */;
INSERT INTO `Tiene` VALUES ('Felipomon','Admin',1),('Felipomon','Antonio',1),('Felipomon','Dulsesico',1),('Felipomon','Manolo',1),('Josepomon','Admin',1),('Josepomon','Antonio',1),('Josepomon','Dulsesico',1),('Josepomon','Manolo',1),('Sambon','Admin',1),('Sambon','Antonio',1),('Sambon','Dulsesico',1),('Sambon','Manolo',1);
/*!40000 ALTER TABLE `Tiene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `NomUsu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `ContUsu` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `PartidasGan` int(11) DEFAULT NULL,
  `TokensEvo` int(11) DEFAULT NULL,
  `EsAdmin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`NomUsu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES ('Admin','1234',0,0,1),('Antonio','cosas',9,1,1),('Dulsesico','ososos',777,23,0),('Manolo','bombo',23,4,1);
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-25 12:47:44
