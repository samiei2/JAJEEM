package com.jajeem.core.dao;

import com.jajeem.core.model.Student;

public interface IStudentDAO extends IUserDAO {

	Student authenticate(String user, String pass);
	Student create(Student student);
	void list();
	Student get(Student student);
	boolean delete(Student student);
	boolean update(Student student);
}
