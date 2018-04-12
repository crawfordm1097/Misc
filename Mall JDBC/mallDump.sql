/*
Mikayla Crawford
7776
09
April 9, 2018
*/

-- MySQL dump 10.16  Distrib 10.2.12-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: Mall
-- ------------------------------------------------------
-- Server version	10.2.12-MariaDB

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accounts` (
  `aid` int(8) NOT NULL,
  `balance` decimal(8,2) DEFAULT NULL,
  `type` varchar(8) DEFAULT NULL CHECK (`type` = 'credit' or `type` = 'checking' or `type` = 'cash'),
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (11432584,153.16,'cash'),(12312315,3014.23,'cash'),(21553694,1602.03,'credit'),(31497325,1207.38,'checking'),(34972168,3821.49,'credit'),(66666666,301658.23,'cash'),(81354922,23.01,'checking');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `name` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `aid` int(8) NOT NULL,
  PRIMARY KEY (`name`,`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES ('Becky Thompson','3513 9th St NW',21553694),('Cersei Lannister','King Rock, Westeros',66666666),('John Douglas','659 Archer Rd',21553694),('Lindsey Coppenhagen','3243 University Ave',11432584),('Rock Lee','The Hidden Leaf Village',12312315);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `eid` int(3) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `salary` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (101,'Dayman','Champion of the Sun',150000.00),(102,'Nightman','Sneaky and Mean',149999.99),(123,'Bilbo Baggins','Cashier',30000.00),(157,'Robert Pension','Sales Associate',35000.00),(189,'Morgan Freeman','Mall Owner',650000.00),(204,'Chef','Cook',30000.00),(234,'Jamie Lannister','Sales Associate',25999.00),(321,'Paul Buyon','Supervisor',40000.00),(423,'Mary Allan','Sales Associate',25000.00),(456,'Guy Sensei','Security',30000.00),(498,'Terry Crews','Crew Member',35000.00),(519,'Mary Shelley','Assistant Regional Manager',40000.00),(561,'Erin Barnacle','Barista',11000.00),(567,'Michael Cera','Floor Associate',30000.00),(694,'Charlie Day','Charlie Work',200.00),(731,'Baker Bake','Baker',25000.00),(754,'Lewis Caroll Jr.','Regional Manager',50000.00),(789,'Albert Gator','Sales Associate',35000.00),(826,'Brienne Tarth','Sales Associate',26000.00),(931,'Martin Manage','Manager',55000.00),(967,'John Cena','Supervisor',40000.00),(987,'Alberta Gator','Floor Associate',35000.00);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employs`
--

DROP TABLE IF EXISTS `employs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employs` (
  `store` varchar(50) NOT NULL,
  `eid` int(3) NOT NULL,
  PRIMARY KEY (`store`,`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employs`
--

LOCK TABLES `employs` WRITE;
/*!40000 ALTER TABLE `employs` DISABLE KEYS */;
INSERT INTO `employs` VALUES ('Dillards',157),('Dillards',234),('Dillards',826),('Dillards',931),('Food Court',204),('Food Court',731),('Footlocker',321),('Footlocker',498),('Leaf Village',456),('Patty\'s Pub',101),('Patty\'s Pub',102),('Patty\'s Pub',694),('Starbucks',519),('Starbucks',561),('The Magic Shop',567),('The Magic Shop',967),('The Shire',123),('The Swamp',789),('The Swamp',987),('Yankee Candle',423),('Yankee Candle',754);
/*!40000 ALTER TABLE `employs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory` (
  `name` varchar(50) NOT NULL,
  `item` varchar(25) NOT NULL,
  `quanity` int(4) DEFAULT NULL,
  PRIMARY KEY (`name`,`item`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES ('Dillards','Dryer',5),('Dillards','Gator T-Shirt',9),('Dillards','Large Autumn Candle',5),('Dillards','Men\'s Fitted Suit',6),('Dillards','Washer',3),('Dillards','Winter Boots',30),('Foot Court','Sandwich',301),('Footlocker','NMD R1',30),('Footlocker','Ultra Boost X',20),('JC-Pennys','Dryer',0),('JC-Pennys','Gator T-Shirt',1),('JC-Pennys','Magic Kit',2),('JC-Pennys','Washer',5),('Leaf Village','Jitsu Scroll',2),('Patty\'s Pub','Coors Light',1000),('Patty\'s Pub','Egg',1000),('Starbucks','Cake Pop',79),('Starbucks','Croissant',243),('Starbucks','Hot Coffee',2000),('Starbucks','Iced Coffee',2000),('The Magic Shop','Magic Kit',100),('The Shire','Mead',90),('The Shire','Sandwich',37),('The Swamp','Gator T-Shirt',12),('Yankee Candle','Large Autumn Candle',8),('Yankee Candle','Pine Wax Melt',9);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `item` varchar(25) NOT NULL,
  `brand` varchar(25) DEFAULT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`item`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES ('Cake Pop','Starbucks',1.56),('Coors Light','Millers',4.95),('Croissant','Starbucks',2.29),('Dryer','General Electric',999.99),('Egg','Patty\'s',10.99),('Gator T-Shirt','Cotton Tee',25.95),('Hot Coffee','Starbucks',2.25),('Iced Coffee','Starbucks',3.25),('Jitsu Scroll','Dangerous',99.99),('Large Autumn Candle','Yankee Candle',24.99),('Magic Kit','Mad Scientist',45.95),('Mead','Hobbit Brewed',9.75),('Men\'s Fitted Suit','Gucci',885.99),('NMD R1','Adidas',120.00),('Pine Wax Melt','Yankee Candle',5.99),('Sandwich','Generic',7.99),('Ultra Boost X','Adidas',180.00),('Washer','General Electric',999.99),('Winter Boots','Uggs',35.99);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stores`
