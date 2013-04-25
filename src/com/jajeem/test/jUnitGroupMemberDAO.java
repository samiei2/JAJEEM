package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.jajeem.groupwork.dao.h2.GroupMemberDAO;
import com.jajeem.groupwork.model.GroupMember;
import com.jajeem.util.BaseDAO;
import com.jajeem.util.StartUp;

public class jUnitGroupMemberDAO {

	@Before
	public void setUp() throws Exception {
        @SuppressWarnings("unused")
		StartUp startUp = new StartUp();
        
		Connection con = BaseDAO.getConnection();

		String query = "";
		query += "drop table if exists GroupMember;CREATE TABLE GroupMember ( id INT auto_increment, groupId INT,Leader varchar(100), seatId INT);";
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
	}

	@Test
	public void testAuthenticate() throws SQLException {
		Connection con = BaseDAO.getConnection();
		
        GroupMemberDAO dao = new GroupMemberDAO();
		GroupMember s = new GroupMember();
		s.setGroupId(12);
		s.setSeatId(2);
		s.setId(1);
		s.setLeader("Test");
		
		assertEquals(s, dao.create(s));
		
		assertEquals("Test", dao.get(s).getLeader());
		assertEquals(1, dao.get(s).getId());
		assertEquals(2, dao.get(s).getSeatId());
		assertEquals(12, dao.get(s).getGroupId());
		
		s.setLeader("Test modified");
		assertEquals(true, dao.update(s));
		assertEquals("Test modified", dao.get(s).getLeader());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
