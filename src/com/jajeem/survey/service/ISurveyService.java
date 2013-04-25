package com.jajeem.survey.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Survey;

public interface ISurveyService {
	
	Survey create(Survey survey) throws SQLException;
	boolean update(Survey survey) throws SQLException;
	Survey get(Survey survey) throws SQLException;
	boolean delete(Survey survey) throws SQLException;
	ArrayList<Survey> list() throws SQLException;
	void copy();
	void getResponses();
}
