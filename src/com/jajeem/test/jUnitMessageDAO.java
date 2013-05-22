package com.jajeem.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.message.dao.h2.MessageDAO;
import com.jajeem.message.model.Message;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitMessageDAO {

	@Before
	public void setUp() throws Exception {
		@SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();
        
		String query = "";
		query += "drop table if exists Message;CREATE TABLE Message ( id INT auto_increment, fromId INT,toId INT,content varchar(100), date varchar(100),type smallint,anonymous tinyint);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
        
        MessageDAO dao = new MessageDAO();
		Message s = new Message();
		s.setAnonymous((byte)2);
		s.setContent("Test");
		s.setDate("Test date");
		s.setId(1);
		s.setFromId(1);
		s.setToId(2);
		s.setType((short)3);
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getContent());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getToId());
		assertEquals(1, dao.get(s).getFromId());
		assertEquals((short)3, dao.get(s).getType());
		
		s.setContent("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getContent());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}
}
