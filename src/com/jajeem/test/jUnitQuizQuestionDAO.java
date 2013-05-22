package com.jajeem.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.quiz.dao.h2.QuestionDAO;
import com.jajeem.quiz.model.Question;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitQuizQuestionDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
		String query = "";
		query += "drop table if exists QuizQuestion;CREATE TABLE QuizQuestion ( id INT auto_increment, instructorid INT,title varchar(100), quizId INT" +
				", point INT,type tinyint,imgpath varchar(100),url varchar(100),answer1 varchar(100),answer2 varchar(100),answer3 varchar(100),answer4 varchar(100),answer5 varchar(100));";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test(expected = SQLException.class)
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
        
        QuestionDAO dao = new QuestionDAO();
		Question s = new Question();
		s.setInstructorId(12);
		s.setQuizId(2);
		s.setId(1);
		s.setTitle("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getTitle());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getQuizId());
		assertEquals(12, dao.get(s).getInstructorId());
		
		s.setTitle("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getTitle());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
