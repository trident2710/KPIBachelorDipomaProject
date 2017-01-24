-- MySQL dump 10.13  Distrib 5.7.9, for osx10.10 (x86_64)
--
-- Host: localhost    Database: object_access_diploma
-- ------------------------------------------------------
-- Server version	5.7.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_type` int(11) DEFAULT NULL,
  `terminal` int(11) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `staff_member` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idEvent_UNIQUE` (`id`),
  KEY `fk_Event_EventType1_idx` (`event_type`),
  KEY `fk_Event_Terminal1_idx` (`terminal`),
  KEY `fk_event_staff_member1_idx` (`staff_member`),
  CONSTRAINT `fk_Event_EventType1` FOREIGN KEY (`event_type`) REFERENCES `event_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Event_Terminal1` FOREIGN KEY (`terminal`) REFERENCES `terminal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_staff_member1` FOREIGN KEY (`staff_member`) REFERENCES `staff_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idActionType_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_type`
--

LOCK TABLES `event_type` WRITE;
/*!40000 ALTER TABLE `event_type` DISABLE KEYS */;
INSERT INTO `event_type` VALUES (1,'Access Allowed'),(2,'Access Denied'),(3,'Access Error'),(4,'Access Admin'),(5,'Dangerous Behaviour');
/*!40000 ALTER TABLE `event_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_info`
--

DROP TABLE IF EXISTS `staff_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idStaffInfo_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_info`
--

LOCK TABLES `staff_info` WRITE;
/*!40000 ALTER TABLE `staff_info` DISABLE KEYS */;
INSERT INTO `staff_info` VALUES (1,'George','Cena','28'),(2,'John','Clark','32'),(3,'Mykola','Cena','23'),(4,'John','Gustaffson','23'),(5,'Tamara','Gustaffson','26'),(6,'Tamara','Clark','33'),(7,'George','Shevchenko','22'),(8,'Mykola','Lewis','19'),(9,'Tanya','Shevchenko','29'),(10,'Tanya','Petrenko','31'),(11,'Carlie','Mykolaychuk','27'),(12,'Andriana','Mykolaychuk','26'),(13,'George','Mykolaychuk','20'),(14,'John','Mykolaychuk','30'),(15,'Tamara','Gustaffson','34'),(16,'John','Petrenko','36'),(17,'Tanya','Clark','29'),(18,'Carlie','Mykolaychuk','32'),(19,'Mykola','Watson','24'),(20,'George','Watson','21'),(21,'John','Watson','21'),(22,'Andriana','Watson','29'),(23,'George','Lewis','27'),(24,'Tanya','Mykolaychuk','20'),(25,'Tanya','Watson','23'),(26,'Andriana','Gustaffson','22'),(27,'Andriana','Petrenko','36'),(28,'Andriana','Lewis','26'),(29,'Tanya','Gustaffson','36'),(30,'Andriana','Petrenko','35');
/*!40000 ALTER TABLE `staff_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_member`
--

DROP TABLE IF EXISTS `staff_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_info` int(11) DEFAULT NULL,
  `special_code` varchar(128) NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `staff_position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idStaffMember_UNIQUE` (`id`),
  UNIQUE KEY `staff_code_UNIQUE` (`special_code`),
  KEY `fk_StaffMember_StaffInfo1_idx` (`staff_info`),
  KEY `fk_staff_member_staff_position1_idx` (`staff_position`),
  CONSTRAINT `fk_StaffMember_StaffInfo1` FOREIGN KEY (`staff_info`) REFERENCES `staff_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_member_staff_position1` FOREIGN KEY (`staff_position`) REFERENCES `staff_position` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_member`
--

LOCK TABLES `staff_member` WRITE;
/*!40000 ALTER TABLE `staff_member` DISABLE KEYS */;
INSERT INTO `staff_member` VALUES (1,1,'7turr8g8ep03i62b98k4bokkdj',1,3),(2,2,'5s8is2u1j46j3u4rvvk2reprf0',1,1),(3,3,'e6cqtq5t2bne2420di637ces5s',1,1),(4,4,'6coul9v2mhmrkjuibg0bg9lh97',1,1),(5,5,'ftihr8a5ag5p78tpb9b9plhfkk',1,3),(6,6,'94rv1aobagkne56s4rnp61sehn',1,3),(7,7,'pp40pcgeimsgr623kt8doi75t8',1,3),(8,8,'akrocu8dpptipi6mch9c4bpn7u',1,2),(9,9,'8lmtbad0o2er4v073t8pvh4mfs',1,2),(10,10,'rkjs25r08ntjsqo4sb2mjm8oa0',1,1),(11,11,'qlhgcqaoabiq0sgifcuro3350i',1,2),(12,12,'4b7r54hu9v79up0s0b1tuccskg',1,1),(13,13,'joha35bne49ot2gbf02aiad5dg',1,1),(14,14,'5t0rt7bsqp5bkm3canhg01gqg1',1,3),(15,15,'80oud43prq9c3h0f2mn2p53b1r',1,3),(16,16,'irb892m1rq3t6di9i23p5a2i7p',1,1),(17,17,'fao5u1vdg2ug0dcb1f1mlpvbur',1,3),(18,18,'7aspdhb58v7p6i9bo2sa6vp1kh',1,3),(19,19,'d8fhkqb3b437fq3rgoqidebmvn',1,1),(20,20,'u8opriel8bpk09dqi15dauit5s',1,1),(21,21,'1dk2bfns2tbbeeaq1lthrg23f6',1,2),(22,22,'tskmof36sheng99f5q4b92hq4q',1,1),(23,23,'9f2vpm2jq78den0msm1u9e6o48',1,1),(24,24,'2obqd9i1bqmjuvt04vcaif22bc',1,3),(25,25,'fn1sc3gag1t4hn9roatrss6nmd',1,3),(26,26,'cbq3obooip6bucq3i78tqa73qo',1,1),(27,27,'9dm7nqas5e1rs5puq83r1ogcq8',1,3),(28,28,'95uccdj81knl4supq46l0idv30',1,3),(29,29,'55ukhchqhrv6uv18e495g2lpst',1,3),(30,30,'v1fq4tef8ursmvjegs3i5ic8pu',1,3);
/*!40000 ALTER TABLE `staff_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_position`
--

DROP TABLE IF EXISTS `staff_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idStaffPosition_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_position`
--

LOCK TABLES `staff_position` WRITE;
/*!40000 ALTER TABLE `staff_position` DISABLE KEYS */;
INSERT INTO `staff_position` VALUES (1,'Labourer'),(2,'Manager'),(3,'Administrator');
/*!40000 ALTER TABLE `staff_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminal`
--

DROP TABLE IF EXISTS `terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) DEFAULT NULL,
  `special_code` varchar(128) NOT NULL,
  `terminal_data` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idTerminal_UNIQUE` (`id`),
  UNIQUE KEY `uniqueId_UNIQUE` (`special_code`),
  KEY `fk_terminal_terminal_data1_idx` (`terminal_data`),
  CONSTRAINT `fk_terminal_terminal_data1` FOREIGN KEY (`terminal_data`) REFERENCES `terminal_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminal`
--

LOCK TABLES `terminal` WRITE;
/*!40000 ALTER TABLE `terminal` DISABLE KEYS */;
INSERT INTO `terminal` VALUES (1,1,'9g7usm3tsgf4v3r35t0nm14jm3',1),(2,1,'d4n1hic3c9tpc24d7ceifu2ru3',2);
/*!40000 ALTER TABLE `terminal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminal_data`
--

DROP TABLE IF EXISTS `terminal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminal_data`
--

LOCK TABLES `terminal_data` WRITE;
/*!40000 ALTER TABLE `terminal_data` DISABLE KEYS */;
INSERT INTO `terminal_data` VALUES (1,'Terminal 1','Main Terminal','KPI building 14, hall'),(2,'Terminal 2','Secondary Terminal','KPI building 15, 3th floor');
/*!40000 ALTER TABLE `terminal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminal_staff_access`
--

DROP TABLE IF EXISTS `terminal_staff_access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_staff_access` (
  `id_terminal` int(11) NOT NULL,
  `id_staff_member` int(11) NOT NULL,
  PRIMARY KEY (`id_terminal`,`id_staff_member`),
  KEY `fk_Terminal_has_StaffMember_StaffMember1_idx` (`id_staff_member`),
  KEY `fk_Terminal_has_StaffMember_Terminal_idx` (`id_terminal`),
  CONSTRAINT `fk_Terminal_has_StaffMember_StaffMember1` FOREIGN KEY (`id_staff_member`) REFERENCES `staff_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Terminal_has_StaffMember_Terminal` FOREIGN KEY (`id_terminal`) REFERENCES `terminal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminal_staff_access`
--

LOCK TABLES `terminal_staff_access` WRITE;
/*!40000 ALTER TABLE `terminal_staff_access` DISABLE KEYS */;
INSERT INTO `terminal_staff_access` VALUES (1,1),(1,4),(2,4),(1,6),(1,7),(1,10),(2,10),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,21),(1,22),(1,23),(1,26),(1,27);
/*!40000 ALTER TABLE `terminal_staff_access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_data` int(11) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `special_code` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idUser_UNIQUE` (`id`),
  UNIQUE KEY `special_code_UNIQUE` (`special_code`),
  KEY `fk_User_UserData1_idx` (`user_data`),
  CONSTRAINT `fk_User_UserData1` FOREIGN KEY (`user_data`) REFERENCES `user_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'password1','test1@diploma.com','unu9iil1g58vpru40oqac4vkb'),(2,2,'password2','test2@diploma.com','6stsabelmjs1h1aild7k9nbgj4'),(3,3,'password3','test3@diploma.com','ahbsme5er736evoiibjuvfihmk');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idUserData_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (1,'Andrew','Dychka',21),(2,'Peter','Morrison',34),(3,'Shon','Stein',27);
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-27 18:59:24
