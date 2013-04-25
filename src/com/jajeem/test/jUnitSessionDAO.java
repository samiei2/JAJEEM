package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.room.dao.h2.SessionDAO;
import com.jajeem.room.model.Session;
import com.jajeem.util.H2Connection;

public class jUnitSessionDAO {

	@Before
	public void setUp() throws Exception {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists Session;CREATE TABLE Session ( id INT auto_increment, classId INT, instructorId INT, start INT,end INT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		H2Connection db = new H2Connection();
        Connection con = db.getConnection();
        
        SessionDAO dao = new SessionDAO();
		Session s = new Session();
		s.setClassId(12);
		s.setInstructorId(2);
		s.setId(1);
		s.setStart(4);
		
		assertEquals(s, dao.create(s));
		
		assertEquals(4, dao.get(s).getStart());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getInstructorId());
		assertEquals(12, dao.get(s).getClassId());
		
		s.setStart(5);
		assertEquals(true, dao.update(s));
		assertEquals(5, dao.get(s).getStart());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}
}
