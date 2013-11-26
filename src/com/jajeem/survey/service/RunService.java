package com.jajeem.survey.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.dao.h2.RunDAO;
import com.jajeem.survey.dao.h2.SurveyDAO;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;

public class RunService {
	private SurveyDAO surveyDAO;
	private RunDAO runDAO;

	// StartUp start = new StartUp();

	public Run create(Run run) throws SQLException {
		runDAO = new RunDAO();// TODO remove this line
		if (runDAO != null) {
			if (runDAO.Contains(run)) {
				runDAO.update(run);
			} else {
				runDAO.create(run);
			}
		}
		return null;
	}

	public ArrayList<Survey> list() throws SQLException {
		surveyDAO = new SurveyDAO();// TODO remove this line
		if (surveyDAO != null) {
			return surveyDAO.list();
		}
		return null;
	}
}
