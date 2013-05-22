package com.jajeem.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.survey.dao.h2.ResponseDAO;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitSurveyResponseDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
		
		String query = "";
		query += "drop table if exists SurveyResponse;CREATE TABLE SurveyResponse ( id INT auto_increment, runId INT, studentId INT" +
				",answer1 varchar(100),answer2 varchar(100),answer3 varchar(100),answer4 varchar(100),answer5 varchar(100));";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
		
        ResponseDAO dao = new ResponseDAO();
		com.jajeem.survey.model.Response s = new com.jajeem.survey.model.Response();
		s.setRunId(12);
		s.setStudentId(2);
		s.setId(1);
		s.setAnswer1("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getAnswer1());
		assertEquals(1, dao.get(s).getId());
		assertEquals(12, dao.get(s).getRunId());
		assertEquals(2, dao.get(s).getStudentId());
		
		s.setAnswer1("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getAnswer1());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
