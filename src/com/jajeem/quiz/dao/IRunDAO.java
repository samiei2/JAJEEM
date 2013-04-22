package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Run;

public interface IRunDAO {
	
	void create(Run run) throws SQLException;
	void update(Run run) throws SQLException;
	Run get(int id) throws SQLException;
	ArrayList<Run> list() throws SQLException;
}
