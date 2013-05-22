package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.quiz.dao.h2.QuizDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitQuizDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
        
		String query = "";
//		query += "drop table if exists Quiz;CREATE TABLE Quiz ( id INT auto_increment, instructorId INT,title varchar(100), points INT" +
//				",pointing int,category varchar(100),description varchar(100),time int,shuffle tinyint);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
		
        QuizDAO dao = new QuizDAO();
		Quiz s = new Quiz();
		s.setInstructorId(12);
		s.setPoints(2);
		s.setId(1);
		s.setTitle("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getTitle());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getPoints());
		assertEquals(12, dao.get(s).getInstructorId());
		
		s.setTitle("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getTitle());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}
	
	@Test
	public void Test() throws SQLException{
		Connection con = BaseDAO.getConnection();
		
        QuizDAO dao = new QuizDAO();
        Quiz q = new Quiz();
        q.setInstructorId(1);
        dao.create(q);
        dao.create(q);
        dao.create(q);
        dao.create(q);
        dao.create(q);
        dao.create(q);
        
        
        dao.list();
	}

}
