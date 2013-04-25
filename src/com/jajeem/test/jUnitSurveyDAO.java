package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.survey.dao.h2.SurveyDAO;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.H2Connection;

public class jUnitSurveyDAO {

	@Before
	public void setUp() throws Exception {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists Survey;CREATE TABLE Survey ( id INT auto_increment, instructorId INT,title varchar(100)" +
				",category varchar(100),description varchar(100));";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
        SurveyDAO dao = new SurveyDAO();
		Survey s = new Survey();
		s.setInstructorId(12);
		s.setCategory("CAT");
		s.setId(1);
		s.setTitle("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getTitle());
		assertEquals(1, dao.get(s).getId());
		assertEquals("CAT", dao.get(s).getCategory());
		assertEquals(12, dao.get(s).getInstructorId());
		
		s.setTitle("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getTitle());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
