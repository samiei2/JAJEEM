package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Quiz;

public interface IQuizDAO {
	
	Quiz create(Quiz respons) throws SQLException;
	Quiz get(Quiz quiz) throws SQLException;
	boolean update(Quiz respons) throws SQLException;
	boolean delete(Quiz quiz) throws SQLException;
	ArrayList<Quiz> list() throws SQLException;
}
