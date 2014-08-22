package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Student;

public interface IStudentService {
	Student get(Student student) throws SQLException;

	Student create(Student student) throws SQLException;

	boolean update(Student student) throws SQLException;

	boolean delete(Student student) throws SQLException;

	ArrayList<Student> list() throws SQLException;

	boolean authenticate(String user, char[] pass) throws SQLException;
}
