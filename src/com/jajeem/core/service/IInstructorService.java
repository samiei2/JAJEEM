package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Instructor;

public interface IInstructorService {
	boolean authenticate(String user, char[] cs) throws SQLException;
	Instructor create(Instructor instructor) throws SQLException;
	Instructor get(Instructor instructor) throws SQLException;
	boolean update(Instructor instructor) throws SQLException;
	boolean delete(Instructor instructor) throws SQLException;
	ArrayList<Instructor> list() throws SQLException;
}
