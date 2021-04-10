DROP DATABASE IF EXISTS `GraduateGenie`;
CREATE DATABASE `GraduateGenie` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE GraduateGenie;
DROP TABLE IF EXISTS `Administration`;
CREATE TABLE `Administration` (
  `AdminID` varchar(10) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Last_Name` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE KEY `AdminID_UNIQUE` (`AdminID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `College`;
CREATE TABLE `College` (
  `CollegeID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Director` varchar(100) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`CollegeID`),
  UNIQUE KEY `CollegeID_UNIQUE` (`CollegeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Company`;
CREATE TABLE `Company` (
  `CompanyID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(100) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Building,Street,Area` varchar(500) NOT NULL,
  `City` varchar(100) NOT NULL,
  `State` varchar(100) NOT NULL,
  `Country` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`CompanyID`),
  UNIQUE KEY `CompanyID_UNIQUE` (`CompanyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Student`;
CREATE TABLE `Student` (
  `StudentID` varchar(13) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Last_Name` varchar(100) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
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
  PRIMARY KEY (`StudentID`),
  UNIQUE KEY `StudentID_UNIQUE` (`StudentID`),
  KEY `CollegeID_Student_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Student` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Course`;
CREATE TABLE `Course` (
  `CourseID` varchar(10) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` varchar(100) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Credits` int unsigned NOT NULL,
  `Semester` varchar(20) NOT NULL,
  PRIMARY KEY (`CourseID`,`CollegeID`),
  KEY `CollegeID_Course_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Course` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Faculty`;
CREATE TABLE `Faculty` (
  `FacultyID` varchar(10) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `First_Name` varchar(100) NOT NULL,
  `Second_Name` varchar(100) NOT NULL,
  `HouseNumber` varchar(20) NOT NULL,
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
  PRIMARY KEY (`FacultyID`),
  UNIQUE KEY `FacultyID_UNIQUE` (`FacultyID`),
  KEY `CollegeID_Faculty_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Faculty` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Placement`;
CREATE TABLE `Placement` (
  `CollegeID` varchar(10) NOT NULL,
  `Year` int unsigned NOT NULL,
  `Total Offers` int NOT NULL,
  `StudentsRegistered` int unsigned NOT NULL,
  `PlacementPercentage` float NOT NULL,
  `CompaniesVisited` int unsigned NOT NULL,
  `AveragePackage` float unsigned NOT NULL,
  PRIMARY KEY (`CollegeID`,`Year`),
  CONSTRAINT `CollegeID_Placement` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `FacultyDepartment`;
CREATE TABLE `FacultyDepartment` (
  `Name` varchar(100) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `HODID` varchar(10) NOT NULL,
  PRIMARY KEY (`Name`,`CollegeID`),
  KEY `CollegeID_FacultyDepartment_idx` (`CollegeID`),
  KEY `HODID_FacultyDepartment_idx` (`HODID`),
  CONSTRAINT `CollegeID_FacultyDepartment` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `HODID_FacultyDepartment` FOREIGN KEY (`HODID`) REFERENCES `Faculty` (`FacultyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Grade`;
CREATE TABLE `Grade` (
  `StudentID` varchar(13) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `CourseID` varchar(10) NOT NULL,
  `Semester` int unsigned NOT NULL,
  `Grade` float DEFAULT NULL,
  PRIMARY KEY (`StudentID`,`CollegeID`,`CourseID`),
  KEY `CourseID_Grade_idx` (`CourseID`),
  KEY `CollegeID_Grade_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Grade` FOREIGN KEY (`CollegeID`) REFERENCES `Student` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Grade` FOREIGN KEY (`CourseID`) REFERENCES `Course` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StudentID_Grade` FOREIGN KEY (`StudentID`) REFERENCES `Student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Studies`;
CREATE TABLE `Studies` (
  `CourseID` varchar(19) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `StudentID` varchar(13) NOT NULL,
  PRIMARY KEY (`CourseID`,`CollegeID`,`StudentID`),
  KEY `CollegeID_Studies_idx` (`CollegeID`),
  KEY `StudentID_Studies_idx` (`StudentID`),
  CONSTRAINT `CollegeID_Studies` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Studies` FOREIGN KEY (`CourseID`) REFERENCES `Course` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StudentID_Studies` FOREIGN KEY (`StudentID`) REFERENCES `Student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS 'Teaches';
CREATE TABLE `Teaches` (
  `CourseID` varchar(10) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  `FacultyID` varchar(10) NOT NULL,
  PRIMARY KEY (`CourseID`,`CollegeID`,`FacultyID`),
  KEY `CollegeID_Teaches_idx` (`CollegeID`),
  KEY `FacultyID_Teaches_idx` (`FacultyID`),
  CONSTRAINT `CollegeID_Teaches` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Teaches` FOREIGN KEY (`CourseID`) REFERENCES `Course` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FacultyID_Teaches` FOREIGN KEY (`FacultyID`) REFERENCES `Faculty` (`FacultyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS 'Visits';
CREATE TABLE `Visits` (
  `CompanyID` varchar(10) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  PRIMARY KEY (`CompanyID`,`CollegeID`),
  KEY `CollegeID_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Visits` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CompanyID_Visits` FOREIGN KEY (`CompanyID`) REFERENCES `Company` (`CompanyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS 'Job_Offer';
CREATE TABLE `Job_Offer` (
  `CompanyID` varchar(10) NOT NULL,
  `StudentID` varchar(13) NOT NULL,
  `Status` varchar(20) NOT NULL,
  PRIMARY KEY (`CompanyID`,`StudentID`),
  KEY `StudentID_Job_Offer_idx` (`StudentID`),
  CONSTRAINT `CompanyID_Job_Offer` FOREIGN KEY (`CompanyID`) REFERENCES `Company` (`CompanyID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `StudentID_Job_Offer` FOREIGN KEY (`StudentID`) REFERENCES `Student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS 'Course_Offer';
CREATE TABLE `Course_Offer` (
  `FacultyDeptName` varchar(100) NOT NULL,
  `CourseID` varchar(10) NOT NULL,
  `CollegeID` varchar(10) NOT NULL,
  PRIMARY KEY (`FacultyDeptName`,`CourseID`,`CollegeID`),
  KEY `CourseID_Course_Offer_idx` (`CourseID`),
  KEY `CollegeID_Course_Offer_idx` (`CollegeID`),
  CONSTRAINT `CollegeID_Course_Offer` FOREIGN KEY (`CollegeID`) REFERENCES `College` (`CollegeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Course_Offer` FOREIGN KEY (`CourseID`) REFERENCES `Course` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FacultyDept_Course_Offer` FOREIGN KEY (`FacultyDeptName`) REFERENCES `FacultyDepartment` (`Name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
