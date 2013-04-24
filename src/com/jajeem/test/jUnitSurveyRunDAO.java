package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.survey.dao.h2.RunDAO;
import com.jajeem.survey.model.Run;
import com.jajeem.util.H2ConnectionImpl;

public class jUnitSurveyRunDAO {

	@Before
	public void setUp() throws Exception {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists SurveyRun;CREATE TABLE SurveyRun ( id INT auto_increment, instructorId INT, sessionId INT" +
				", surveyId INT,start INT,end INT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test(expected = SQLException.class)
	public void testAuthenticate() throws SQLException {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
        RunDAO dao = new RunDAO();
		Run s = new Run();
		s.setInstructorId(12);
		s.setSurveyId(2);
		s.setId(1);
		s.setStart(4);
		
		assertEquals(s, dao.create(s));
		
		assertEquals(4, dao.get(s).getStart());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getSurveyId());
		assertEquals(12, dao.get(s).getInstructorId());
		
		s.setStart(5);
		assertEquals(true, dao.update(s));
		assertEquals(5, dao.get(s).getStart());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}
}
