package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.room.dao.h2.AttendantDAO;
import com.jajeem.room.model.Attendant;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitAttendantDAO {

	@Before
	public void setUp() throws Exception {
        @SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
        
		String query = "";
		query += "drop table if exists Attendant;CREATE TABLE Attendant ( id INT auto_increment, sessionId INT,seatId INT, studentId INT,status SMALLINT );";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}
	
	@Test
	public void testAuthenticate() throws SQLException {
        Connection con = BaseDAO.getConnection();
        
        AttendantDAO dao = new AttendantDAO();
		Attendant s = new Attendant();
		s.setSessionId(12);
		s.setSeatId(2);
		s.setId(1);
		s.setStatus((short)3);
		
		assertEquals(s, dao.create(s));
		
		assertEquals((short)3, dao.get(s).getStatus());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getSeatId());
		assertEquals(12, dao.get(s).getSessionId());
		
		s.setSeatId(4);
		assertEquals(true, dao.update(s));
		assertEquals(4, dao.get(s).getSeatId());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
