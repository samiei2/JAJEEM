package com.jajeem.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.core.model.Student;
import com.jajeem.util.H2ConnectionImpl;

public class jUnitStudentDAO {

	@Test
	public void test() throws SQLException {
		H2ConnectionImpl db = new H2ConnectionImpl();
        Connection con = db.getConnection();
	
		String query = "";
		query += "drop table if exists Student;CREATE TABLE Student ( id INT auto_increment, firstName varchar(100),middleName varchar(100)," +
  				"lastName varchar(100),username varchar(100),password varchar(100),language varchar(100));";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		} catch (Exception ex) {}
		
		StudentDAO dao = new StudentDAO();
		Student s = new Student();
		s.setUsername("Test");
		s.setPassword("Pass");
		dao.create(s);
		
		assertEquals(true, dao.authenticate("Test", "Pass"));
		assertEquals(false, dao.authenticate("Test", "Wrong!"));
		
		assertEquals("Test", dao.get(s).getUsername());
		
		s.setUsername("Test modified");
		dao.update(s);
		
		assertEquals("Test modified", dao.get(s).getUsername());
		
		assertEquals(true, dao.delete(s));
		
		con.close();
	}

}
