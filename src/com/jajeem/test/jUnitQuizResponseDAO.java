package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.quiz.dao.h2.ResponseDAO;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitQuizResponseDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
        
		String query = "";
		query += "drop table if exists QuizResponse;CREATE TABLE QuizResponse ( id INT auto_increment, runId INT, studentId INT" +
				",answer1 varchar(100),answer2 varchar(100),answer3 varchar(100),answer4 varchar(100),answer5 varchar(100));";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
        
        ResponseDAO dao = new ResponseDAO();
		com.jajeem.quiz.model.Response s = new com.jajeem.quiz.model.Response();
		s.setRunId(2);
		s.setStudentId(12);
		s.setId(1);
		s.setAnswer("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getAnswer());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getRunId());
		assertEquals(12, dao.get(s).getStudentId());
		
		s.setAnswer("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getAnswer());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
