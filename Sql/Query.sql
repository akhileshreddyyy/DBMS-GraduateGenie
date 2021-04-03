/*Query to find grades when roll number and semester are known*/
SELECT Grade.StudentID,Grade.CourseID,Course.Name,Grade.Semester,Grade.Grade 
FROM GRADE
INNER JOIN Course
ON Course.CourseID=Grade.CourseID
WHERE Grade.StudentID="IIITD2019230" AND Grade.Semester=1

