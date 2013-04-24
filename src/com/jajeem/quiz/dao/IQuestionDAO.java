package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Question;

public interface IQuestionDAO {

	Question create(Question question) throws SQLException;
	Question get(Question question) throws SQLException;
	boolean update(Question question) throws SQLException;
	boolean delete(Question question) throws SQLException;
	ArrayList<Question> list() throws SQLException;
}
