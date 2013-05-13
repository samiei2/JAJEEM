package com.jajeem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class initDatabase {

	public static void initialize() {
		Connection con;
		try {
			con = BaseDAO.getConnection();
			String query = "";
			query += "CREATE TABLE IF NOT EXISTS QuizQuestion (Quizid int NOT NULL, id int DEFAULT 0 NOT NULL AUTO_INCREMENT, title varchar(5000), type tinyint NOT NULL, point int, imagepath varchar(1000), url varchar(555), answer1 varchar(555), answer2 varchar(555), answer3 varchar(555), answer4 varchar(555), answer5 varchar(555), Instructorid int NOT NULL, PRIMARY KEY (Quizid, id));"+
					 "CREATE TABLE IF NOT EXISTS QuizRun (Quizid int NOT NULL, Instructorid int NOT NULL, start int, end int, id int DEFAULT 0 NOT NULL AUTO_INCREMENT, sessionId int NOT NULL, Studentid int NOT NULL, PRIMARY KEY (id, sessionId));"+
					 "CREATE TABLE IF NOT EXISTS Quiz (id int DEFAULT 0 NOT NULL AUTO_INCREMENT, instructorid int NOT NULL, type tinyint, category varchar(255), description varchar(1000), pointing int, points int, time int, shuffle tinyint, title varchar(255), PRIMARY KEY (id));"+
					 "CREATE TABLE IF NOT EXISTS Reponse (QuizRunid int, QuizRunsessionId int, answer varchar(555), Studentid int NOT NULL, id int DEFAULT 0 NOT NULL AUTO_INCREMENT, bool1 varchar(5), bool2 varchar(5), bool3 varchar(5), bool4 varchar(5), bool5 varchar(5), PRIMARY KEY (id));"+
					 "ALTER TABLE QuizQuestion ADD CONSTRAINT IF NOT EXISTS FKQuizQuesti740493 FOREIGN KEY (Quizid) REFERENCES Quiz (id);"+
					 "ALTER TABLE QuizRun ADD CONSTRAINT IF NOT EXISTS FKQuizRun478787 FOREIGN KEY (Quizid) REFERENCES Quiz (id);"+
					 "ALTER TABLE Reponse ADD CONSTRAINT IF NOT EXISTS FKReponse175471 FOREIGN KEY (QuizRunid, QuizRunsessionId) REFERENCES QuizRun (id, sessionId);";

			Statement statement = con.createStatement();
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}

}
