package com.jajeem.survey.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.dao.h2.QuestionDAO;
import com.jajeem.survey.dao.h2.ResponseDAO;
import com.jajeem.survey.dao.h2.RunDAO;
import com.jajeem.survey.dao.h2.SurveyDAO;
import com.jajeem.survey.model.Survey;

public class SurveyService implements ISurveyService{
	private SurveyDAO surveyDAO;
	private QuestionDAO questionDAO;
	private RunDAO runDAO;
	private ResponseDAO responseDAO;
	@Override
	public Survey create(Survey survey) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Survey survey) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Survey get(Survey survey) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Survey survey) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Survey> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getResponses() {
		// TODO Auto-generated method stub
		
	}

	public SurveyDAO getSurveyDAO() {
		return surveyDAO;
	}

	public void setSurveyDAO(SurveyDAO surveyDAO) {
		this.surveyDAO = surveyDAO;
	}

	public QuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public RunDAO getRunDAO() {
		return runDAO;
	}

	public void setRunDAO(RunDAO runDAO) {
		this.runDAO = runDAO;
	}

	public ResponseDAO getResponseDAO() {
		return responseDAO;
	}

	public void setResponseDAO(ResponseDAO responseDAO) {
		this.responseDAO = responseDAO;
	}
	
}
