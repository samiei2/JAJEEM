package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.groupwork.dao.h2.GroupworkDAO;
import com.jajeem.groupwork.model.Groupwork;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitGroupworkDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
        
		String query = "";
		query += "drop table if exists Groupwork;CREATE TABLE Groupwork ( id INT auto_increment, classId INT,name varchar(100));";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
        
        GroupworkDAO dao = new GroupworkDAO();
		Groupwork s = new Groupwork();
		s.setClassId(12);
		s.setId(1);
		s.setName("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getName());
		assertEquals(1, dao.get(s).getId());
		assertEquals(12, dao.get(s).getClassId());
		
		s.setName("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getName());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
