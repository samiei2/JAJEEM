package com.jajeem.core.dao;

import com.jajeem.core.model.Student;

public interface IStudentDAO extends IUserDAO {

	Student authenticate(String user, String pass);
	Student create(Student student);
	boolean update(Student student);
	boolean delete(Student student);
	Student get(Student student);
	void list();
}
