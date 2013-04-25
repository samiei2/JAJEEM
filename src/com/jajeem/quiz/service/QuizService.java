package com.jajeem.quiz.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.Student;
import com.jajeem.quiz.dao.h2.QuizDAO;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Response;

public class QuizService implements IQuizService{
	private QuizDAO quizDAO;
	@Override
	public Quiz create(Quiz quiz) throws SQLException {
		if(quizDAO != null)
			quizDAO.create(quiz);
		return null;
	}

	@Override
	public Quiz get(Quiz quiz) throws SQLException {
		if(quizDAO != null)
			quizDAO.get(quiz);
		return null;
	}

	@Override
	public boolean update(Quiz quiz) throws SQLException {
		if(quizDAO != null)
			quizDAO.update(quiz);
		return false;
	}

	@Override
	public boolean delete(Quiz quiz) throws SQLException {
		if(quizDAO != null)
			quizDAO.delete(quiz);
		return false;
	}

	@Override
	public ArrayList<Quiz> list() throws SQLException {
		if(quizDAO != null)
			quizDAO.list();
		return null;
	}

	@Override
	public Quiz copy(Quiz quiz) throws SQLException {
		if(quizDAO != null)
			quizDAO.get(quiz);
		return null;
	}

	@Override
	public Response getResponsesByQuestion(Question question) throws SQLException {
		
		return null;
	}

	@Override
	public Response getResponsesByStudent(Student student) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Response> getResponses() throws SQLException {
		
		return null;
	}

	public QuizDAO getQuizDAO() {
		return quizDAO;
	}

	public void setQuizDAO(QuizDAO quizDAO) {
		this.quizDAO = quizDAO;
	}

}
