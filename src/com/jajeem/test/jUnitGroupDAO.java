package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.groupwork.dao.h2.GroupDAO;
import com.jajeem.groupwork.model.Group;
import com.jajeem.util.H2ConnectionImpl;

public class jUnitGroupDAO {

	@Before
	public void setUp() throws Exception {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
		String query = "";
		query += "drop table if exists Group;CREATE TABLE Group ( id INT auto_increment, groupworkId INT,name varchar(100), color INT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test(expected = SQLException.class)
	public void testAuthenticate() throws SQLException {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
        
        GroupDAO dao = new GroupDAO();
		Group s = new Group();
		s.setColor(12);
		s.setGroupworkId(2);
		s.setId(1);
		s.setName("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getName());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getGroupworkId());
		assertEquals(12, dao.get(s).getColor());
		
		s.setName("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getName());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}



