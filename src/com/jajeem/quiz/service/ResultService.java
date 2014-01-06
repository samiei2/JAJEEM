package com.jajeem.quiz.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.events.QuizResponse;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.dao.h2.ResponseDAO;
import com.jajeem.quiz.dao.h2.RunDAO;
import com.jajeem.quiz.model.Response;
import com.jajeem.quiz.model.Run;

public class ResultService {
	public void create(ArrayList<ArrayList<QuizResponse>> e, ArrayList<Run> run) {
		RunDAO rundao = new RunDAO();
		ResponseDAO responsedao = new ResponseDAO();
		try {
			for (int i = 0; i < run.size(); i++) {
				if (rundao.Contains(run.get(i))) {
					rundao.update(run.get(i));
				} else {
					rundao.create(run.get(i));
				}
			}
		} catch (SQLException e1) {
			JajeemExceptionHandler.logError(e1);
			e1.printStackTrace();
		}
		for (int i = 0; i < e.size(); i++) {
			for (int j = 0; j < e.get(i).size(); j++) {
				Response resp = e.get(i).get(j).getQuestion().getResponse();
				resp.setStudentId(e.get(i).get(j).getStudent().getId());
				resp.setQuizQuestionId(e.get(i).get(j).getQuestion().getId());
				try {
					if (responsedao.Contains(resp)) {
						responsedao.update(resp);
					} else {
						responsedao.create(resp);
					}
				} catch (SQLException e1) {
					JajeemExceptionHandler.logError(e1);
					e1.printStackTrace();
				}
			}
		}
	}
}
