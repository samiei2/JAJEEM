package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Survey;

public interface ISurveyDAO {
	
	void create(Survey survey) throws SQLException;
	void update(Survey survey) throws SQLException;
	Survey get(int id) throws SQLException;
	ArrayList<Survey> list() throws SQLException;
}
