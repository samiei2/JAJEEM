package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.core.model.Student;

public class StudentService implements IStudentService{
	private StudentDAO studentDAO;
	
	@Override
	public boolean authenticate(String user, String pass) throws SQLException {
		if(studentDAO != null)
			studentDAO.authenticate(user, pass);
		return false;
	}

	@Override
	public Student get(Student student) throws SQLException {
		if(studentDAO != null)
			studentDAO.get(student);
		return null;
	}

	@Override
	public Student create(Student student) throws SQLException {
		if(studentDAO != null)
			studentDAO.create(student);
		return null;
	}

	@Override
	public boolean update(Student student) throws SQLException {
		if(studentDAO != null)
			studentDAO.update(student);
		return false;
	}

	@Override
	public boolean delete(Student student) throws SQLException {
		if(studentDAO != null)
			studentDAO.delete(student);
		return false;
	}

	@Override
	public ArrayList<Student> list() throws SQLException {
		if(studentDAO != null)
			studentDAO.list();
		return null;
	}

	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

	public void setStudentDAO(StudentDAO localStudent) {
		this.studentDAO = localStudent;
	}

}
