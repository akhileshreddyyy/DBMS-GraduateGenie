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

DROP TABLE IF EXISTS `Student`;
CREATE TABLE `Student` (
  `Student ID` int NOT NULL,
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