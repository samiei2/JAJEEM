package com.jajeem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class initDatabase {

	public static void initialize() {
		Connection con;
		try {
			con = BaseDAO.getConnection();
			String query = 
			"CREATE TABLE IF NOT EXISTS QuizReponse (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, answer varchar(555), bool1 boolean, bool2 boolean, bool3 boolean, bool4 boolean, bool5 boolean, answerValid boolean, studentId int NOT NULL, QuizQuestionid int NOT NULL, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS QuizQuestion (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, quizId int NOT NULL, title varchar(5000), type tinyint NOT NULL, point int, imagepath varchar(1000), url varchar(555), answer1 varchar(555), answer2 varchar(555), answer3 varchar(555), answer4 varchar(555), answer5 varchar(555), instructorid int NOT NULL, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS QuizRun (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, quizid int NOT NULL, Instructorid int NOT NULL, start datetime, end datetime, score int, Studentid int NOT NULL, sessionid int NOT NULL, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS Quiz (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, instructorid int NOT NULL, type tinyint, category varchar(255), description varchar(1000), pointing int, points int, time datetime, shuffle tinyint, title varchar(255), PRIMARY KEY (id));"+
			
			"CREATE TABLE IF NOT EXISTS Instructor (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, Username varchar(255), Password varchar(255), FirstName varchar(255), MiddleName varchar(255), LastName varchar(255), Language varchar(255), PRIMARY KEY (ID));"+
			"CREATE TABLE IF NOT EXISTS Student (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, Username varchar(255), Password varchar(255), FirstName varchar(255), MiddleName varchar(255), LastName varchar(255), Language varchar(255), PRIMARY KEY (id));"+
			
			"CREATE TABLE IF NOT EXISTS Room (id int DEFAULT 0 NOT NULL AUTO_INCREMENT,name varchar(255) NOT NULL, attendancetype tinyint, signintype tinyint, seatSize int, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS Session (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, Instructorid int NOT NULL, Roomid int NOT NULL, Attendantid int, Courseid int, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS Attendant (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, Sessionid int NOT NULL, Seatid int NOT NULL, studentid int NOT NULL, PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS Course (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, name varchar(255), PRIMARY KEY (id));"+
			"CREATE TABLE IF NOT EXISTS Seat (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, Roomid int NOT NULL, name varchar(255), row int, col int, PRIMARY KEY (id));"+
			
			"ALTER TABLE QuizReponse ADD CONSTRAINT IF NOT EXISTS FKReponse203997 FOREIGN KEY (studentId) REFERENCES Student (id);"+
			"ALTER TABLE QuizReponse ADD CONSTRAINT IF NOT EXISTS FKReponse423401 FOREIGN KEY (quizquestionid) REFERENCES QuizQuestion (id);"+
			"ALTER TABLE QuizQuestion ADD CONSTRAINT IF NOT EXISTS FKQuizQuesti740493 FOREIGN KEY (quizId) REFERENCES Quiz (id);"+
			"ALTER TABLE QuizQuestion ADD CONSTRAINT IF NOT EXISTS FKQuizQuesti13848 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"+
			"ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun738461 FOREIGN KEY (instructorid) REFERENCES Instructor (id);"+
			"ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun166110 FOREIGN KEY (Studentid) REFERENCES Student (id);"+
			"ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun478787 FOREIGN KEY (Quizid) REFERENCES Quiz (id);"+
			"ALTER TABLE Quiz ADD CONSTRAINT IF NOT EXISTS FKQuiz212301 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"+
			
			"ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession669271 FOREIGN KEY (Attendantid) REFERENCES Attendant (id);"+
			"ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession748148 FOREIGN KEY (Roomid) REFERENCES Room (id);"+
			"ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession747599 FOREIGN KEY (Instructorid) REFERENCES Instructor (id);"+
			"ALTER TABLE Session ADD CONSTRAINT IF NOT EXISTS FKSession228309 FOREIGN KEY (Courseid) REFERENCES Course (id);"+
			"ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770036 FOREIGN KEY (Seatid) REFERENCES Seat (id);"+
			"ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770436 FOREIGN KEY (Sessionid) REFERENCES Session (id);"+
			"ALTER TABLE Attendant ADD CONSTRAINT IF NOT EXISTS FKAttendant770536 FOREIGN KEY (studentid) REFERENCES Student (id);"+
			"ALTER TABLE Seat ADD CONSTRAINT IF NOT EXISTS FKSeat323739 FOREIGN KEY (Roomid) REFERENCES Room (id);";
			
			Statement statement = con.createStatement();
			statement.executeUpdate(query);
			
			
			//TODO just for sample data
			query = "Insert into instructor(username,password,firstname,lastname,language) values('1','1','1','1','none');";
			query += "Insert into instructor(username,password,firstname,lastname,language) values('ali','ali','ali','','eng');";
			query += "Insert into instructor(username,password,firstname,lastname,language) values('armin','armin','armin','armin','farsi');";
			query += "Insert into instructor(username,password,firstname,lastname,language) values('mohammad','mohammad','mohammad','mohammadi','arabic');";
			query += "Insert into instructor(username,password,firstname,lastname,language) values('jajeem','jajeem','jajeem','jajeem','none');";
			query += "Insert into student(username,password,firstname,lastname,language) values('2','2','2','2','none');";
			query += "Insert into student(username,password,firstname,lastname,language) values('ali','ali','ali','ali','none');";
			query += "Insert into student(username,password,firstname,lastname,language) values('master','master','master','master','none');";
			query += "Insert into student(username,password,firstname,lastname,language) values('student','student','student','student','none');";
			
			query += "Insert into room(name,seatsize) values('a',10);";
			query += "Insert into room(name,seatsize) values('b',20);";
			query += "Insert into room(name,seatsize) values('c',30);";
			query += "Insert into room(name,seatsize) values('d',45);";
			
			query += "Insert into course(name) values('farsi');";
			query += "Insert into course(name) values('english');";
			query += "Insert into course(name) values('math');";
			query += "Insert into course(name) values('physic');";
			query += "Insert into course(name) values('chemistry');";
			
			query += "Insert into seat(roomid,name,row,col) values(2,'a1',1,1);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a2',2,1);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a3',3,1);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a4',4,1);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a5',1,2);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a6',1,3);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a7',1,4);";
			query += "Insert into seat(roomid,name,row,col) values(2,'a8',1,5);";
			
			
			
			statement = con.createStatement();
			statement.executeUpdate(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	public static void main(String[] args){
		initialize();
	}

}
