package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Quiz;

public interface IQuizDAO {
	
	void create(Quiz respons) throws SQLException;
	void update(Quiz respons) throws SQLException;
	Quiz get(int id) throws SQLException;
	ArrayList<Quiz> list() throws SQLException;
}
