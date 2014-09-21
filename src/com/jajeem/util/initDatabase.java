package com.jajeem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class initDatabase {

	public static void initialize() {

		new Config();
		Connection con;
		new Config();
		try {
			String s = Config.getParam("server");
			if (s.equals("1")) {

				con = BaseDAO.getConnection();

				String query = "CREATE TABLE IF NOT EXISTS QuizResponse ("
						+ "id int DEFAULT NULL AUTO_INCREMENT, "
						+ "runid UUID,"
						+ "iid UUID,"
						+ "answer varchar(555), "
						+ "bool1 boolean, "
						+ "bool2 boolean, "
						+ "bool3 boolean, "
						+ "bool4 boolean, "
						+ "bool5 boolean, "
						+ "answerValid boolean, "
						+ "studentId int  , "
						+ "QuizQuestionid UUID  , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS QuizQuestion ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "quizId UUID  , "
						+ "title varchar(5000), "
						+ "type tinyint  , "
						+ "point int, "
						+ "imagepath varchar(1000), "
						+ "url varchar(555), "
						+ "answer1 varchar(555), "
						+ "answer2 varchar(555), "
						+ "answer3 varchar(555), "
						+ "answer4 varchar(555), "
						+ "answer5 varchar(555), "
						+ "bool1 boolean, "
						+ "bool2 boolean, "
						+ "bool3 boolean, "
						+ "bool4 boolean, "
						+ "bool5 boolean, "
						+ "instructorid int  , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS QuizRun ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "quizid UUID  , "
						+ "courseid int , "
						+ "Instructorid int  , "
						+ "start bigint, "
						+ "end bigint, "
						+ "score int, "
						+ "Studentid int  , "
						+ "sessionid int  , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS Quiz ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "instructorid int  , "
						+ "courseid int  , "
						+ "type tinyint, "
						+ "category varchar(255), "
						+ "description varchar(1000), "
						+ "pointing int, "
						+ "points int, "
						+ "time int, "
						+ "shuffle tinyint, "
						+ "title varchar(255), "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS SurveyResponse ("
						+ "id int DEFAULT NULL  AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "runid UUID,"
						+ "answer varchar(555), "
						+ "bool1 boolean, "
						+ "bool2 boolean, "
						+ "bool3 boolean, "
						+ "bool4 boolean, "
						+ "bool5 boolean, "
						+ "studentId int  , "
						+ "surveyQuestionid UUID  , "
						+ "ResponseQuestionid UUID  , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS SurveyQuestion ("
						+ "id int DEFAULT 0   AUTO_INCREMENT,"
						+ "iid UUID,"
						+ "surveyid UUID  , "
						+ "responseId UUID  , "
						+ "title varchar(5000), "
						+ "type tinyint  , "
						+ "imagepath varchar(1000), "
						+ "url varchar(555), "
						+ "answer1 varchar(555), "
						+ "answer2 varchar(555), "
						+ "answer3 varchar(555), "
						+ "answer4 varchar(555), "
						+ "answer5 varchar(555), "
						+ "instructorid int  , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS SURVEYRUN ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "surveyId UUID  , "
						+ "courseId int  , "
						+ "instructorId int  , "
						+ "start bigint, "
						+ "end bigint, "
						+ "Studentid int, "
						+ "sessionId int , "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS Survey ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "instructorid int  , "
						+ "type tinyint, "
						+ "category varchar(255), "
						+ "description varchar(1000), "
						+ "pointing int, "
						+ "title varchar(255), "
						+ "PRIMARY KEY (id));"

						+
						// "DROP TABLE IF EXISTS Instructor;" +
						// "DROP TABLE IF EXISTS Student;" +
						"CREATE TABLE IF NOT EXISTS Instructor ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "Username varchar(255)   UNIQUE, "
						+ "Password varchar(255), "
						+ "FirstName varchar(255), "
						+ "MiddleName varchar(255), "
						+ "LastName varchar(255), "
						+ "Language varchar(255), "
						+ "PRIMARY KEY (ID));"

						+ "CREATE TABLE IF NOT EXISTS Student ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "Username varchar(255)  , "
						+ "Password varchar(255), "
						+ "FirstName varchar(255), "
						+ "MiddleName varchar(255), "
						+ "LastName varchar(255), "
						+ "Language varchar(255), "
						+ "PRIMARY KEY (id));"
						+

						// "DROP TABLE IF EXISTS Course;" +

						"CREATE TABLE IF NOT EXISTS Room ("
						+ "id int DEFAULT 0   AUTO_INCREMENT,"
						+ "iid UUID,"
						+ "name varchar(255)  , "
						+ "attendancetype tinyint, "
						+ "signintype tinyint, "
						+ "seatSize int, "
						+ "PRIMARY KEY (id));"

						// +"DROP TABLE IF EXISTS Session;"
						+ "CREATE TABLE IF NOT EXISTS Session ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "Instructorid int  , "
						+ "Roomid int  , "
						+ "Attendantid int, "
						+ "Courseid int, "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS Attendant ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "Sessionid int  , "
						+ "Seatid int  , "
						+ "studentid int  , "
						+ "courseid int ,"
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS Course ("
						+ "id int DEFAULT 0   AUTO_INCREMENT,"
						+ "iid UUID,"
						+ "Instructorid int, "
						+ "name varchar(255),"
						+ "classType varchar(255), "
						+ "level varchar(255), "
						+ "session int,"
						+ "startDate double, "
						+ "day1 varchar(10),"
						+ "day2 varchar(10),"
						+ "day3 varchar(10),"
						+ "day4 varchar(10),"
						+ "day5 varchar(10) , "
						+ "startTime1 int,"
						+ "startTime2 int,"
						+ "startTime3 int,"
						+ "startTime4 int,"
						+ "startTime5 int, "
						+ "endTime1 int,"
						+ "endTime2 int,"
						+ "endTime3 int,"
						+ "endTime4 int,"
						+ "endTime5 int, "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS Seat ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "iid UUID,"
						+ "Roomid int  , "
						+ "name varchar(255), "
						+ "row int, col int, "
						+ "PRIMARY KEY (id));"

						+ "CREATE TABLE IF NOT EXISTS StudentCourse ("
						+ "id int DEFAULT 0   AUTO_INCREMENT, "
						+ "studentId int  , "
						+ "courseId int  , "
						+ "score int  , "
						+ "PRIMARY KEY (id));"

						+ "ALTER TABLE Course ADD CONSTRAINT IF NOT EXISTS FKReponse423401 FOREIGN KEY (InstructorId) REFERENCES Instructor(id);"
						+ "ALTER TABLE StudentCourse ADD CONSTRAINT IF NOT EXISTS FKReponse423401 FOREIGN KEY (StudentId) REFERENCES Student (id);"
						+ "ALTER TABLE StudentCourse ADD CONSTRAINT IF NOT EXISTS FKReponse423401 FOREIGN KEY (CourseId) REFERENCES Course (id);";

				// +
				// "ALTER TABLE QuizReponse ADD CONSTRAINT IF NOT EXISTS FKReponse203997 FOREIGN KEY (studentId) REFERENCES Student (id);"
				// +
				// "ALTER TABLE QuizReponse ADD CONSTRAINT IF NOT EXISTS FKReponse423401 FOREIGN KEY (quizquestionid) REFERENCES QuizQuestion (id);"
				// +
				// "ALTER TABLE QuizQuestion ADD CONSTRAINT IF NOT EXISTS FKQuizQuesti740493 FOREIGN KEY (quizId) REFERENCES Quiz (id);"
				// +
				// "ALTER TABLE QuizQuestion ADD CONSTRAINT IF NOT EXISTS FKQuizQuesti13848 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"
				// +
				// "ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun738461 FOREIGN KEY (instructorid) REFERENCES Instructor (id);"
				// +
				// "ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun166110 FOREIGN KEY (Studentid) REFERENCES Student (id);"
				// +
				// "ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun478787 FOREIGN KEY (Quizid) REFERENCES Quiz (id);"
				// +
				// "ALTER TABLE Quiz ADD CONSTRAINT IF NOT EXISTS FKQuiz212301 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"
				//
				// +
				// "ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession669271 FOREIGN KEY (Attendantid) REFERENCES Attendant (id);"
				// +
				// "ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession748148 FOREIGN KEY (Roomid) REFERENCES Room (id);"
				// +
				// "ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession747599 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"
				// +
				// "ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession228309 FOREIGN KEY (Courseid) REFERENCES Course (id);"
				// +
				// "ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770036 FOREIGN KEY (Seatid) REFERENCES Seat (id);"
				// +
				// "ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770436 FOREIGN KEY (Sessionid) REFERENCES Session (id);"
				// +
				// "ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770536 FOREIGN KEY (studentid) REFERENCES Student (id);"
				// +
				// "ALTER TABLE Seat ADD CONSTRAINT IF NOT EXISTS FKSeat323739 FOREIGN KEY (Roomid) REFERENCES Room (id);";

				Statement statement = con.createStatement();
				statement.executeUpdate(query);

				// TODO just for sample data

				/*
				 * query =
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(1,'majid','majid','Majid','Ghasemi','none');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(2,'armin','armin','Armin','Samiei','farsi');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(3,'mohammad','mohammad','Mohammad','Tahaei','eng');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(4,'jajeem','jajeem','Jajeem','Jajeem','none');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(5,'john','john','John','Smith','none');"
				 * ;
				 */
				/*
				 * query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(0,'admin','admin','admin','admin','admin');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(1,'john','john','John','Smith','eng');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(2,'ali','ali','Ali','Alavi','farsi');"
				 * ; query +=
				 * "Insert into instructor(id,username,password,firstname,lastname,language) values(3,'mousavi','mousavi','Moosavi','Mousavi','none');"
				 * ;
				 * 
				 * query +=
				 * "Insert into student(id,username,password,firstname,lastname,language) values(1,'ali','ali','Ali','Mohammadi','none');"
				 * ; query +=
				 * "Insert into student(id,username,password,firstname,lastname,language) values(2,'student1','student1','Student 1','','none');"
				 * ; query +=
				 * "Insert into student(id,username,password,firstname,lastname,language) values(3,'student2','student2','Student 2','','none');"
				 * ;
				 * 
				 * query += "Insert into room(name,seatsize) values('a',10);";
				 * query += "Insert into room(name,seatsize) values('b',20);";
				 * query += "Insert into room(name,seatsize) values('c',30);";
				 * query += "Insert into room(name,seatsize) values('d',45);";
				 * 
				 * query +=
				 * "INSERT INTO Course (name, instructorId, classType, level, session, startDate,"
				 * +
				 * " day1, startTime1, endTime1,day2, startTime2, endTime2,day3, startTime3, endTime3,day4, startTime4, endTime4,day5, startTime5, endTime5) "
				 * +
				 * " VALUES ('Inter', 0, 'Intense', 'B', 3 , 232322, 'Su', 12, 14 , '', null, null, '' , null, null, '', null, null, '', null,null);"
				 * ;
				 * 
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a1',1,1);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a2',2,1);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a3',3,1);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a4',4,1);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a5',1,2);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a6',1,3);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a7',1,4);";
				 * query +=
				 * "Insert into seat(roomid,name,row,col) values(2,'a8',1,5);";
				 * 
				 * query +=
				 * "Insert into session(id,Instructorid,Roomid,Attendantid,Courseid) "
				 * + "values(1,1,2,0,1);"; query +=
				 * "Insert into session(id,Instructorid,Roomid,Attendantid,Courseid) "
				 * + "values(2,1,2,0,2);"; query +=
				 * "Insert into session(id,Instructorid,Roomid,Attendantid,Courseid) "
				 * + "values(3,2,2,0,2);"; query +=
				 * "Insert into session(id,Instructorid,Roomid,Attendantid,Courseid) "
				 * + "values(4,2,2,0,3);";
				 * 
				 * query +=
				 * "Insert into QuizRun(id,iid,quizid,instructorid,studentid,sessionid,score,start,end) "
				 * +
				 * "values(1,1,1,1,1,1,20,"+System.currentTimeMillis()+","+System
				 * .currentTimeMillis()+");"; query +=
				 * "Insert into QuizRun(id,iid,quizid,instructorid,studentid,sessionid,score,start,end) "
				 * +
				 * "values(2,2,2,1,1,2,20,"+System.currentTimeMillis()+","+System
				 * .currentTimeMillis()+");"; query +=
				 * "Insert into QuizRun(id,iid,quizid,instructorid,studentid,sessionid,score,start,end) "
				 * +
				 * "values(3,3,3,2,1,3,20,"+System.currentTimeMillis()+","+System
				 * .currentTimeMillis()+");"; query +=
				 * "Insert into QuizRun(id,iid,quizid,instructorid,studentid,sessionid,score,start,end) "
				 * +
				 * "values(4,4,4,2,2,4,20,"+System.currentTimeMillis()+","+System
				 * .currentTimeMillis()+");";
				 * 
				 * query +=
				 * "Insert into quiz(id,instructorid,points,time,title) values(1,1,200,10,'Math');"
				 * ; query +=
				 * "Insert into quiz(id,instructorid,points,time,title) values(2,1,400,6,'Geo');"
				 * ; query +=
				 * "Insert into quiz(id,instructorid,points,time,title) values(3,1,100,12,'History');"
				 * ; query +=
				 * "Insert into quiz(id,instructorid,points,time,title) values(4,1,300,20,'English');"
				 * ;
				 * 
				 * query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (1,1,'Where are you from?',1,10,'','','Tehran','Esfahan','Shiraz','Tabriz','',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (2,1,'Where were you?',1,10,'','','nomad','there','nowhere','who?where?','',false,true,false,false,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (3,1,'How old are you?',1,10,'','','10','20','30','40','None of your business',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (4,2,'Where are you from?',1,10,'','','Tehran','Esfahan','Shiraz','Tabriz','',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (5,2,'Where were you?',1,10,'','','here','there','nowhere','who?where?','',false,true,false,false,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (6,2,'How old are you?',1,10,'','','10','20','30','40','None of your business',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (7,2,'Where are you from?',1,10,'','','Tehran','Esfahan','Shiraz','Tabriz','',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (8,3,'Where were you?',1,10,'','','here','there','nowhere','who?where?','',false,true,false,false,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (9,3,'How old are you?',1,10,'','','10','20','30','40','None of your business',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (10,4,'Where are you from?',1,10,'','','Tehran','Esfahan','Shiraz','Tabriz','',false,false,false,true,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (11,4,'Where were you?',1,10,'','','here','there','nowhere','who?where?','',false,true,false,false,false,1);"
				 * ; query +=
				 * "Insert into QuizQuestion (id , quizId , title , type , point , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , bool1 , bool2 , bool3, bool4 , bool5 , instructorid) "
				 * +
				 * "values (12,4,'How old are you?',1,10,'','','10','20','30','40','None of your business',false,false,false,true,false,1);"
				 * ;
				 * 
				 * query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * +
				 * "values (1,1,'yeah.I am from here',true,false,false,false,false,false,1,1);"
				 * ; query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * +
				 * "values (2,1,'Nope,I am from tehran',false,false,false,true,false,true,1,2);"
				 * ; query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * + "values (3,4,'',false,false,false,true,false,true,1,3);";
				 * query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * +
				 * "values (4,2,'yeah.I am from here',true,false,false,false,false,false,2,1);"
				 * ; query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * +
				 * "values (5,2,'Nope,I am from tehran',false,false,false,true,false,false,2,2);"
				 * ; query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * + "values (6,2,'',false,false,false,true,false,true,1,3);";
				 * query +=
				 * "Insert into quizresponse (id,runid, answer, bool1, bool2, bool3, bool4, bool5, answerValid, studentId  , QuizQuestionid) "
				 * + "values (7,3,'',false,false,false,true,false,true,1,4);";
				 * 
				 * query +=
				 * "Insert into SURVEYRUN(id, surveyId  , instructorId  , Studentid, sessionId, start, end) "
				 * +
				 * "values (1,1,1,1,1,+"+System.currentTimeMillis()+","+System.
				 * currentTimeMillis()+");"; query +=
				 * "Insert into SURVEYRUN(id, surveyId  , instructorId  , Studentid, sessionId, start, end) "
				 * +
				 * "values (2,2,1,2,1,+"+System.currentTimeMillis()+","+System.
				 * currentTimeMillis()+");"; query +=
				 * "Insert into SURVEYRUN(id, surveyId  , instructorId  , Studentid, sessionId, start, end) "
				 * +
				 * "values (3,2,1,2,1,+"+System.currentTimeMillis()+","+System.
				 * currentTimeMillis()+");"; query +=
				 * "Insert into SURVEYRUN(id, surveyId  , instructorId  , Studentid, sessionId, start, end) "
				 * +
				 * "values (4,4,1,1,1,+"+System.currentTimeMillis()+","+System.
				 * currentTimeMillis()+");"; query +=
				 * "Insert into SURVEYRUN(id, surveyId  , instructorId  , Studentid, sessionId, start, end) "
				 * +
				 * "values (5,3,1,2,1,+"+System.currentTimeMillis()+","+System.
				 * currentTimeMillis()+");";
				 * 
				 * query+="Insert into Survey (id , instructorid , title)  " +
				 * "values (1,1,'Who is the best student here?');";
				 * query+="Insert into Survey (id , instructorid , title)  " +
				 * "values (2,1,'Who is the best president?');";
				 * query+="Insert into Survey (id , instructorid , title)  " +
				 * "values (3,1,'What is the best picture?');";
				 * query+="Insert into Survey (id , instructorid , title)  " +
				 * "values (4,1,'Who is the best scientist?');";
				 * 
				 * query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (1,1,1,'Who has the best picture?',1,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (2,1,2,'Who has the best cassette?',0,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (3,1,3,'Who has the best player?',1,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (4,2,4,'Who has the best cell?',0,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (5,2,5,'Who has the best face?',1,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (6,2,6,'Who has the best kid?',0,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (7,3,7,'Who has the best parent?',1,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ; query +=
				 * "Insert into SurveyQuestion (id ,surveyid , responseId , title , type , imagepath , url , answer1 , answer2 , answer3 , answer4 , answer5 , instructorid) "
				 * +
				 * "values (8,3,8,'Who has the best id?',1,'','','first picture','second picture','third picture','forth picture','fifth picture',1);"
				 * ;
				 * 
				 * query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (1,'first picture',true,false,false,false,false,1,1);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (2,'second picture',false,false,true,false,false,1,2);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (3,'third picture',true,false,false,false,false,2,1);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (4,'fourth picture',false,false,true,false,false,2,2);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (5,'fifth picture',true,false,false,false,false,1,3);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (6,'sixth picture',false,true,false,false,false,1,4);"
				 * ; query +=
				 * "Insert into SurveyReponse (id , answer , bool1 , bool2 , bool3 , bool4 , bool5 , studentId , ResponseQuestionid ) "
				 * +
				 * "values (7,'seventh picture',true,false,false,false,false,1,5);"
				 * ;
				 */
				statement = con.createStatement();
				statement.executeUpdate(query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		initialize();
	}

}
