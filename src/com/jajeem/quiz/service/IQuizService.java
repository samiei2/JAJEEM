package com.jajeem.quiz.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Student;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Response;

public interface IQuizService {
	
	Quiz create(Quiz quiz) throws SQLException;
	Quiz get(Quiz quiz) throws SQLException;
	boolean update(Quiz quiz) throws SQLException;
	boolean delete(Quiz quiz) throws SQLException;
	ArrayList<Quiz> list() throws SQLException;
	Quiz copy(Quiz quiz) throws SQLException;
	Response getResponsesByQuestion(Question question) throws SQLException;
	Response getResponsesByStudent(Student student) throws SQLException;
	ArrayList<Response> getResponses() throws SQLException;

}
