package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.room.dao.h2.RoomDAO;
import com.jajeem.room.model.Room;
import com.jajeem.util.H2ConnectionImpl;

public class jUnitRoomDAO {

	@Before
	public void setUp() throws Exception {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists room;CREATE TABLE room ( id INT auto_increment, seatSize INT,name varchar(100), attendanceType TINYINT,signInType TINYINT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
        RoomDAO dao = new RoomDAO();
		Room s = new Room();
		s.setSeatSize(12);
		s.setAttendanceType((byte)2);
		s.setId(1);
		s.setName("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getName());
		assertEquals(1, dao.get(s).getId());
		assertEquals((byte)2, dao.get(s).getAttendanceType());
		assertEquals(12, dao.get(s).getSeatSize());
		
		s.setName("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getName());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
