package com.jajeem.quiz.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.dao.h2.QuestionDAO;
import com.jajeem.quiz.dao.h2.QuizDAO;
import com.jajeem.quiz.dao.h2.ResponseDAO;
import com.jajeem.quiz.dao.h2.RunDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;

public class RunService{
	private QuizDAO quizDAO;
	private QuestionDAO questionDAO;
	private ResponseDAO responseDAO;
	private RunDAO runDAO;
	//StartUp start = new StartUp();
	
	public Run create(Run run) throws SQLException {
		runDAO = new RunDAO();//TODO remove this line
		if(runDAO != null)
			return runDAO.create(run);
		return null;
	}
	
	public ArrayList<Quiz> list() throws SQLException {
		quizDAO = new QuizDAO();//TODO remove this line
		if(quizDAO != null)
			return quizDAO.list();
		return null;
	}
}
