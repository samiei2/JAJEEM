package com.jajeem.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Instructor;

public interface IInstructorDAO extends IUserDAO {
	boolean authenticate(String username, char[] pass) throws SQLException;

	Instructor create(Instructor instructor) throws SQLException;

	Instructor get(Instructor instructor) throws SQLException;

	boolean update(Instructor instructor) throws SQLException;

	boolean delete(Instructor instructor) throws SQLException;

	ArrayList<Instructor> list() throws SQLException;
}
