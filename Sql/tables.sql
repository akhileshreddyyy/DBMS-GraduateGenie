-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema graduategenie
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema graduategenie
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `graduategenie` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `graduategenie` ;

-- -----------------------------------------------------
-- Table `graduategenie`.`administration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`administration` (
  `AdminID` VARCHAR(10) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `First_Name` VARCHAR(100) NOT NULL,
  `Last_Name` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE INDEX `AdminID_UNIQUE` (`AdminID` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`college`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`college` (
  `CollegeID` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Building,Street,Area` VARCHAR(500) NOT NULL,
  `City` VARCHAR(100) NOT NULL,
  `State` VARCHAR(100) NOT NULL,
  `Country` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Director` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`CollegeID`),
  UNIQUE INDEX `CollegeID_UNIQUE` (`CollegeID` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`company` (
  `CompanyID` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Type` VARCHAR(100) NOT NULL,
  `Description` MEDIUMTEXT NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  `Building,Street,Area` VARCHAR(500) NOT NULL,
  `City` VARCHAR(100) NOT NULL,
  `State` VARCHAR(100) NOT NULL,
  `Country` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`CompanyID`),
  UNIQUE INDEX `CompanyID_UNIQUE` (`CompanyID` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`course` (
  `CourseID` VARCHAR(10) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `Type` VARCHAR(100) NOT NULL,
  `Description` MEDIUMTEXT NOT NULL,
  `Credits` INT UNSIGNED NOT NULL,
  `Semester` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`CourseID`, `CollegeID`),
  INDEX `CollegeID_Course_idx` (`CollegeID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_Course`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`faculty` (
  `FacultyID` VARCHAR(10) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `First_Name` VARCHAR(100) NOT NULL,
  `Second_Name` VARCHAR(100) NOT NULL,
  `HouseNumber` VARCHAR(20) NOT NULL,
  `Building,Street,Area` VARCHAR(500) NOT NULL,
  `City` VARCHAR(100) NOT NULL,
  `State` VARCHAR(100) NOT NULL,
  `Country` VARCHAR(100) NOT NULL,
  `DOB` DATE NOT NULL,
  `Gender` CHAR(1) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Specialization` VARCHAR(200) NULL DEFAULT NULL,
  `Position` VARCHAR(100) NOT NULL,
  `Experience` INT UNSIGNED NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`FacultyID`),
  UNIQUE INDEX `FacultyID_UNIQUE` (`FacultyID` ASC) VISIBLE,
  INDEX `CollegeID_Faculty_idx` (`CollegeID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_Faculty`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`facultydepartment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`facultydepartment` (
  `Name` VARCHAR(100) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `HODID` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Name`, `CollegeID`),
  INDEX `CollegeID_FacultyDepartment_idx` (`CollegeID` ASC) VISIBLE,
  INDEX `HODID_FacultyDepartment_idx` (`HODID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_FacultyDepartment`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `HODID_FacultyDepartment`
    FOREIGN KEY (`HODID`)
    REFERENCES `graduategenie`.`faculty` (`FacultyID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`student` (
  `StudentID` VARCHAR(13) NOT NULL,
  `First_Name` VARCHAR(100) NOT NULL,
  `Last_Name` VARCHAR(100) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `House Number` VARCHAR(20) NOT NULL,
  `Building,Street,Area` VARCHAR(500) NOT NULL,
  `City` VARCHAR(100) NOT NULL,
  `State` VARCHAR(100) NOT NULL,
  `Country` VARCHAR(100) NOT NULL,
  `DOB` DATE NOT NULL,
  `Gender` CHAR(1) NOT NULL,
  `Branch` VARCHAR(100) NOT NULL,
  `CGPA` FLOAT NULL DEFAULT NULL,
  `Semester` INT UNSIGNED NULL DEFAULT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`StudentID`),
  UNIQUE INDEX `StudentID_UNIQUE` (`StudentID` ASC) VISIBLE,
  INDEX `CollegeID_Student_idx` (`CollegeID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_Student`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`grade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`grade` (
  `StudentID` VARCHAR(13) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `CourseID` VARCHAR(10) NOT NULL,
  `Semester` INT UNSIGNED NOT NULL,
  `Grade` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`StudentID`, `CollegeID`, `CourseID`),
  INDEX `CourseID_Grade_idx` (`CourseID` ASC) VISIBLE,
  INDEX `CollegeID_Grade_idx` (`CollegeID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_Grade`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`student` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Grade`
    FOREIGN KEY (`CourseID`)
    REFERENCES `graduategenie`.`course` (`CourseID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `StudentID_Grade`
    FOREIGN KEY (`StudentID`)
    REFERENCES `graduategenie`.`student` (`StudentID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`placement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`placement` (
  `CollegeID` VARCHAR(10) NOT NULL,
  `Year` INT UNSIGNED NOT NULL,
  `Total Offers` INT NOT NULL,
  `StudentsRegistered` INT UNSIGNED NOT NULL,
  `PlacementPercentage` FLOAT NOT NULL,
  `CompaniesVisited` INT UNSIGNED NOT NULL,
  `AveragePackage` FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY (`CollegeID`, `Year`),
  CONSTRAINT `CollegeID_Placement`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`studies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`studies` (
  `CourseID` VARCHAR(19) NOT NULL,
  `CollegeID` VARCHAR(10) NOT NULL,
  `StudentID` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`CourseID`, `CollegeID`, `StudentID`),
  INDEX `CollegeID_Studies_idx` (`CollegeID` ASC) VISIBLE,
  INDEX `StudentID_Studies_idx` (`StudentID` ASC) VISIBLE,
  CONSTRAINT `CollegeID_Studies`
    FOREIGN KEY (`CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `CourseID_Studies`
    FOREIGN KEY (`CourseID`)
    REFERENCES `graduategenie`.`course` (`CourseID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `StudentID_Studies`
    FOREIGN KEY (`StudentID`)
    REFERENCES `graduategenie`.`student` (`StudentID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`job_offered`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`job_offered` (
  `company_CompanyID` VARCHAR(10) NOT NULL,
  `student_StudentID` VARCHAR(13) NOT NULL,
  `status` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`company_CompanyID`, `student_StudentID`),
  INDEX `fk_company_has_student_student1_idx` (`student_StudentID` ASC) VISIBLE,
  INDEX `fk_company_has_student_company1_idx` (`company_CompanyID` ASC) VISIBLE,
  CONSTRAINT `fk_company_has_student_company1`
    FOREIGN KEY (`company_CompanyID`)
    REFERENCES `graduategenie`.`company` (`CompanyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_company_has_student_student1`
    FOREIGN KEY (`student_StudentID`)
    REFERENCES `graduategenie`.`student` (`StudentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`teaches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`teaches` (
  `course_CourseID` VARCHAR(10) NOT NULL,
  `course_CollegeID` VARCHAR(10) NOT NULL,
  `faculty_FacultyID` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`course_CourseID`, `course_CollegeID`, `faculty_FacultyID`),
  INDEX `fk_course_has_faculty_faculty1_idx` (`faculty_FacultyID` ASC) VISIBLE,
  INDEX `fk_course_has_faculty_course1_idx` (`course_CourseID` ASC, `course_CollegeID` ASC) VISIBLE,
  CONSTRAINT `fk_course_has_faculty_course1`
    FOREIGN KEY (`course_CourseID` , `course_CollegeID`)
    REFERENCES `graduategenie`.`course` (`CourseID` , `CollegeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_has_faculty_faculty1`
    FOREIGN KEY (`faculty_FacultyID`)
    REFERENCES `graduategenie`.`faculty` (`FacultyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `graduategenie`.`visits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `graduategenie`.`visits` (
  `college_CollegeID` VARCHAR(10) NOT NULL,
  `company_CompanyID` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`college_CollegeID`, `company_CompanyID`),
  INDEX `fk_college_has_company_company1_idx` (`company_CompanyID` ASC) VISIBLE,
  INDEX `fk_college_has_company_college1_idx` (`college_CollegeID` ASC) VISIBLE,
  CONSTRAINT `fk_college_has_company_college1`
    FOREIGN KEY (`college_CollegeID`)
    REFERENCES `graduategenie`.`college` (`CollegeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_college_has_company_company1`
    FOREIGN KEY (`company_CompanyID`)
    REFERENCES `graduategenie`.`company` (`CompanyID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
