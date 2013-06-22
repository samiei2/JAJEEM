package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.dao.h2.InstructorDAO;
import com.jajeem.core.model.Instructor;

public class InstructorService implements IInstructorService{
	private InstructorDAO instructordao = new InstructorDAO();
	@Override
	public boolean authenticate(String user, char[] cs) throws SQLException {
		if(instructordao!=null)
			return instructordao.authenticate(user, cs);
		return false;
	}

	@Override
	public Instructor create(Instructor instruct) throws SQLException {
		if(instructordao!=null)
			return instructordao.create(instruct);
		return null;
	}

	@Override
	public Instructor get(Instructor instruct) throws SQLException {
		if(instructordao!=null)
			return instructordao.get(instruct);
		return null;
	}
	
	public Instructor get(String user) {
		if(instructordao!=null)
			return instructordao.get(user);
		return null;
	}

	@Override
	public boolean update(Instructor instruct) throws SQLException {
		if(instructordao!=null)
			return instructordao.update(instruct);
		return false;
	}

	@Override
	public boolean delete(Instructor instruct) throws SQLException {
		if(instructordao!=null)
			return instructordao.delete(instruct);
		return false;
	}

	@Override
	public ArrayList<Instructor> list() throws SQLException {
		if(instructordao!=null)
			return instructordao.list();
		return null;
	}

	public InstructorDAO getInstructorDAO() {
		return instructordao;
	}

	public void setInstructorDAO(InstructorDAO instructor) {
		this.instructordao = instructor;
	}

	

}
