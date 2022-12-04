-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: dbloja
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbnotafiscal`
--

DROP TABLE IF EXISTS `tbnotafiscal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbnotafiscal` (
  `idNota` int NOT NULL AUTO_INCREMENT,
  `data_nt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `idVendedor_tbVendedor` int NOT NULL,
  `idCompra_tbCompra` int NOT NULL,
  PRIMARY KEY (`idNota`),
  KEY `idVendedor_tbVendedor` (`idVendedor_tbVendedor`),
  KEY `idCompra_tbCompra` (`idCompra_tbCompra`),
  CONSTRAINT `tbnotafiscal_ibfk_1` FOREIGN KEY (`idVendedor_tbVendedor`) REFERENCES `tbvendedor` (`idVendedor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbnotafiscal_ibfk_2` FOREIGN KEY (`idCompra_tbCompra`) REFERENCES `tbcompra` (`idCompra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbnotafiscal`
--

LOCK TABLES `tbnotafiscal` WRITE;
/*!40000 ALTER TABLE `tbnotafiscal` DISABLE KEYS */;
INSERT INTO `tbnotafiscal` VALUES (1,'2022-12-04 03:00:00',1,1);
/*!40000 ALTER TABLE `tbnotafiscal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-04  0:04:08
