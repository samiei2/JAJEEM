package com.jajeem.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Student;

public interface IStudentDAO extends IUserDAO {
	boolean authenticate(String username, char[] pass) throws SQLException;

	Student get(Student student) throws SQLException;

	Student create(Student student) throws SQLException;

	boolean update(Student student) throws SQLException;

	boolean delete(Student student) throws SQLException;

	ArrayList<Student> list() throws SQLException;
}
