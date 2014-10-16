package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Question;

public interface IQuestionDAO {

	Question create(Question survey) throws SQLException;

	boolean update(Question survey) throws SQLException;

	Question get(Question question) throws SQLException;

	boolean delete(Question question) throws SQLException;

	ArrayList<Question> list() throws SQLException;
}
