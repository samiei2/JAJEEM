package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Question;

public interface IQuestionDAO {
	
	void create(Question survey) throws SQLException;
	void update(Question survey) throws SQLException;
	Question get(int id) throws SQLException;
	ArrayList<Question> list() throws SQLException;
}
