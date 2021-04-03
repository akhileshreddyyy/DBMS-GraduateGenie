DROP DATABASE IF EXISTS `GraduateGenie`;
CREATE DATABASE `GraduateGenie` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE GraduateGenie;
DROP TABLE IF EXISTS `Administration`;
CREATE TABLE `Administration` (
  `Admin ID` varchar(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Last_Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`Admin ID`),
  UNIQUE KEY `Admin ID_UNIQUE` (`Admin ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `College`;
CREATE TABLE `College` (
  `College ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Director` varchar(100) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Message/Motive` text,
  PRIMARY KEY (`College ID`),
  UNIQUE KEY `College ID_UNIQUE` (`College ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Company`;
CREATE TABLE `Company` (
  `Company ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(100) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`Company ID`),
  UNIQUE KEY `Company ID_UNIQUE` (`Company ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Student`;
CREATE TABLE `Student` (
  `Student ID` varchar(10) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Last_Name` varchar(100) NOT NULL,
  `College ID` varchar(10) NOT NULL,
  `House Number` varchar(20) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `DOB` date NOT NULL,
  `Gender` char(1) NOT NULL,
  `Branch` varchar(100) NOT NULL,
  `CGPA` float DEFAULT NULL,
  `Semester` int unsigned DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`Student ID`),
  UNIQUE KEY `Student ID_UNIQUE` (`Student ID`),
  KEY `College ID_Student_idx` (`College ID`),
  CONSTRAINT `College ID_Student` FOREIGN KEY (`College ID`) REFERENCES `College` (`College ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Course`;
CREATE TABLE `Course` (
  `Course ID` varchar(10) NOT NULL,
  `College ID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(100) NOT NULL,
  `Description` varchar(500) NOT NULL,
  `Credits` int unsigned NOT NULL,
  `Semester` varchar(10) NOT NULL,
  PRIMARY KEY (`Course ID`,`College ID`),
  KEY `College ID_Course_idx` (`College ID`),
  CONSTRAINT `College ID_Course` FOREIGN KEY (`College ID`) REFERENCES `College` (`College ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Faculty`;
CREATE TABLE `Faculty` (
  `Faculty ID` varchar(10) NOT NULL,
  `College ID` varchar(10) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Second_Name` varchar(100) NOT NULL,
  `House Number` varchar(20) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `DOB` date NOT NULL,
  `Gender` char(1) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Specialization` varchar(200) DEFAULT NULL,
  `Position` varchar(100) NOT NULL,
  `Experience` int unsigned NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`Faculty ID`),
  UNIQUE KEY `Faculty ID_UNIQUE` (`Faculty ID`),
  KEY `College ID_Faculty_idx` (`College ID`),
  CONSTRAINT `College ID_Faculty` FOREIGN KEY (`College ID`) REFERENCES `College` (`College ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Placement`;
CREATE TABLE `Placement` (
  `College ID` varchar(10) NOT NULL,
  `Year` int unsigned NOT NULL,
  `Total Offers` int NOT NULL,
  `Students Registered` int unsigned NOT NULL,
  `Placement Percentage` float NOT NULL,
  `Companies Visited` int unsigned NOT NULL,
  `Average Package` float unsigned NOT NULL,
  PRIMARY KEY (`College ID`,`Year`),
  CONSTRAINT `College ID_Placement` FOREIGN KEY (`College ID`) REFERENCES `College` (`College ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Faculty Department`;
CREATE TABLE `Faculty Department` (
  `Name` varchar(100) NOT NULL,
  `College ID` varchar(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `HOD ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Name`,`College ID`),
  KEY `College ID_Faculty Department_idx` (`College ID`),
  KEY `HOD ID_Faculty Department_idx` (`HOD ID`),
  CONSTRAINT `College ID_Faculty Department` FOREIGN KEY (`College ID`) REFERENCES `College` (`College ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `HOD ID_Faculty Department` FOREIGN KEY (`HOD ID`) REFERENCES `Faculty` (`Faculty ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Grade`;
CREATE TABLE `Grade` (
  `Student ID` varchar(10) NOT NULL,
  `Course ID` varchar(10) NOT NULL,
  `College ID` varchar(10) NOT NULL,
  `Semester` int unsigned NOT NULL,
  `Grade` float DEFAULT NULL,
  PRIMARY KEY (`Student ID`,`Course ID`,`College ID`),
  KEY `College ID_Grade_idx` (`College ID`),
  KEY `Course ID_Grade_idx` (`Course ID`),
  CONSTRAINT `College ID_Grade` FOREIGN KEY (`College ID`) REFERENCES `Course` (`Course ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Course ID_Grade` FOREIGN KEY (`Course ID`) REFERENCES `Course` (`Course ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Student ID_Grade` FOREIGN KEY (`Student ID`) REFERENCES `Student` (`Student ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;