package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Survey;

public interface ISurveyDAO {
	
	Survey create(Survey survey) throws SQLException;
	boolean update(Survey survey) throws SQLException;
	Survey get(Survey survey) throws SQLException;
	boolean delete(Survey survey) throws SQLException;
	ArrayList<Survey> list() throws SQLException;
}
