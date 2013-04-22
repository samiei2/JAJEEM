package com.jajeem.core.dao;

import com.jajeem.core.model.Instructor;

public interface IInstructorDAO extends IUserDAO {
	
	Instructor authenticate(String user, String pass);
	Instructor create(Instructor instructor);
	void list();
	Instructor get(Instructor instructor);
	boolean delete(Instructor instructor);
	boolean update(Instructor instructor);
}