--

DROP TABLE IF EXISTS `stores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stores` (
  `name` varchar(50) NOT NULL,
  `location` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stores`
--

LOCK TABLES `stores` WRITE;
/*!40000 ALTER TABLE `stores` DISABLE KEYS */;
INSERT INTO `stores` VALUES ('Dillards','Anchor'),('Food Court','Anchor'),('Footlocker','Level 2 near Food Court'),('JC-Pennys','Anchor'),('Leaf Village','Hidden'),('Patty\'s Pub','Level 1 near Food Court'),('Starbucks','Level 1 near Dillards'),('The Magic Shop','Level 1 near Dillards'),('The Shire','Level 1 near JC-Pennys'),('The Swamp','Outside'),('Yankee Candle','Level 2 near JC-Pennys');
/*!40000 ALTER TABLE `stores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `tid` int(8) NOT NULL,
  `store` varchar(50) DEFAULT NULL,
  `item` varchar(25) DEFAULT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (12345678,'Food Court','Sandwich','Becky Thompson','2015-06-05'),(12357321,'Leaf Village','Jitsu Scroll','Rock Lee','2008-08-16'),(13479524,'Leaf Village','Jitsu Scroll','Rock Lee','2018-04-01'),(13697234,'The Magic Shop','Magic Kit','Rock Lee','2018-02-28'),(13846975,'Patty\'s Pub','Egg','Rock Lee','2018-04-02'),(13976219,'Footlocker','Ultra Boost X','Rock Lee','2016-06-08'),(21359768,'Footlocker','NMD R1','Rock Lee','2017-05-24'),(23154897,'The Shire','Sandwich','John Douglas','2018-02-14'),(31492454,'Starbucks','Cake Pop','Rock Lee','2018-04-01'),(31579245,'Starbucks','Hot Coffee','Cersei Lannister','2018-04-01'),(31597267,'The Swamp','Gator T-Shirt','Lindsey Coppenhagen','2018-03-26'),(32156323,'Footlocker','Ultra Boost X','Linsey Coppenhagen','2018-02-25'),(32158627,'The Shire','Mead','Becky Thompson','2017-11-11'),(32459873,'JC-Pennys','Magic Kit','Cersei Lannister','2017-03-15'),(34862147,'Food Court','Sandwich','John Douglas','2018-06-05'),(42973158,'Dillards','Washer','John Douglas','2017-11-12'),(51348124,'Starbucks','Iced Coffee','Lindsey Coppenhagen','2018-02-25'),(51348925,'Dillards','Dryer','Becky Thompson','2018-01-01'),(54893145,'The Shire','Mead','John Douglas','2017-11-11'),(62155846,'Yankee Candle','Pine Wax Melt','John Douglas','2017-08-25'),(63159469,'Yankee Candle','Large Autumn Candle','Cersei Lannister','2018-03-14'),(64239767,'Patty\'s Pub','Coors Light','Becky Thompson','2015-03-14'),(64893255,'Patty\'s Pub','Coors Light','Lindsey Coppenhagen','2015-03-14'),(65496327,'Patty\'s Pub','Coors Light','John Douglas','2015-03-14'),(65893256,'Yankee Candle','Large Autumn Candle','Lindsey Coppenhagen','2017-10-30'),(75631258,'The Swamp','Gator T-Shirt','John Douglas','2017-08-25'),(78613584,'Yankee Candle','Pine Wax Melt','John Douglas','2017-12-13'),(83119754,'The Shire','Mead','Cersei Lannister','2018-03-14'),(85496513,'The Magic Shop','Magic Kit','Becky Thompson','2017-09-26'),(98765432,'JC-Pennys','Gator T-Shirt','Cersei Lannister','2018-04-01');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-02 15:04:03
