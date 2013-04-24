package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.room.dao.h2.SeatDAO;
import com.jajeem.room.model.Seat;
import com.jajeem.util.H2ConnectionImpl;

public class jUnitSeatDAO {

		@Before
		public void setUp() throws Exception {
			H2ConnectionImpl db = new H2ConnectionImpl();
	        Connection con = db.getConnection();
	        
			String query = "";
			query += "drop table if exists seat;CREATE TABLE seat ( id INT auto_increment, classId INT,name varchar(100), row INT,col INT);";
			Statement statement = con.createStatement();
			statement.executeUpdate(query);
		}

		@Test
		public void testAuthenticate() throws SQLException {
			H2ConnectionImpl db = new H2ConnectionImpl();
	        Connection con = db.getConnection();
	        
	        SeatDAO dao = new SeatDAO();
			Seat s = new Seat();
			s.setClassId(12);
			s.setColumn(2);
			s.setId(1);
			s.setName("Test");
			
			assertEquals(s, dao.create(s));
			
			assertEquals("Test", dao.get(s).getName());
			assertEquals(1, dao.get(s).getId());
			assertEquals(2, dao.get(s).getColumn());
			assertEquals(12, dao.get(s).getClassId());
			
			s.setName("Test modified");
			assertEquals(true, dao.update(s));
			assertEquals("Test modified", dao.get(s).getName());
			
			assertEquals(true, dao.delete(s));
			
			con.close();
		}

}
