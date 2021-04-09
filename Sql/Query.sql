/*
STUDENT QUERY
Query to find grades when roll number and semester are known
*/
SELECT Grade.StudentID,Grade.CourseID,Course.Name,Grade.Semester,Grade.Grade
FROM Grade
INNER JOIN Course
ON Course.CourseID=Grade.CourseID
WHERE Grade.StudentID="IIITD2019230" AND Grade.Semester=1;

/*
STUDENT QUERY
Query to find current courses enrolled in when roll number is known
*/
SELECT Studies.StudentID,Studies.CourseID,Course.Name
FROM Studies
INNER JOIN Course
ON Course.CourseID=Studies.CourseID
WHERE Studies.StudentID="IIITD2019230";

/*
STUDENT QUERY
Know Course details (Type can be specified)
*/
SELECT CourseID,Name,Type,Description,Credits,Semester
FROM Course
WHERE CollegeID="IIITD" AND TYPE="CSE";

/*
STUDENT QUERY
Know CGPA when StudentID is known
*/
SELECT Student.StudentID,Student.First_Name,Student.Last_Name,AVG(Grade.Grade) 'CGPA'
FROM Grade
INNER JOIN Student
ON Student.StudentID=Grade.StudentID
WHERE Grade.StudentID="IIITD2019230";

/*
STUDENT QUERY
Know SGPA when StudentID is known along with semester
*/
SELECT Student.StudentID,Student.First_Name,Student.Last_Name,Grade.Semester,AVG(Grade.Grade) 'SGPA'
FROM Grade
INNER JOIN Student
ON Student.StudentID=Grade.StudentID
WHERE Grade.StudentID="IIITD2019230" AND Grade.Semester=1;

/*
INSERT QUERY USED BY
+Admins(To add colleges,students,companies,facultydept,faculty,placements,grades,courses),
+College(To add students,faculty,facultydept,Grades,Courses,placement)
+Faculty(To add grades)
*/
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);

/*
FACULTY QUERY
Know Courses taught when FacultyID is known
*/
SELECT Course.CourseID,Course.Name
FROM Faculty INNER JOIN Course
ON Faculty.FacultyID=Course.FacultyID
WHERE Faculty.FacultyID="IIITD001";

/*
FACULTY QUERY
Know which students are enrolled in their courses when FacultyID is known
*/
SELECT Course.CourseID,Course.Name,Student.StudentID,CONCAT(Student.First_Name," ",Student.Last_Name) AS FullName
FROM Faculty,Course,Studies,Student
WHERE Faculty.FacultyID=Course.FacultyID
AND Course.CourseID=Studies.CourseID
AND Studies.StudentID=Student.StudentID
AND Faculty.FacultyID="IIITD001";

/*
FACULTY QUERY
Update student grades when FacultyID,StudentID,CourseID,CollegeID,Grade are known
*/
UPDATE Grade
SET Grade.Grade="8"
WHERE Grade.StudentID="IIITD2019230"
AND Grade.CourseID="COM101"
AND Grade.CollegeID="IIITD";

/*
ADMINISTRATION QUERY
Show company details when CompanyID is known
*/
SELECT CompanyID,Name,Type,Description,CONCAT(City,", ",State,", ",Country) AS Address,Email
FROM Company
WHERE CompanyID="GS";

/*
COMPANY QUERY
List students when CollegeID is known
*/
SELECT Student.StudentID,CONCAT(Student.First_Name," ",Student.Last_Name) AS FullName
FROM Student
WHERE Student.CollegeID="IIITD";
