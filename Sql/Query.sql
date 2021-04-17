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
Know which students are enrolled in their courses when FacultyID is known
*/
SELECT CONCAT(Student.First_Name," ",Student.Last_Name) AS FullName, 
FROM Studies 
INNER JOIN Student
ON Student.StudentID=Studies.StudentID
WHERE Course.CourseID="IIITD001" AND Course.CollegeID="IIITD"

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


/*
Company query
Display details of the colleges which have more than 20 number of courses and their average package>=15 LPA and placement %age>=90
*/
Select college.CollegeID, college.Name, placement.Year, count(course.CollegeID)
from college, placement, course
where college.CollegeID=placement.CollegeID and placement.AveragePackage>=15 
and placement.PlacementPercentage>=90 and college.CollegeID=course.CollegeID
group by course.CollegeID
having count(course.courseID)>20;


/*
College query
Display companies that have hired students from their college
*/
SELECT company.companyID, count(job_offered.student_StudentID)
from company, job_offered
where job_offered.student_StudentID like 'IIITD%' and job_offered.status='hired'
and company.CompanyID=job_offered.company_CompanyID
group by company.CompanyID;


/*
Faculty query
View the details of the courses they are teaching
*/
select teaches.course_CourseID, course.Name, course.Type, course.Description
from teaches, course
where teaches.faculty_FacultyID='IIITD002' 
and teaches.course_CourseID=course.CourseID;


/*
Company query
View the details of the students of a particular college who have CGPA>=8
*/
select student.StudentID, student.First_Name, student.Last_Name, AVG(Grade.Grade) 'CGPA'
from grade, student
where student.StudentID=grade.StudentID and grade.CollegeID='IIITD'
group by student.StudentID
having AVG(Grade.Grade)>=8;


/*
College query
Display the details of the students of their college who have declined the job offer
*/
select job_offered.student_StudentID, student.First_Name, student.Last_Name, job_offered.company_companyID
from job_offered,student
where job_offered.student_StudentID=student.StudentID and job_offered.status='Declined'
and job_offered.student_StudentID like 'IIITD%';



