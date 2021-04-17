import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class GraduateGenie {
    static final String driver="com.mysql.cj.jdbc.Driver";
    static final String url="jdbc:mysql://localhost/";
    static String user="OutsiderUser";
    static String password="GGOutsider";
    static reader input=new reader();
    static Connection connection=null;
    static Statement statement=null;
    public static void main (String args[]){
        try{
            UserChange();
            Start();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(statement!=null)
                    statement.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            try{
                if(connection!=null)
                    connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void Start() throws SQLException, ParseException, ClassNotFoundException {
        boolean check1=true;
        while(check1){
            System.out.println("Welcome to Graduate Genie !!");
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:Sign In");
            System.out.println("2:Register");
            System.out.println("3:Exit");
            int choice=input.nextInt();
            boolean check2=true;
            if(choice==1){
                while(check2){
                    System.out.println("You have chosen to sign in.");
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:Student Login");
                    System.out.println("2:Faculty Login");
                    System.out.println("3:College Login");
                    System.out.println("4:Company Login");
                    System.out.println("5:Administration Login");
                    System.out.println("6:Back");
                    System.out.println("7:Exit");
                    int query=input.nextInt();
                    if(query==1||query==2||query==3||query==4||query==5){
                        Status status=Login(query);
                        check1=status.exit;
                        check2=status.back;
                    }else if(query==6){
                        break;
                    }else if(query==7){
                        check1=false;
                        System.out.println("Goodbye !!");
                        break;
                    }else
                        System.out.println("Invalid Request!Please try again");
                }
            }else if(choice==2){
                while(check2){
                    System.out.println("You have chosen to register.");
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:College Registration");
                    System.out.println("2:Company Registration");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int query=input.nextInt();
                    if(query==1||query==2){
                        Status status=Registration(query,true);
                        check1=status.exit;
                        check2=status.back;
                    }else if(query==3){
                        break;
                    }else if(query==4){
                        check1=false;
                        System.out.println("Goodbye !!");
                        break;
                    }else
                        System.out.println("Invalid Request!Please try again");
                }
            }else if(choice==3){
                System.out.println("Goodbye !!");
                break;
            }
            else
                System.out.println("Invalid Request!Please try again");
        }
    }
    public static Status Login(int query) throws SQLException, ParseException, ClassNotFoundException {
        boolean check1=true;
        while(check1){
            user="OutsiderUser";
            password="GGOutsider";
            UserChange();
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:Enter Login Credentials");
            System.out.println("2:Back");
            System.out.println("3:Exit");
            int choice=input.nextInt();
            if(choice==1) {
                ResultSet RS;
                while (true){
                    System.out.println(LoginHelper1(query));
                    String ID=input.next();
                    System.out.println("Enter Password:");
                    String pwd=input.next();
                    RS= statement.executeQuery(LoginHelper2(query,ID,pwd));
                    if(RS.next()) {
                        System.out.println("Login successful.");
                        Status status;
                        if(query==1){
                            user="StudentUser";
                            password="GGStudent";
                            UserChange();
                            status=StudentQueries(ID);
                        }else if(query==2) {
                            user="FacultyUser";
                            password="GGFaculty";
                            UserChange();
                            status = FacultyQueries(ID);
                        } else if(query==3) {
                            user="CollegeUser";
                            password="GGCollege";
                            UserChange();
                            status = CollegeQueries(ID);
                        }else if(query==4) {
                            user="CompanyUser";
                            password="GGCompany";
                            UserChange();
                            status = CompanyQueries(ID);
                        }else {
                            user="AdminUser";
                            password="GGAdmin";
                            UserChange();
                            status = AdministrationQueries(ID);
                        }
                        if(!status.exit)
                            return status;
                        else
                            break;
                    }
                    else {
                        System.out.println("Incorrect ID or Password.Please try again.");
                        break;
                    }
                }
                System.out.println();
            }else if(choice==2){
                return new Status(false,true);
            }else if(choice==3){
                System.out.println("Goodbye !!");
                return new Status(false,false);
            }else{
                System.out.println("Invalid Request!Please try again");
            }
        }
        return null;
    }
    public static String LoginHelper1(int query){
        if(query == 1){
            return "Enter StudentID:";
        }else if (query == 2){
            return "Enter FacultyID:";
        }else if (query == 3){
            return "Enter CollegeID:";
        }else if(query==4){
            return "Enter CompanyID:";
        }else
            return "Enter AdminID:";
    }
    public static String LoginHelper2(int query,String ID,String pwd){
        if (query == 1) {
            return "SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+ID+"\" AND Password=\""+pwd+"\"";
        } else if (query == 2) {
            return "SELECT * FROM GraduateGenie.Faculty WHERE FacultyID=\""+ID+"\" AND Password=\""+pwd+"\"";
        } else if (query == 3){
            return "SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+ID+"\" AND Password=\""+pwd+"\"";
        }else if(query==4){
            return "SELECT * FROM GraduateGenie.Company WHERE CompanyID=\""+ID+"\" AND Password=\""+pwd+"\"";
        }else{
            return "SELECT * FROM GraduateGenie.Administration WHERE AdminID=\""+ID+"\" AND Password=\""+pwd+"\"";
        }
    }
    public static Status StudentQueries(String StudentID) throws SQLException, ParseException {
        String studentquery="SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+StudentID+"\"";
        ResultSet Student= statement.executeQuery(studentquery);
        Student.next();
        System.out.println("Hello "+Student.getString("First_Name"));
        String college=Student.getString("CollegeID");
        boolean check1=true;
        while(check1) {
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:Check student details");
            System.out.println("2:Edit student details");
            System.out.println("3:Check grades");
            System.out.println("4:Check courses for current semester");
            System.out.println("5:Register for courses");
            System.out.println("6:Check course details");
            System.out.println("7:Back");
            System.out.println("8:Exit");
            int choice=input.nextInt();
            if(choice==1){
                StudentDetails(StudentID,1);
            }else if(choice==2){
                boolean cont=StudentDetails(StudentID,2);
                if(!cont){
                    return new Status(false,false);
                }
            }else if(choice==3){
                boolean cont=StudentGrades(StudentID,1);
                if(!cont){
                    return new Status(false,false);
                }
            }else if(choice==4){
                boolean cont=StudentCourses(StudentID,1);
                if(!cont){
                    return new Status(false,false);
                }
            }else if(choice==5){
                System.out.println("Course Registration:");
                boolean cont=StudentCourses(StudentID,2);
                if(!cont){
                    return new Status(false,false);
                }
            }else if(choice==6){
                System.out.println("Course Details:");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View courses according to semester");
                    System.out.println("2:View courses according to type");
                    System.out.println("3:View course details");
                    System.out.println("4:Back");
                    System.out.println("5:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Enter semester according to the following codes:");
                        System.out.println("1:Monsoon semester");
                        System.out.println("2:Winter semester");
                        int sem=input.nextInt();
                        String semester=null;
                        boolean valid=true;
                        if(sem==1){
                            semester="Monsoon";
                        }else if(sem==2){
                            semester="Winter";
                        }else{
                            System.out.println("Invalid input.");
                            valid=false;
                        }
                        if(valid) {
                            ResultSet r = statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CollegeID=\"" + college + "\" AND (Semester=\"" + semester + "\" OR Semester=\"" + "Monsoon/Winter" + "\")");
                            int count = 0;
                            System.out.println("Courses for " + semester + " semester:");
                            while (r.next()) {
                                count++;
                                System.out.println(count + ". CourseID:" + r.getString("CourseID") + ", Name:" + r.getString("Name"));
                            }
                        }
                    }else if(newchoice==2){
                        System.out.println("Enter course type according to the following codes:");
                        System.out.println("1:BIO");
                        System.out.println("2:CSE");
                        System.out.println("3:DES");
                        System.out.println("4:ECE");
                        System.out.println("5:MTH");
                        System.out.println("6:SSH");
                        int type=input.nextInt();
                        String coursetype=null;
                        boolean valid=true;
                        if(type==1)
                            coursetype="BIO";
                        else if(type==2)
                            coursetype="CSE";
                        else if(type==3)
                            coursetype="DES";
                        else if(type==4)
                            coursetype="ECE";
                        else if(type==5)
                            coursetype="MTH";
                        else if(type==6)
                            coursetype="SSH";
                        else {
                            System.out.println("Invalid input.");
                            valid=false;
                        }
                        if(valid){
                            ResultSet r = statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CollegeID=\"" + college + "\" AND Type=\"" +coursetype + "\"");
                            int count = 0;
                            System.out.println("Courses of type " +coursetype);
                            while (r.next()) {
                                count++;
                                System.out.println(count + ". CourseID:" + r.getString("CourseID") + ", Name:" + r.getString("Name"));
                            }
                        }
                    }else if(newchoice==3){
                        System.out.println("Course Details");
                        System.out.println("Enter CourseID: ");
                        String course=input.next();
                        ResultSet r = statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CollegeID=\"" + college + "\" AND CourseID=\"" +course + "\"");
                        if(r.next()){
                            System.out.println("CourseID:"+r.getString("CourseID"));
                            System.out.println("Name:"+r.getString("Name"));
                            System.out.println("Type:"+r.getString("Type"));
                            System.out.println("Description:"+r.getString("Description"));
                            System.out.println("Credits:"+r.getInt("Credits"));
                            System.out.println("Semester:"+r.getString("Semester"));
                        }else
                            System.out.println("Invalid CourseID.");
                    }else if(newchoice==4){
                        break;
                    }else if(newchoice==5){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                    }
                    System.out.println();
                }
            }else if(choice==7){
                Student.close();
                return new Status(false,true);
            }else if(choice==8){
                Student.close();
                return new Status(false,false);
            }else{
                System.out.println("Invalid Request!Please try again");
            }
            System.out.println();
        }
        return null;
    }
    public static Status FacultyQueries(String FacultyID) throws SQLException, ParseException {
        String facultyquery="SELECT * FROM GraduateGenie.Faculty WHERE FacultyID=\""+FacultyID+"\"";
        ResultSet Faculty= statement.executeQuery(facultyquery);
        Faculty.next();
        System.out.println("Hello "+Faculty.getString("First_Name"));
        boolean check1=true;
        while(check1) {
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:View faculty details");
            System.out.println("2:Edit faculty details");
            System.out.println("3:View Courses for the semester");
            System.out.println("4:View and edit course information");
            System.out.println("5:View Students for a particular course");
            System.out.println("6:Add/Update student grades");
            System.out.println("7:Back");
            System.out.println("8:Exit");
            int choice = input.nextInt();
            if (choice == 1) {
                FacultyDetails(FacultyID,1);
            }else if(choice==2){
                boolean cont=FacultyDetails(FacultyID,2);
                if(!cont)
                    return new Status(false,false);
            }else if(choice==3){
                Faculty= statement.executeQuery(facultyquery);
                Faculty.next();
                System.out.println("Courses for the semester are:");
                String query1=" SELECT GraduateGenie.Teaches.CourseID,GraduateGenie.Course.Name FROM Teaches INNER JOIN Course " +
                        "ON GraduateGenie.Course.CourseID=GraduateGenie.Teaches.CourseID WHERE Teaches.FacultyID=\""+FacultyID+"\";";
                ResultSet r= statement.executeQuery(query1);
                while(r.next()){
                    System.out.println(r.getString("CourseID")+" "+r.getString("Name"));
                }
            }else if(choice==4){
                Faculty= statement.executeQuery(facultyquery);
                Faculty.next();
                String college=Faculty.getString("CollegeID");
                boolean check2=true;
                while(check2){
                    System.out.println("You have chosen to view and edit course information:");
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View Course Information");
                    System.out.println("2:Edit Course Information");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("You have chosen to view course information");
                        System.out.println("Enter CourseID");
                        String course=input.next();
                        String query=" SELECT * FROM Teaches INNER JOIN Course ON GraduateGenie.Course.CourseID=GraduateGenie.Teaches.CourseID " +
                                "WHERE GraduateGenie.Teaches.FacultyID=\""+FacultyID+"\" AND GraduateGenie.Teaches.CourseID=\""+course+"\" AND GraduateGenie.Teaches.CollegeID=\""+college+"\";";
                        ResultSet r= statement.executeQuery(query);
                        if(r.next()){
                            CourseDetails(course,college,1);
                        }else{
                            System.out.println("Invalid CourseID.");
                        }
                    }else if(newchoice==2){
                        System.out.println("You have chosen to edit course information");
                        System.out.println("Enter CourseID");
                        String course=input.next();
                        String query1="SELECT * FROM GraduateGenie.Teaches " +
                                "WHERE CourseID=\""+course+"\" AND CollegeID=\""+college+"\" AND FacultyID=\""+FacultyID+"\";";
                        ResultSet r1= statement.executeQuery(query1);
                        if(r1.next()){
                            boolean cont=CourseDetails(course,college,2);
                            if(!cont)
                                return new Status(false,false);
                        }else{
                            System.out.println("Invalid CourseID.");
                        }
                    }else if(newchoice==3){
                        break;
                    }else if(newchoice==4){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                    }
                    System.out.println();
                }
            }else if(choice==5){
                Faculty= statement.executeQuery(facultyquery);
                Faculty.next();
                String college=Faculty.getString("CollegeID");
                System.out.println("Enter CourseID for which student list is required:");
                String course=input.next();
                String query1=" SELECT * FROM Teaches WHERE GraduateGenie.Teaches.FacultyID=\""+FacultyID+"\" AND GraduateGenie.Teaches.CourseID=\""+course+"\" AND GraduateGenie.Teaches.CollegeID=\""+college+"\";";
                ResultSet r= statement.executeQuery(query1);
                if(r.next()){
                    String query2="SELECT CONCAT(GraduateGenie.Student.First_Name,\" \",GraduateGenie.Student.Last_Name) AS FullName,GraduateGenie.Student.StudentID FROM GraduateGenie.Studies " +
                            "INNER JOIN GraduateGenie.Student ON GraduateGenie.Student.StudentID=GraduateGenie.Studies.StudentID " +
                            "WHERE GraduateGenie.Studies.CourseID=\""+course+"\" AND GraduateGenie.Studies.CollegeID=\""+college+"\" "+
                            "ORDER BY StudentID ASC";
                    r= statement.executeQuery(query2);
                    int count=0;
                    while(r.next()){
                        count++;
                        System.out.println(r.getString("StudentID")+" "+r.getString("FullName"));
                    }
                    System.out.println("Total Students: "+count);
                }else{
                    System.out.println("Invalid CollegeID");
                }
            }else if(choice==6){
                Faculty= statement.executeQuery(facultyquery);
                Faculty.next();
                String college=Faculty.getString("CollegeID");
                System.out.println("Enter CourseID:");
                String course=input.next();
                String query1=" SELECT * FROM Teaches WHERE GraduateGenie.Teaches.FacultyID=\""+FacultyID+"\" AND GraduateGenie.Teaches.CourseID=\""+course+"\" AND GraduateGenie.Teaches.CollegeID=\""+college+"\";";
                ResultSet r= statement.executeQuery(query1);
                if(r.next()){
                    while(true){
                        System.out.println("Please enter the necessary code corresponding to your query:");
                        System.out.println("1:View Student Grade");
                        System.out.println("2:Add/Edit Student Grade");
                        System.out.println("3:Back");
                        System.out.println("4:Exit");
                        int newchoice=input.nextInt();
                        if(newchoice==1){
                            System.out.println("You have chosen to view student grade");
                            System.out.println("Enter StudentID:");
                            String SID=input.next();
                            r= statement.executeQuery("SELECT * FROM GraduateGenie.Studies WHERE StudentID=\""+SID+"\" AND CourseID=\""+course+"\" AND CollegeID=\""+college+"\"");
                            if(r.next()){
                                r= statement.executeQuery("SELECT Grade.Grade FROM GraduateGenie.Grade WHERE StudentID=\""+SID+"\" AND CourseID=\""+course+"\" AND CollegeID=\""+college+"\"");
                                if(r.next()){
                                    System.out.println("Grade for StudentID="+SID+": "+r.getFloat("Grade"));
                                }else
                                    System.out.println("Grade not present");
                            }else{
                                System.out.println("Invalid StudentID.(Or Grade not present)");
                            }
                        }
                        else if(newchoice==2){
                            System.out.println("You have chosen to add/edit student grade");
                            System.out.println("Enter StudentID:");
                            String SID=input.next();
                            r= statement.executeQuery("SELECT * FROM GraduateGenie.Studies WHERE StudentID=\""+SID+"\" AND CourseID=\""+course+"\" AND CollegeID=\""+college+"\"");
                            if(r.next()){
                                System.out.println("Enter grade:");
                                float grade = (float) input.nextDouble();
                                r=statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\"");
                                r.next();
                                int sem=r.getInt("Semester");
                                statement.executeUpdate("INSERT INTO GraduateGenie.Grade (StudentID, CollegeID, CourseID, Semester, Grade) " +
                                        "VALUES(\""+SID+"\", \""+college+"\", \""+course+"\", "+sem+", "+grade+" ) ON DUPLICATE KEY UPDATE Grade=\""+grade+"\"");
                                System.out.println("Grade added/updated.");
                            }else {
                                System.out.println("Invalid StudentID.");
                            }
                        }else if(newchoice==3){
                            break;
                        }else if(newchoice==4){
                            return new Status(false,false);
                        }else{
                            System.out.println("Invalid Request!Please try again");
                        }
                    }
                }else{
                    System.out.println("Invalid CollegeID");
                }
            }else if(choice==7){
                Faculty.close();
                return new Status(false,true);
            }else if(choice==8){
                Faculty.close();
                return new Status(false,false);
            }else{
                System.out.println("Invalid Request!Please try again");
            }
            System.out.println();
        }
        return null;
    }
    public static Status CollegeQueries(String CollegeID) throws SQLException, ParseException {
        String collegequery="SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+CollegeID+"\"";
        ResultSet College= statement.executeQuery(collegequery);
        College.next();
        System.out.println(College.getString("Name"));
        boolean check1=true;
        while(check1){
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:College Related Query");
            System.out.println("2:Student Related Query");
            System.out.println("3:Faculty/Academics Related Query");
            System.out.println("4:Company Related Query");
            System.out.println("5:Back");
            System.out.println("6:Exit");
            int choice = input.nextInt();
            if(choice==1){
                System.out.println("College Related Query");
                while(true){
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View College Details");
                    System.out.println("2:Edit College Details");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        CollegeDetails(CollegeID,1);
                    }else if(newchoice==2){
                        boolean cont=CollegeDetails(CollegeID,2);
                        if(!cont){
                            return new Status(false,false);
                        }
                    }else if(newchoice==3){
                        break;
                    }else if(newchoice==4){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                        System.out.println();
                    }
                }
            }else if(choice==2){
                System.out.println("Student Related Query");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:Add Student");
                    System.out.println("2:View Student Details");
                    System.out.println("3:Edit Student Details");
                    System.out.println("4:View Student Grades");
                    System.out.println("5:Edit Student Grades");
                    System.out.println("6:View Student Courses");
                    System.out.println("7:Edit Student Courses");
                    System.out.println("8:Back");
                    System.out.println("9:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Add Student");
                        System.out.println("Enter StudentID:");
                        String SID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\"");
                        if(r.next()){
                            System.out.println("StudentID already present");
                        }else{
                            System.out.println("Enter first name: ");
                            String fname=input.next();
                            System.out.println("Enter last name: ");
                            String lname=input.next();
                            System.out.println("Enter house number: ");
                            String hno=input.nextLine();
                            System.out.println("Enter building/street/area: ");
                            String bsa=input.nextLine();
                            System.out.println("Enter city:");
                            String city=input.nextLine();
                            System.out.println("Enter state:");
                            String state=input.nextLine();
                            System.out.println("Enter country:");
                            String country=input.nextLine();
                            System.out.println("Enter D.O.B.(YYYY-MM-DD):");
                            String db=input.next();
                            Date date=new SimpleDateFormat("yyyy-MM-dd").parse(db);
                            java.sql.Date dob=new java.sql.Date(date.getTime());
                            System.out.println("Enter Gender(M/F/0):");
                            String gen=input.next();
                            System.out.println("Enter Branch:");
                            String branch=input.nextLine();
                            System.out.println("Enter Semester:");
                            int sem=input.nextInt();
                            System.out.println("Enter EmailID:");
                            String mail=input.next();
                            System.out.println("Enter Password(8-20 characters long): ");
                            String pwd=input.next();
                            String Add="INSERT INTO GraduateGenie.Student (`StudentID`,`First_Name`,`Last_Name`,`CollegeID`,`HouseNumber`,`Building,Street,Area`,`City`,`State`,`Country`,`DOB`,`Gender`,`Branch`,`Semester`,`Email`,`Password`) " +
                                    "VALUES (\""+SID+"\",\""+fname+"\",\""+lname+"\",\""+CollegeID+"\",\""+hno+"\",\""+bsa+"\",\""+city+"\",\""+state+"\",\""+country+"\",\""+dob+"\",\""+gen+"\",\""+branch+"\","+sem+",\""+mail+"\",\""+pwd+"\")";
                            statement.executeUpdate(Add);
                            System.out.println("Student account created successfully.");
                        }
                    }else if(newchoice==2){
                        System.out.println("View Student Details");
                        System.out.println("Enter StudentID:");
                        String SID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            StudentDetails(SID,1);
                        }else{
                            System.out.println("Invalid StudentID.");
                        }
                    }else if(newchoice==3){
                        System.out.println("Edit Student Details");
                        System.out.println("Enter StudentID:");
                        String SID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            boolean cont=StudentDetails(SID,2);
                            if(!cont){
                                return new Status(false,false);
                            }
                        }else{
                            System.out.println("Invalid StudentID.");
                        }
                    }else if(newchoice==4){
                        System.out.println("View Student Grades");
                        System.out.println("Enter StudentID:");
                        String SID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            boolean cont=StudentGrades(SID,1);
                            if(!cont){
                                return new Status(false,false);
                            }
                        }else{
                            System.out.println("Invalid StudentID.");
                        }
                    }else if(newchoice==5){
                        System.out.println("Edit Student Grades");
                        System.out.println("Edit CourseID:");
                        String course=input.next();
                        System.out.println("Enter StudentID:");
                        String SID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            r=statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+course+"\" AND CollegeID=\""+CollegeID+"\"");
                            if(r.next()){
                                System.out.println("Enter grade:");
                                float grade=(float)input.nextDouble();
                                r=statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\"");
                                r.next();
                                int sem=r.getInt("Semester");
                                statement.executeUpdate("INSERT INTO GraduateGenie.Grade (StudentID, CollegeID, CourseID, Semester, Grade) " +
                                        "VALUES(\""+SID+"\", \""+CollegeID+"\", \""+course+"\", "+sem+", "+grade+" ) ON DUPLICATE KEY UPDATE Grade=\""+grade+"\"");
                                System.out.println("Grade added/updated.");
                            }else{
                                System.out.println("Invalid CourseID.");
                            }
                        }else{
                            System.out.println("Invalid StudentID.");
                        }
                    }else if(newchoice==6){
                        System.out.println("View student courses");
                        System.out.println("Enter StudentID");
                        String SID=input.next();
                        String query="SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"";
                        ResultSet r= statement.executeQuery(query);
                        if(r.next())
                            StudentCourses(SID,1);
                        else
                            System.out.println("Invalid StudentID.");
                    }else if(newchoice==7){
                        System.out.println("Edit student courses");
                        System.out.println("Enter StudentID");
                        String SID=input.next();
                        String query="SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"";
                        ResultSet r= statement.executeQuery(query);
                        if(r.next()) {
                            boolean cont=StudentCourses(SID, 2);
                            if(!cont)
                                return new Status(false,false);
                        }
                        else
                            System.out.println("Invalid StudentID.");
                    }else if(newchoice==8){
                        break;
                    }else if(newchoice==9){
                        return new Status(false,false);
                    }else
                        System.out.println("Invalid Request!Please try again");
                    System.out.println();
                }
                System.out.println();
            }else if(choice==3){
                System.out.println("Faculty/Academics Related Query");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View faculty details");
                    System.out.println("2:Edit faculty details");
                    System.out.println("3:View faculty department details");
                    System.out.println("4:Edit faculty department details");
                    System.out.println("5:View course information ");
                    System.out.println("6:Edit course information");
                    System.out.println("7:Back");
                    System.out.println("8:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Enter FacultyID");
                        String FID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Faculty WHERE FacultyID=\""+FID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            FacultyDetails(FID,1);
                        }else{
                            System.out.println("Invalid FacultyID.");
                        }
                    }else if(newchoice==2){
                        System.out.println("Enter FacultyID");
                        String FID=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Faculty WHERE FacultyID=\""+FID+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()){
                            System.out.println("Please enter the necessary code corresponding to your query:");
                            System.out.println("1:Edit Faculty Details");
                            System.out.println("2:Delete Faculty");
                            int option=input.nextInt();
                            if(option==1){
                                boolean cont=FacultyDetails(FID,2);
                                if(!cont)
                                    return new Status(false,false);
                            }else if(option==2){
                                statement.executeUpdate("DELETE FROM GraduateGenie.Faculty WHERE FacultyID=\""+FID+"\"");
                                System.out.println("Faculty deleted successfully");
                            }else
                                System.out.println("Invalid input.");
                        }else{
                            System.out.println("Invalid FacultyID.");
                        }
                    }else if(newchoice==3){
                        System.out.println("Faculty Department");
                        ResultSet r= statement.executeQuery("SELECT Name,GraduateGenie.FacultyDepartment.Email,HODID,Concat(First_Name,\" \",Last_Name) AS HOD_Name " +
                                "FROM GraduateGenie.FacultyDepartment " +
                                "INNER JOIN GraduateGenie.Faculty " +
                                "ON GraduateGenie.Faculty.FacultyID=GraduateGenie.FacultyDepartment.HODID " +
                                "WHERE GraduateGenie.Faculty.CollegeID=\""+CollegeID+"\"");
                        while (r.next()){
                            System.out.println(r.getString("Name")+", Email:"+r.getString(2)+", HODID:"+r.getString(3)+", HOD Name:"+r.getString(4));
                        }
                    }else if(newchoice==4){
                        System.out.println("Enter faculty department name:");
                        String fname=input.nextLine();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.FacultyDepartment WHERE CollegeID=\""+CollegeID+"\" AND Name=\""+fname+"\"");
                        if(r.next()){
                            System.out.println("Please enter the necessary code corresponding to your query:");
                            System.out.println("1:Add/Edit faculty department");
                            System.out.println("2:Delete faculty department");
                            int option=input.nextInt();
                            if(option==1){
                                System.out.println("Enter faculty department name:");
                                String name=input.nextLine();
                                System.out.println("Enter faculty department email:");
                                String mail=input.next();
                                System.out.println("Enter faculty department HODID:");
                                String hod=input.next();
                                statement.executeUpdate("INSERT INTO GraduateGenie.FacultyDepartment (Name, CollegeID, Email, HODID) "+
                                        "VALUES(\""+name+"\","+CollegeID+","+mail+","+hod+") " +
                                        "ON DUPLICATE KEY UPDATE Email=\""+mail+"\",HODID=\""+hod+"\")");
                                System.out.println("Faculty department added/updated");
                            }else if(option==2){
                                statement.executeUpdate("DELETE FROM GraduateGenie.FacultyDepartment WHERE CollegeID=\""+CollegeID+"\" AND Name=\""+fname+"\"");
                                System.out.println("Faculty department deleted successfully");
                            }else{
                                System.out.println("Invalid input.");
                            }
                        }else
                            System.out.println("Invalid faculty department name.");
                    }else if(newchoice==5){
                        System.out.println("Enter CourseID:");
                        String course=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+course+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()) {
                            CourseDetails(course, CollegeID, 1);
                        }else
                            System.out.println("Invalid CourseID.");
                    }else if(newchoice==6){
                        System.out.println("Enter CourseID:");
                        String course=input.next();
                        ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+course+"\" AND CollegeID=\""+CollegeID+"\"");
                        if(r.next()) {
                            System.out.println("Please enter the necessary code corresponding to your query:");
                            System.out.println("1:Edit course");
                            System.out.println("2:Delete course");
                            int option=input.nextInt();
                            if(option==1){
                                boolean cont=CourseDetails(course,CollegeID,2);
                                if(!cont)
                                    return new Status(false,false);
                            }else if(option==2){
                                statement.executeUpdate("DELETE FROM GraduateGenie.Course WHERE CollegeID=\""+CollegeID+"\" AND CourseID=\""+course+"\"");
                                System.out.println("Course deleted successfully");
                            }else
                                System.out.println("Invalid input.");
                        }else
                            System.out.println("Invalid CourseID.");
                    }
                    else if(newchoice==7) {
                        break;
                    }else if(newchoice==8) {
                        return new Status(false, false);
                    }else
                        System.out.println("Invalid Request!Please try again");
                    System.out.println();
                }
                System.out.println();
            }else if(choice==4){
                System.out.println("Company Related Query");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View colleges visiting campus");
                    System.out.println("2:View student placement status");
                    System.out.println("3:View placement statistics");
                    System.out.println("4:Add/Update placement statistics");
                    System.out.println("5:Back");
                    System.out.println("6:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Companies visiting campus:");
                        String query1="SELECT GraduateGenie.Company.Name FROM GraduateGenie.Company, GraduateGenie.Visits " +
                                "WHERE GraduateGenie.Visits.CompanyID=GraduateGenie.Company.CompanyID AND CollegeID=\""+CollegeID+"\"";
                        ResultSet r= statement.executeQuery(query1);
                        int count=0;
                        while(r.next()){
                            count++;
                            System.out.println(count+". "+r.getString("Name"));
                        }
                        String query2="SELECT COUNT(GraduateGenie.Visits.CompanyID) AS Total FROM GraduateGenie.Visits " +
                                "WHERE CollegeID=\""+CollegeID+"\" GROUP BY CollegeID";
                        r= statement.executeQuery(query2);
                        r.next();
                        System.out.println("Total companies visiting: "+r.getInt("Total"));
                        System.out.println();
                    }else if(newchoice==2){
                        while(true){
                            System.out.println("Student Placement Status");
                            System.out.println("Please enter the necessary code corresponding to your query:");
                            System.out.println("1:Generate overall review.");
                            System.out.println("2:Check student status");
                            System.out.println("3:Back");
                            System.out.println("4:Exit");
                            int newchoice1=input.nextInt();
                            if(newchoice1==1){
                                    System.out.println("Overall Review:");
                                    System.out.println("Company wise review(Job/Internship offers):");
                                    String query1="SELECT GraduateGenie.Company.Name,COUNT(GraduateGenie.Job_Offer.StudentID) AS Total " +
                                            "FROM GraduateGenie.Job_Offer " +
                                            "INNER JOIN GraduateGenie.Company ON GraduateGenie.Job_Offer.CompanyID=GraduateGenie.Company.CompanyID "+
                                            "INNER JOIN GraduateGenie.Student ON GraduateGenie.Job_Offer.StudentID=GraduateGenie.Student.StudentID "+
                                            "WHERE GraduateGenie.Student.CollegeID=\""+CollegeID+"\" GROUP BY GraduateGenie.Job_Offer.CompanyID ORDER BY Total DESC;";
                                    ResultSet r= statement.executeQuery(query1);
                                    int count=0;
                                    while(r.next()){
                                        count++;
                                        System.out.println(count+". "+r.getString("Name")+": "+r.getInt("Total"));
                                    }
                                    System.out.println();
                                    System.out.println("Status wise review:");
                                    String query2="SELECT GraduateGenie.Job_Offer.Status AS Status,COUNT(GraduateGenie.Job_Offer.StudentID) AS Total " +
                                            "FROM GraduateGenie.Job_Offer " +
                                            "INNER JOIN GraduateGenie.Student ON GraduateGenie.Job_Offer.StudentID=GraduateGenie.Student.StudentID "+
                                            "WHERE GraduateGenie.Student.CollegeID=\""+CollegeID+"\" GROUP BY Status ORDER BY Status;";
                                    r= statement.executeQuery(query2);
                                    count=0;
                                    while(r.next()){
                                        count++;
                                        System.out.println(count+". "+r.getString("Status")+": "+r.getInt("Total"));
                                    }
                                    System.out.println();
                            }else if(newchoice1==2){
                                System.out.println("Student status");
                                System.out.println("Enter StudentID:");
                                String SID=input.next();
                                ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+CollegeID+"\"");
                                if(r.next()){
                                    System.out.println("Internship/Job offers for StudentID="+SID+":");
                                    r=statement.executeQuery("SELECT * FROM GraduateGenie.Job_Offer WHERE StudentID=\""+SID+"\"");
                                    int count=0;
                                    while(r.next()){
                                        count++;
                                        System.out.println(count+". CompanyID:"+r.getString("CompanyID")+", Status:"+r.getString("Status"));
                                    }
                                }
                                System.out.println();
                            }else if(newchoice1==3) {
                                break;
                            }
                            else if(newchoice1==4){
                                return new Status(false,false);
                            }else{
                                System.out.println("Invalid Request!Please try again");
                                System.out.println();
                            }
                        }
                    }else if(newchoice==3){
                        System.out.println("Placement Statistics");
                        String query="SELECT * FROM GraduateGenie.Placement WHERE CollegeID=\""+CollegeID+"\" ORDER BY Year DESC";
                        ResultSet r= statement.executeQuery(query);
                        while(r.next()){
                            System.out.println("Year:"+r.getInt("Year")+",Total Offers:"+r.getInt("TotalOffers")+",Students registered:"+
                                    r.getInt("StudentsRegistered")+",Placement%:"+r.getFloat("PlacementPercentage")+",CompaniesVisited:"+
                                    r.getInt("CompaniesVisited")+",AveragePackage:"+r.getFloat("AveragePackage"));
                        }
                        System.out.println();
                    }else if(newchoice==4){
                        System.out.println("Edit placement statistics");
                        System.out.println("Please enter the necessary code corresponding to your query:");
                        System.out.println("1:Add/Edit placement statistics");
                        System.out.println("2:Delete placement statistics");
                        int option=input.nextInt();
                        if(option==1){
                            System.out.println("Enter year:");
                            int year=input.nextInt();
                            System.out.println("Enter total offers:");
                            int tfo=input.nextInt();
                            System.out.println("Enter students registered:");
                            int sr=input.nextInt();
                            System.out.println("Enter Placement%:");
                            int pp=input.nextInt();
                            System.out.println("Enter companies visited::");
                            int cv=input.nextInt();
                            System.out.println("Enter average package:");
                            int ap=input.nextInt();
                            String query="INSERT INTO GraduateGenie.Placement (CollegeID, Year, TotalOffers, StudentsRegistered, PlacementPercentage, CompaniesVisited, AveragePackage) "+
                                    "VALUES(\""+CollegeID+"\","+year+","+tfo+","+sr+","+pp+","+cv+","+ap+") " +
                                    "ON DUPLICATE KEY UPDATE TotalOffers="+tfo+",StudentsRegistered="+sr+",PlacementPercentage="+pp+",CompaniesVisited="+cv+",AveragePackage="+ap;
                            statement.executeUpdate(query);
                            System.out.println("Placement statistics added/updated.");
                        }else if(option==2){
                            System.out.println("Enter year:");
                            int year=input.nextInt();
                            statement.executeUpdate("DELETE FROM GraduateGenie.Placement WHERE Year="+year+" AND CollegeID=\""+CollegeID+"\"");
                            System.out.println("Placement statistics updated.");
                        }
                        System.out.println();
                    }else if(newchoice==5){
                        break;
                    }else if(newchoice==6){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                        System.out.println();
                    }
                }
            }else if(choice==5){
                return new Status(false,true);
            }else if(choice==6){
                return new Status(false,false);
            }else{
                System.out.println("Invalid Request!Please try again");
            }
        }
        College.close();
        return null;
    }
    public static Status CompanyQueries(String CompanyID) throws SQLException, ParseException {
        ResultSet Company= statement.executeQuery("SELECT * FROM GraduateGenie.Company WHERE CompanyID=\""+CompanyID+"\"");
        Company.next();
        System.out.println(Company.getString("Name"));
        while(true) {
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:View company details");
            System.out.println("2:Edit company details");
            System.out.println("3:View and connect with colleges");
            System.out.println("4:View Student Recommendations");
            System.out.println("5:View/Edit student offers");
            System.out.println("6:Back");
            System.out.println("7:Exit");
            int choice = input.nextInt();
            if(choice==1){
                CompanyDetails(CompanyID,1);
            }else if(choice==2){
                boolean cont=CompanyDetails(CompanyID,2);
                if(!cont)
                    return new Status(false,false);
            }
            else if(choice==3){
                while(true){
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View colleges");
                    System.out.println("2:Connect with colleges");
                    System.out.println("3:View placement statistics");
                    System.out.println("4:Back");
                    System.out.println("5:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("List of connected colleges:");
                        String query1="SELECT GraduateGenie.Visits.CollegeID,GraduateGenie.College.Name " +
                                "FROM GraduateGenie.College " +
                                "INNER JOIN GraduateGenie.Visits ON GraduateGenie.College.CollegeID=GraduateGenie.Visits.CollegeID " +
                                "WHERE CompanyID=\""+CompanyID+"\"";
                        ResultSet r= statement.executeQuery(query1);
                        int count=0;
                        while (r.next()){
                            count++;
                            System.out.println(count+". "+r.getString("CollegeID")+" "+r.getString("Name"));
                        }
                        System.out.println();
                        System.out.println("List of unconnected colleges:");
                        String query2="SELECT GraduateGenie.College.CollegeID,GraduateGenie.College.Name FROM GraduateGenie.College "+
                        "LEFT JOIN GraduateGenie.Visits ON GraduateGenie.College.CollegeID=GraduateGenie.Visits.CollegeID AND CompanyID=\""+CompanyID+"\" "+
                        "WHERE Visits.CollegeID IS NULL";
                        r= statement.executeQuery(query2);
                        count=0;
                        while (r.next()){
                            count++;
                            System.out.println(count+". "+r.getString("CollegeID")+" "+r.getString("Name"));
                        }
                        System.out.println();
                    }else if(newchoice==2){
                        System.out.println("Connect with colleges");
                        System.out.println("Enter CollegeId you wish to connect with:");
                        String college=input.next();
                        String query1="SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+college+"\"";
                        ResultSet r= statement.executeQuery(query1);
                        if(r.next()){
                            String query2="INSERT IGNORE INTO GraduateGenie.Visits (CompanyID, CollegeID) " +
                                    "VALUES(\""+CompanyID+"\",\""+college+"\")";
                            statement.executeUpdate(query2);
                            System.out.println("College Connected.");
                        }else{
                            System.out.println("Invalid CollegeID.");
                        }
                    }else if(newchoice==3){
                        System.out.println("Placement Statistics");
                        System.out.println("Enter College ID:");
                        String college=input.next();
                        String query="SELECT * FROM GraduateGenie.Placement WHERE CollegeID=\""+college+"\" "+
                                "ORDER BY YEAR DESC";
                        ResultSet r= statement.executeQuery(query);
                        if(r.next()){
                            r.previous();
                            while(r.next()){
                                System.out.println("Year:"+r.getInt("Year")+",Total Offers:"+r.getInt("TotalOffers")+",Students registered:"+
                                        r.getInt("StudentsRegistered")+",Placement%:"+r.getFloat("PlacementPercentage")+",CompaniesVisited:"+
                                        r.getInt("CompaniesVisited")+",AveragePackage:"+r.getFloat("AveragePackage"));
                            }
                        }else{
                            System.out.println("Placement statistics not available.");
                        }
                    }else if(newchoice==4){
                        break;
                    }else if(newchoice==5){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                    }
                    System.out.println();
                }
            }else if(choice==4){
                System.out.println("Student Recommendations: ");
                System.out.println("Enter CollegeID:");
                String college=input.next();
                ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Visits WHERE CollegeID=\""+college+"\" AND CompanyID=\""+CompanyID+"\"");
                if(r.next()){
                    while(true){
                        System.out.println("Please enter the necessary code corresponding to your query:");
                        System.out.println("1:View student recommendations(on basis of grades)");
                        System.out.println("2:View student data");
                        System.out.println("3:Back");
                        System.out.println("4:Exit");
                        int newchoice=input.nextInt();
                        if(newchoice==1){
                            String query="SELECT GraduateGenie.Grade.StudentID,CONCAT(Student.First_Name,\" \",Student.Last_Name)AS Full_Name,AVG(Grade) AS CGPA " +
                                    "FROM GraduateGenie.Student INNER JOIN GraduateGenie.Grade " +
                                    "ON GraduateGenie.Grade.StudentID=GraduateGenie.Student.StudentID AND GraduateGenie.Student.CollegeID=\""+college+"\" " +
                                    "GROUP BY GraduateGenie.Grade.StudentID ORDER BY CGPA DESC";
                            r= statement.executeQuery(query);
                            int count=1;
                            System.out.println("Student recommendations(on basis of grades):");
                            while(r.next()&&count!=16){
                                System.out.println(count+". StudentID="+r.getString("StudentID")+", Name="+r.getString("Full_Name")+", CGPA="+r.getString("CGPA"));
                            }
                        }else if(newchoice==2){
                            System.out.println("Enter StudentID:");
                            String SID=input.next();
                            r= statement.executeQuery("SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+college+"\"");
                            if(r.next()){
                                StudentDetails(SID,1);
                            }else
                                System.out.println("Invalid StudentID");
                        }else if(newchoice==3){
                            break;
                        }else if(newchoice==4){
                            return new Status(false,false);
                        }else
                            System.out.println("Invalid request.Please try again");
                        System.out.println();
                    }
                    System.out.println();
                }else{
                    System.out.println("Invalid CollegeID.");
                }
                System.out.println();
            }else if(choice==5){
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:View current student offers");
                    System.out.println("2:Add/Update student offers");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Enter CollegeId:");
                        String college=input.next();
                        String query1="SELECT * FROM GraduateGenie.Visits WHERE CollegeID=\""+college+"\" AND CompanyID=\""+CompanyID+"\"";
                        ResultSet r= statement.executeQuery(query1);
                        if(r.next()){
                            String query2="SELECT Name FROM GraduateGenie.College WHERE CollegeID=\""+college+"\"";
                            r= statement.executeQuery(query2);
                            if(r.next())
                                System.out.println("Student Offers for "+r.getString("Name"));
                            String query3="SELECT GraduateGenie.Student.StudentID,CONCAT(Student.First_Name,\" \",Student.Last_Name) AS FullName,GraduateGenie.Job_Offer.Status " +
                                    "FROM GraduateGenie.Student RIGHT JOIN GraduateGenie.Job_Offer " +
                                    "ON GraduateGenie.Student.StudentID=GraduateGenie.Job_Offer.StudentID " +
                                    "WHERE CollegeID=\""+college+"\" AND CompanyID=\""+CompanyID+"\"ORDER BY STATUS";
                            r= statement.executeQuery(query3);
                            int count=0;
                            while (r.next()){
                                count++;
                                System.out.println(count+". StudentID="+r.getString("StudentID")+", Name="+r.getString("FullName")+",Status="+r.getString("Status"));
                            }
                        }else
                            System.out.println("Invalid CollegeID.");
                    }else if(newchoice==2){
                        System.out.println("Enter CollegeId:");
                        String college=input.next();
                        String query1="SELECT * FROM GraduateGenie.Visits WHERE CollegeID=\""+college+"\" AND CompanyID=\""+CompanyID+"\"";
                        ResultSet r= statement.executeQuery(query1);
                        if(r.next()){
                            System.out.println("Enter StudentID");
                            String SID=input.next();
                            String query2="SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+SID+"\" AND CollegeID=\""+college+"\"";
                            r= statement.executeQuery(query2);
                            if(r.next()){
                                while(true){
                                    System.out.println("Choose status according to corresponding codes:");
                                    System.out.println("1:Hired");
                                    System.out.println("2:Declined");
                                    System.out.println("3:Under Process");
                                    int status=input.nextInt();
                                    if(status==1||status==2||status==3){
                                        String stat=null;
                                        if(status==1)
                                            stat="Hired";
                                        else if(status==2)
                                            stat="Declined";
                                        else
                                            stat="Under Process";
                                        statement.executeUpdate("INSERT INTO GraduateGenie.Job_Offer(CompanyID,StudentID,Status) " +
                                                "VALUES(\""+CompanyID+"\", \""+SID+"\", \""+stat+"\") ON DUPLICATE KEY UPDATE Status=\""+stat+"\"");
                                        System.out.println("Student job offer added/modified");
                                        break;
                                    }else
                                        System.out.println("Invalid Option.Please try again");
                                    System.out.println();
                                }
                            }else
                                System.out.println("Invalid StudentID");
                        }else
                            System.out.println("Invalid CollegeID.");
                        System.out.println();
                    }else if(newchoice==3){
                        break;
                    }else if(newchoice==4){
                        return new Status(false,false);
                    }else{
                        System.out.println("Invalid Request!Please try again");
                    }
                    System.out.println();
                }
            }else if(choice==6){
                return new Status(false,true);
            }else if(choice==7){
                return new Status(false,false);
            }else {
                System.out.println("Invalid Request!Please try again");
            }
            System.out.println();
        }
    }
    public static Status AdministrationQueries(String AdminID) throws SQLException, ParseException, ClassNotFoundException {
        ResultSet Admin= statement.executeQuery("SELECT * FROM GraduateGenie.Administration WHERE AdminID=\""+AdminID+"\"");
        Admin.next();
        System.out.println("Hello "+Admin.getString("First_Name"));
        while(true) {
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:Add/Edit to database");
            System.out.println("2:Delete from database");
            System.out.println("3:Back");
            System.out.println("4:Exit");
            int choice=input.nextInt();
            if(choice==1){
                System.out.println("1:Add/Edit to database");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:Add/Edit College");
                    System.out.println("2:Add/Edit Company");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Please enter the necessary code corresponding to your query:");
                        System.out.println("1:Add college");
                        System.out.println("2:Edit college");
                        int option=input.nextInt();
                        if(option==1){
                            Registration(1,false);
                        }else if(option==2){
                            System.out.println("Enter CollegeID");
                            String college=input.next();
                            ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+college+"\"");
                            if(r.next()){
                                boolean cont=CollegeDetails(college,2);
                                if(!cont)
                                    return new Status(false,false);
                            }else
                                System.out.println("Invalid CollegeID.");
                        }else
                            System.out.println("Invalid input.");
                    }else if(newchoice==2){
                        System.out.println("Please enter the necessary code corresponding to your query:");
                        System.out.println("1:Add company");
                        System.out.println("2:Edit company");
                        int option=input.nextInt();
                        if(option==1){
                            Registration(2,false);
                        }else if(option==2){
                            System.out.println("Enter CompanyID");
                            String company=input.next();
                            ResultSet r= statement.executeQuery("SELECT * FROM GraduateGenie.Company WHERE CompanyID=\""+company+"\"");
                            if(r.next()){
                                boolean cont=CompanyDetails(company,2);
                                if(!cont)
                                    return new Status(false,false);
                            }else
                                System.out.println("Invalid CollegeID.");
                        }else
                            System.out.println("Invalid input.");
                    }else if(newchoice==3){
                        break;
                    }else if(newchoice==4){
                        return new Status(false,false);
                    }else
                        System.out.println("Invalid option.Please try again.");
                    System.out.println();
                }
                System.out.println();
            }else if(choice==2){
                System.out.println("2:Delete from database");
                while(true) {
                    System.out.println("Please enter the necessary code corresponding to your query:");
                    System.out.println("1:Delete College");
                    System.out.println("2:Delete Company");
                    System.out.println("3:Back");
                    System.out.println("4:Exit");
                    int newchoice=input.nextInt();
                    if(newchoice==1){
                        System.out.println("Enter CollegeID");
                        String college=input.next();
                        statement.executeUpdate("DELETE FROM GraduateGenie.College WHERE CollegeID=\""+college+"\"");
                        System.out.println("Database updated");
                    }else if(newchoice==2){
                        System.out.println("Enter CompanyID");
                        String company=input.next();
                        statement.executeUpdate("DELETE FROM GraduateGenie.Company WHERE CompanyID=\""+company+"\"");
                        System.out.println("Database updated");
                    }else if(newchoice==3){
                        break;
                    }else if(newchoice==4){
                        return new Status(false,false);
                    }else
                        System.out.println("Invalid option.Please try again.");
                    System.out.println();
                }
                System.out.println();
            }else if(choice==3){
                break;
            }else if(choice==4){
                return new Status(false,false);
            }else
                System.out.println("Invalid option.Please try again.");
            System.out.println();
        }
        System.out.println();
        return null;
    }
    public static Status Registration(int query,boolean checkin) throws SQLException, ParseException, ClassNotFoundException {
        while(true){
            System.out.println("Please enter the necessary code corresponding to your query:");
            System.out.println("1:Registeration");
            System.out.println("2:Back");
            System.out.println("3:Exit");
            int choice=input.nextInt();
            if(choice==1){
                if(query==1){
                    System.out.println("College Registration");
                    System.out.println("Enter desired CollegeID(Maximum 10 characters long)");
                    String ID=input.next();
                    ResultSet rs=statement.executeQuery("SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+ID+"\"");
                    if(rs.next()||ID.length()>10){
                        System.out.println("CollegeID not available.Please try again.");
                    }else{
                        System.out.println("Enter college name:");
                        String name=input.next();
                        System.out.println("Enter address:");
                        System.out.println("Enter building/street/area:");
                        String bsa=input.nextLine();
                        System.out.println("Enter city:");
                        String city=input.nextLine();
                        System.out.println("Enter state:");
                        String state=input.nextLine();
                        System.out.println("Enter country:");
                        String country=input.nextLine();
                        System.out.println("Enter college email:");
                        String email=input.nextLine();
                        System.out.println("Enter director name:");
                        String director=input.nextLine();
                        while(true){
                            System.out.println("Enter password(8-20 characters long):");
                            String pwd=input.nextLine();
                            if(pwd.length()<8||pwd.length()>20)
                                System.out.println("Invalid password.Please try again.");
                            else{
                                String subquery="INSERT INTO GraduateGenie.College (`CollegeID`,`Name`,`Building,Street,Area`,`City`,`State`,`Country`,`Email`,`Director`,`Password`) " +
                                        "VALUES (\""+ID+"\",\""+name+"\",\""+bsa+"\",\""+city+"\",\""+state+"\",\""+country+"\",\""+email+"\",\""+director+"\",\""+pwd+"\")";
                                statement.executeUpdate(subquery);
                                System.out.println("College Registration Complete");
                                System.out.println();
                                if(checkin) {
                                    user="CollegeUser";
                                    password="GGCollege";
                                    UserChange();
                                    return CollegeQueries(ID);
                                }
                                else
                                    return new Status(true,true);
                            }
                        }
                    }
                }else if(query==2) {
                    System.out.println("Company Registration");
                    System.out.println("Enter desired CompanyID(Maximum 10 characters long)");
                    String ID = input.next();
                    ResultSet rs = statement.executeQuery("SELECT * FROM GraduateGenie.Company WHERE CompanyID=\"" + ID + "\"");
                    if (rs.next() || ID.length() > 10) {
                        System.out.println("CompanyID not available.Please try again.");
                    } else {
                        System.out.println("Enter company name:");
                        String name = input.nextLine();
                        System.out.println("Enter company type:");
                        String type = input.nextLine();
                        System.out.println("Enter description:");
                        String desc = input.nextLine();
                        System.out.println("Enter address:");
                        System.out.println("Enter building/street/area:");
                        String bsa = input.nextLine();
                        System.out.println("Enter city:");
                        String city = input.nextLine();
                        System.out.println("Enter state:");
                        String state = input.nextLine();
                        System.out.println("Enter country:");
                        String country = input.nextLine();
                        System.out.println("Enter company email:");
                        String email = input.nextLine();
                        while (true) {
                            System.out.println("Enter password(8-20 characters long):");
                            String pwd = input.nextLine();
                            if (pwd.length() < 8 || pwd.length() > 20)
                                System.out.println("Invalid password.Please try again.");
                            else {
                                String subquery = "INSERT INTO GraduateGenie.Company (`CompanyID`,`Name`,`Type`,`Description`,`Building,Street,Area`,`City`,`State`,`Country`,`Email`,`Password`) " +
                                        "VALUES (\"" + ID + "\",\"" + name + "\",\"" + type + "\",\"" + desc + "\",\"" + bsa + "\",\"" + city + "\",\"" + state + "\",\"" + country + "\",\"" + email + "\",\"" + pwd + "\")";
                                statement.executeUpdate(subquery);
                                System.out.println("Company Registration Complete");
                                System.out.println();
                                if(checkin) {
                                    user="CompanyUser";
                                    password="GGCompany";
                                    UserChange();
                                    return CompanyQueries(ID);
                                }
                                else
                                    return new Status(true,true);
                            }
                        }
                    }
                }
            }else if(choice==2){
                return new Status(false,true);
            }else if(choice==3){
                return new Status(false,false);
            }else{
                System.out.println("Invalid Request!Please try again");
            }
        }
    }
    public static boolean StudentDetails(String StudentID,int choice) throws SQLException, ParseException {
        //1 is for view, 2 is for Edit
        String studentquery="SELECT * FROM GraduateGenie.Student WHERE StudentID=\""+StudentID+"\"";
        ResultSet Student= statement.executeQuery(studentquery);
        Student.next();
        if(choice==1){
            System.out.println("Student Details:");
            System.out.println("Student Name: "+Student.getString("First_Name")+" "+Student.getString("Last_Name"));
            System.out.println("Student ID: "+Student.getString("StudentID"));
            System.out.println("College ID: "+Student.getString("CollegeID"));
            System.out.println("Address: "+Student.getString("HouseNumber")+","+Student.getString("Building,Street,Area")+","
                    +Student.getString("City")+"," +Student.getString("State")+","+Student.getString("Country"));
            System.out.println("D.O.B: "+Student.getString("DOB"));
            System.out.println("Gender: "+Student.getString("Gender"));
            System.out.println("Branch: "+Student.getString("Branch"));
            System.out.println("Semester: "+Student.getString("Semester"));
            System.out.println("Email ID: "+Student.getString("Email"));
        }else{
            boolean check2=true;
            while(check2){
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Edit Student Name");
                System.out.println("2:Edit Address");
                System.out.println("3:Edit D.O.B");
                System.out.println("4:Edit Gender");
                System.out.println("5:Back");
                System.out.println("6:Exit");
                int newchoice=input.nextInt();
                if(newchoice==1){
                    System.out.println("You have chosen to edit Student Name");
                    System.out.println("Enter new first name:");
                    String fname=input.next();
                    System.out.println("Enter last name:");
                    String lname=input.next();
                    Student.updateString("First_Name",fname);Student.updateString("Last_Name",lname);
                    System.out.println("Name updated.");
                    System.out.println();
                    Student.updateRow();
                }else if(newchoice==2){
                    System.out.println("You have chosen to edit Address");
                    System.out.println("Enter house number:");
                    String hno=input.nextLine();
                    System.out.println("Enter building/street/area:");
                    String bsa=input.nextLine();
                    System.out.println("Enter city:");
                    String city=input.nextLine();
                    System.out.println("Enter state:");
                    String state=input.nextLine();
                    System.out.println("Enter country:");
                    String country=input.nextLine();
                    Student.updateString("HouseNumber",hno);Student.updateString("Building,Street,Area",bsa);
                    Student.updateString("City",city);Student.updateString("State",state);Student.updateString("Country",country);
                    System.out.println("Address updated.");
                    System.out.println();
                    Student.updateRow();
                }else if(newchoice==3){
                    System.out.println("You have chosen to edit D.O.B");
                    System.out.println("Enter D.O.B(YYYY-MM-DD):");
                    String date1=input.next();
                    Date date=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                    Student.updateDate("DOB",new java.sql.Date(date.getTime()));
                    System.out.println("D.O.B. updated.");
                    System.out.println();
                    Student.updateRow();
                }else if(newchoice==4){
                    System.out.println("You have chosen to edit Gender");
                    System.out.println("Enter Gender(M/F/O):");
                    String gender=input.next();
                    if(gender.equals("M")||gender.equals("F")||gender.equals("O")){
                        Student.updateString("Gender",gender);
                        System.out.println("Gender updated.");
                        System.out.println();
                        Student.updateRow();
                    }else {
                        System.out.println("Invalid Input");
                        System.out.println();
                    }
                }else if(newchoice==5){
                    break;
                }else if(newchoice==6){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                    System.out.println();
                }
            }
        }
        return true;
    }
    public static boolean StudentGrades(String StudentID,int choice) throws SQLException {
        if(choice==1){
            while (true){
                System.out.println("You have chosen to view grades");
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:View grades for a particular semester:");
                System.out.println("2:View overall grades");
                System.out.println("3:Back");
                System.out.println("4:Exit");
                int newchoice=input.nextInt();
                if(newchoice==1){
                    System.out.println("Enter semester:");
                    int sem=input.nextInt();
                    String query1="SELECT GraduateGenie.Grade.StudentID,GraduateGenie.Grade.CourseID,GraduateGenie.Course.Name,GraduateGenie.Grade.Semester,GraduateGenie.Grade.Grade " +
                            " FROM GraduateGenie.GRADE INNER JOIN Course ON GraduateGenie.Course.CourseID=GraduateGenie.Grade.CourseID " +
                            "WHERE GraduateGenie.Grade.StudentID=\""+StudentID+"\" AND GraduateGenie.Grade.Semester="+sem;
                    ResultSet r=statement.executeQuery(query1);
                    System.out.println("Grades for semester "+sem+":");
                    if(r.next()){
                        do{
                            System.out.println(r.getString("CourseID")+" "+r.getString("Name")+" "+r.getFloat("Grade"));
                        }while(r.next());
                    }else
                        System.out.println("Grades not available");
                    String query2="SELECT AVG(GraduateGenie.Grade.Grade) 'SGPA' FROM GraduateGenie.Grade " +
                            "WHERE GraduateGenie.Grade.StudentID=\""+StudentID+"\" AND Semester="+sem;
                    r=statement.executeQuery(query2);
                    if(r.next())
                        System.out.println("SGPA = "+r.getFloat("SGPA"));
                }else if(newchoice==2){
                    String query1="SELECT GraduateGenie.Grade.StudentID,GraduateGenie.Grade.CourseID,GraduateGenie.Course.Name,GraduateGenie.Grade.Semester,GraduateGenie.Grade.Grade " +
                            " FROM GraduateGenie.GRADE INNER JOIN Course ON GraduateGenie.Course.CourseID=GraduateGenie.Grade.CourseID " +
                            "WHERE GraduateGenie.Grade.StudentID=\""+StudentID+"\" "+
                            "ORDER BY GraduateGenie.Grade.Semester ASC;";
                    ResultSet r=statement.executeQuery(query1);
                    System.out.println("Grades:");
                    if(r.next()){
                        do {
                            System.out.println(r.getString("CourseID") + " " + r.getString("Name") + ",Semester: " + r.getInt("Semester") + ",Grade: " + r.getFloat("Grade"));
                        }while(r.next());
                    }
                    String query2="SELECT AVG(GraduateGenie.Grade.Grade) 'CGPA' FROM GraduateGenie.Grade " +
                            "WHERE GraduateGenie.Grade.StudentID=\""+StudentID+"\"";
                    r=statement.executeQuery(query2);
                    if(r.next());
                        System.out.println("CGPA = "+r.getFloat("CGPA"));
                }else if(newchoice==3){
                    break;
                }else if(newchoice==4){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                }
                System.out.println();
            }
        }
        return true;
    }
    public static boolean StudentCourses(String StudentID,int choice) throws SQLException {
        if(choice==1){
            System.out.println("Courses for the current semester are: ");
            String query1="SELECT GraduateGenie.Studies.CourseID,GraduateGenie.Course.Name FROM Studies INNER JOIN Course " +
                    "ON GraduateGenie.Course.CourseID=GraduateGenie.Studies.CourseID WHERE GraduateGenie.Studies.StudentID=\""+StudentID+"\"";
            ResultSet r= statement.executeQuery(query1);
            int count=0;
            while(r.next()) {
                count++;
                System.out.println(count + ". " + r.getString("CourseID") + " " + r.getString("Name"));
            }
        }else{
            ResultSet Student=statement.executeQuery("SELECT CollegeID FROM GraduateGenie.Student WHERE StudentID=\""+StudentID+"\"");
            Student.next();
            String college=Student.getString("CollegeID");
            while(true){
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Add a course");
                System.out.println("2:Drop a course");
                System.out.println("3:Back");
                System.out.println("4:Exit");
                int newchoice=input.nextInt();
                if(newchoice==1){
                    System.out.println("Add a course");
                    String query1="SELECT COUNT(StudentID) AS COUNT,StudentID FROM GraduateGenie.Studies WHERE StudentID=\""+StudentID+"\"";
                    ResultSet r= statement.executeQuery(query1);
                    boolean valid=true;
                    if(r.next()){
                        int subjects=r.getInt("COUNT");
                        if(subjects>=5) {
                            System.out.println("Cannot register for more than 5 subjects in a semester");
                            valid=false;
                        }
                    }
                    if(valid){
                        System.out.println("Enter CourseID for registration:");
                        String course=input.next();
                        String query2="SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+course+"\" AND CollegeID=\""+college+"\"";
                        r= statement.executeQuery(query2);
                        if(r.next()){
                            statement.executeUpdate("INSERT IGNORE INTO GraduateGenie.Studies (CourseID, CollegeID, StudentID) " +
                                    "VALUES(\""+course+"\",\""+college+"\",\""+StudentID+"\")");
                            System.out.println("Course added successfully.");
                        }else
                            System.out.println("Invalid CourseID.");
                    }
                    break;
                }else if(newchoice==2){
                    System.out.println("Drop a course");
                    System.out.println("Enter CourseID for registration:");
                    String course=input.next();
                    String query1="SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+course+"\" AND CollegeID=\""+college+"\"";
                    ResultSet r= statement.executeQuery(query1);
                    if(r.next()){
                        statement.executeUpdate("DELETE FROM GraduateGenie.Studies WHERE CourseID=\""+course+"\" AND CollegeID=\""+college+"\" AND StudentID=\""+StudentID+"\"");
                        System.out.println("Course dropped successfully.");
                    }else
                        System.out.println("Invalid CourseID.");
                    break;
                }else if(newchoice==3){
                    break;
                }else if(newchoice==4){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                }
                System.out.println();
            }
        }
        return true;
    }
    public static boolean FacultyDetails(String FacultyID,int choice) throws SQLException, ParseException {
        //1 is for view, 2 is for Edit
        String facultyquery="SELECT * FROM GraduateGenie.Faculty WHERE FacultyID=\""+FacultyID+"\"";
        ResultSet Faculty= statement.executeQuery(facultyquery);
        Faculty.next();
        if(choice==1){
            System.out.println("Faculty Details:");
            System.out.println("FacultyName: "+Faculty.getString("First_Name")+" "+Faculty.getString("Last_Name"));
            System.out.println("FacultyID: "+Faculty.getString("FacultyID"));
            System.out.println("College ID: "+Faculty.getString("CollegeID"));
            System.out.println("Address: "+Faculty.getString("HouseNumber")+","+Faculty.getString("Building,Street,Area")+","
                    +Faculty.getString("City")+"," +Faculty.getString("State")+","+Faculty.getString("Country"));
            System.out.println("D.O.B: "+Faculty.getString("DOB"));
            System.out.println("Gender: "+Faculty.getString("Gender"));
            System.out.println("Specialization: "+Faculty.getString("Specialization"));
            System.out.println("Position: "+Faculty.getString("Position"));
            System.out.println("Experience: "+Faculty.getString("Experience")+" years");
            System.out.println("Email ID: "+Faculty.getString("Email"));
            System.out.println();
        }else if(choice==2){
            while(true){
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Edit Name");
                System.out.println("2:Edit Address");
                System.out.println("3:Edit D.O.B");
                System.out.println("4:Edit Gender");
                System.out.println("5:Edit Specialization");
                System.out.println("6:Edit Experience");
                System.out.println("7:Back");
                System.out.println("8:Exit");
                int newchoice=input.nextInt();
                if(newchoice==1){
                    System.out.println("You have chosen to edit Name");
                    System.out.println("Enter new first name:");
                    String fname=input.next();
                    System.out.println("Enter last name:");
                    String lname=input.next();
                    Faculty.updateString("First_Name",fname);Faculty.updateString("Last_Name",lname);
                    System.out.println("Name updated.");
                    System.out.println();
                    Faculty.updateRow();
                }else if(newchoice==2){
                    System.out.println("You have chosen to edit Address");
                    System.out.println("Enter house number:");
                    String hno=input.nextLine();
                    System.out.println("Enter building/street/area:");
                    String bsa=input.nextLine();
                    System.out.println("Enter city:");
                    String city=input.nextLine();
                    System.out.println("Enter state:");
                    String state=input.nextLine();
                    System.out.println("Enter country:");
                    String country=input.nextLine();
                    Faculty.updateString("HouseNumber",hno);Faculty.updateString("Building,Street,Area",bsa);
                    Faculty.updateString("City",city);Faculty.updateString("State",state);Faculty.updateString("Country",country);
                    System.out.println("Address updated.");
                    System.out.println();
                    Faculty.updateRow();
                }else if(newchoice==3){
                    System.out.println("You have chosen to edit D.O.B");
                    System.out.println("Enter D.O.B(YYYY-MM-DD):");
                    String date1=input.next();
                    Date date=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                    Faculty.updateDate("DOB",new java.sql.Date(date.getTime()));
                    System.out.println("D.O.B. updated.");
                    System.out.println();
                    Faculty.updateRow();
                }else if(newchoice==4){
                    System.out.println("You have chosen to edit Gender");
                    System.out.println("Enter Gender(M/F/O):");
                    String gender=input.next();
                    if(gender.equals("M")||gender.equals("F")||gender.equals("O")){
                        Faculty.updateString("Gender",gender);
                        System.out.println("Gender updated.");
                        System.out.println();
                        Faculty.updateRow();
                    }else {
                        System.out.println("Invalid Input");
                        System.out.println();
                    }
                }else if(newchoice==5){
                    System.out.println("You have chosen to edit Specialization");
                    System.out.println("Enter Specialization:");
                    String spec=input.nextLine();
                    Faculty.updateString("Specialization",spec);
                    System.out.println("Specialization updated.");
                    System.out.println();
                    Faculty.updateRow();
                }else if(newchoice==6){
                    System.out.println("You have chosen to edit Experience");
                    System.out.println("Enter Experience(in complete years(i.e. integer value)):");
                    int exp=input.nextInt();
                    Faculty.updateInt("Experience",exp);
                    System.out.println("Experience updated.");
                    System.out.println();
                    Faculty.updateRow();
                }else if(newchoice==7){
                    break;
                }else if(newchoice==8){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                    System.out.println();
                }
            }
        }
        return true;
    }
    public static boolean CourseDetails(String CourseID,String CollegeID,int choice) throws SQLException {
        boolean check3=true;
        String query2="SELECT * FROM GraduateGenie.Course WHERE CourseID=\""+CourseID+"\" AND CollegeID=\""+CollegeID+"\"";
        ResultSet Course=statement.executeQuery(query2);
        Course.next();
        if(choice==1){
            System.out.println("CourseId: "+Course.getString("CourseID"));
            System.out.println("Name: "+Course.getString("Name"));
            System.out.println("Type: "+Course.getString("Type"));
            System.out.println("Description: "+Course.getString("Description"));
            System.out.println("Credits: "+Course.getInt("Credits"));
            System.out.println("Semester: "+Course.getString("Semester"));
        }else if(choice==2){
            while(check3) {
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Edit Course Name");
                System.out.println("2:Edit Course Description");
                System.out.println("3:Edit Course Type");
                System.out.println("4:Edit Course Credits");
                System.out.println("5:Edit Course Semester");
                System.out.println("6:Back");
                System.out.println("7:Exit");
                int newchoice1 = input.nextInt();
                if (newchoice1 == 1) {
                    System.out.println("You have chosen to edit Course Name");
                    System.out.println("Enter Course Name:");
                    String name=input.nextLine();
                    Course.updateString("Name",name);
                    System.out.println("Course Name updated.");
                    Course.updateRow();
                } else if (newchoice1 == 2) {
                    System.out.println("You have chosen to edit Course Description");
                    System.out.println("Enter Course Description:");
                    String desc=input.nextLine();
                    Course.updateString("Description",desc);
                    System.out.println("Course Description updated.");
                    Course.updateRow();
                } else if (newchoice1 == 3) {
                    System.out.println("You have chosen to edit Course Type");
                    System.out.println("Enter Course Type:");
                    String type=input.nextLine();
                    Course.updateString("Type",type);
                    System.out.println("Course Type updated.");
                    Course.updateRow();
                } else if (newchoice1 == 4) {
                    System.out.println("You have chosen to edit Course Credits");
                    System.out.println("Enter Course Credits(integer value):");
                    int cred=input.nextInt();
                    Course.updateInt("Credits",cred);
                    System.out.println("Course Credits updated.");
                    Course.updateRow();
                } else if (newchoice1 == 5) {
                    System.out.println("You have chosen to edit Course Semester");
                    System.out.println("Enter Course Semester(Summer/Winter/(Summer/Winter):");
                    String sem=input.nextLine();
                    Course.updateString("Semester",sem);
                    System.out.println("Course Semester updated.");
                    Course.updateRow();
                } else if (newchoice1 == 6) {
                    break;
                } else if (newchoice1 == 7) {
                    return false;
                } else {
                    System.out.println("Invalid Request!Please try again");
                }
                System.out.println();
            }
        }
        return true;
    }
    public static boolean CollegeDetails(String CollegeID,int choice) throws SQLException {
        String collegequery="SELECT * FROM GraduateGenie.College WHERE CollegeID=\""+CollegeID+"\"";
        ResultSet College= statement.executeQuery(collegequery);
        College.next();
        if(choice==1){
            College= statement.executeQuery(collegequery);
            College.next();
            System.out.println("College Details:");
            System.out.println("CollegeName: "+College.getString("Name"));
            System.out.println("Address: "+College.getString("Building,Street,Area")+","
                    +College.getString("City")+"," +College.getString("State")+","+College.getString("Country"));
            System.out.println("Director: "+College.getString("Director"));
            System.out.println("Email ID: "+College.getString("Email"));
            System.out.println();
        }else{
            while(true){
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Edit College Name");
                System.out.println("2:Edit College Address");
                System.out.println("3:Edit College Director");
                System.out.println("4:Edit College Email ID");
                System.out.println("5:Back");
                System.out.println("6:Exit");
                int newchoice = input.nextInt();
                if (newchoice == 1) {
                    System.out.println("You have chosen to edit College Name");
                    System.out.println("Enter College Name:");
                    String name=input.nextLine();
                    College.updateString("Name",name);
                    System.out.println("College Name updated.");
                    College.updateRow();
                }else if(newchoice==2){
                    System.out.println("You have chosen to edit College Address");
                    System.out.println("Enter building/street/area:");
                    String bsa=input.nextLine();
                    System.out.println("Enter city:");
                    String city=input.nextLine();
                    System.out.println("Enter state:");
                    String state=input.nextLine();
                    System.out.println("Enter country:");
                    String country=input.nextLine();
                    College.updateString("Building,Street,Area",bsa);College.updateString("City",city);
                    College.updateString("State",state);College.updateString("Country",country);
                    System.out.println("Address updated.");
                    College.updateRow();
                }else if(newchoice==3){
                    System.out.println("You have chosen to edit College Director");
                    System.out.println("Enter College Director Name:");
                    String name=input.nextLine();
                    College.updateString("Director",name);
                    System.out.println("College Director updated.");
                    College.updateRow();
                }else if(newchoice==4){
                    System.out.println("You have chosen to edit College Email");
                    System.out.println("Enter College Email:");
                    String mail=input.next();
                    College.updateString("Email",mail);
                    System.out.println("College Mail updated.");
                    College.updateRow();
                }else if(newchoice==5){
                    break;
                }else if(newchoice==6){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                }
                System.out.println();
            }
        }
        return true;
    }
    public static boolean CompanyDetails(String CompanyID,int choice) throws SQLException, ParseException {
        //1 is for view, 2 is for Edit
        String companyquery="SELECT * FROM GraduateGenie.Company WHERE CompanyID=\""+CompanyID+"\"";
        ResultSet Company= statement.executeQuery(companyquery);
        Company.next();
        if(choice==1){
            System.out.println("Company Details:");
            System.out.println("CompanyID: "+Company.getString("CompanyID"));
            System.out.println("Company Name: "+Company.getString("Name"));
            System.out.println("Address: "+Company.getString("Building,Street,Area")+","
                    +Company.getString("City")+"," +Company.getString("State")+","+Company.getString("Country"));
            System.out.println("Type: "+Company.getString("Type"));
            System.out.println("Description: "+Company.getString("Description"));
            System.out.println("Email ID: "+Company.getString("Email"));
            System.out.println();
        }else{
            boolean check2=true;
            while(check2){
                System.out.println("Please enter the necessary code corresponding to your query:");
                System.out.println("1:Edit company name");
                System.out.println("2:Edit address");
                System.out.println("3:Edit type");
                System.out.println("4:Edit description");
                System.out.println("5:Edit email");
                System.out.println("6:Back");
                System.out.println("7:Exit");
                int newchoice=input.nextInt();
                if(newchoice==1){
                    System.out.println("You have chosen to edit company name");
                    System.out.println("Enter new name:");
                    String name=input.nextLine();
                    Company.updateString("Name",name);
                    System.out.println("Name updated.");
                    Company.updateRow();
                }else if(newchoice==2){
                    System.out.println("You have chosen to edit address");
                    System.out.println("Enter building/street/area:");
                    String bsa=input.nextLine();
                    System.out.println("Enter city:");
                    String city=input.nextLine();
                    System.out.println("Enter state:");
                    String state=input.nextLine();
                    System.out.println("Enter country:");
                    String country=input.nextLine();
                    Company.updateString("Building,Street,Area",bsa);Company.updateString("City",city);
                    Company.updateString("State",state);Company.updateString("Country",country);
                    System.out.println("Address updated.");
                    Company.updateRow();
                }else if(newchoice==3){
                    System.out.println("You have chosen to edit type");
                    System.out.println("Enter new type:");
                    String type=input.nextLine();
                    Company.updateString("Type",type);
                    System.out.println("Type updated.");
                    Company.updateRow();
                }else if(newchoice==4){
                    System.out.println("You have chosen to edit description");
                    System.out.println("Enter description:");
                    String desc=input.nextLine();
                    Company.updateString("Description",desc);
                    System.out.println("Description updated.");
                    Company.updateRow();
                }else if(newchoice==5){
                    System.out.println("You have chosen to edit email");
                    System.out.println("Enter email:");
                    String mail=input.nextLine();
                    Company.updateString("Email",mail);
                    System.out.println("Email updated.");
                    Company.updateRow();
                }else if(newchoice==6){
                    break;
                }else if(newchoice==7){
                    return false;
                }else{
                    System.out.println("Invalid Request!Please try again");
                }
                System.out.println();
            }
        }
        return true;
    }
    public static void UserChange() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection= DriverManager.getConnection(url,user,password);
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.executeUpdate("USE GraduateGenie");
    }
}
class Status{
    boolean back;
    boolean exit;
    Status(boolean back,boolean exit){
        this.back=back;
        this.exit=exit;
    }
}
class reader {
    BufferedReader br;
    StringTokenizer st;
    public reader() {
        br = new BufferedReader(new
                InputStreamReader(System.in));
    }
    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
    int nextInt() {
        return Integer.parseInt(next());
    }
    long nextLong() {
        return Long.parseLong(next());
    }
    double nextDouble() {
        return Double.parseDouble(next());
    }
    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
