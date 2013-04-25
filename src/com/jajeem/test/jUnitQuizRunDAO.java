package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.quiz.dao.h2.RunDAO;
import com.jajeem.quiz.model.Run;
import com.jajeem.util.H2Connection;

public class jUnitQuizRunDAO {

	@Before
	public void setUp() throws Exception {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists QuizRun;CREATE TABLE QuizRun ( id INT auto_increment, instructorId INT, sessionId INT" +
				", quizId INT,start INT,end INT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
        RunDAO dao = new RunDAO();
		Run s = new Run();
		s.setInstructorId(12);
		s.setQuizId(2);
		s.setId(1);
		s.setStart(4);
		
		assertEquals(s, dao.create(s));
		
		assertEquals(4, dao.get(s).getStart());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getQuizId());
		assertEquals(12, dao.get(s).getInstructorId());
		
		s.setStart(5);
		assertEquals(true, dao.update(s));
		assertEquals(5, dao.get(s).getStart());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
