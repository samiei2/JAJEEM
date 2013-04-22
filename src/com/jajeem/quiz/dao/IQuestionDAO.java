package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Quiz;;

public interface IQuestionDAO {
	
	void create(Quiz survey) throws SQLException;
	void update(Quiz survey) throws SQLException;
	Quiz get(int id) throws SQLException;
	ArrayList<Quiz> list() throws SQLException;
}
