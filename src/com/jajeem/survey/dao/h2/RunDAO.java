package com.jajeem.survey.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.dao.h2.QuizDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.survey.dao.IRunDAO;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.BaseDAO;

public class RunDAO implements IRunDAO {

	Logger logger = Logger.getLogger(RunDAO.class);

	public RunDAO() {
		
	}

	@Override
	public Run create(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO SurveyRun (instructorId, sessionId, surveyId, start, end) "
				+ " VALUES (?, ?, ?, ?, ?);");
		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getSessionId());
		ps.setInt(3, run.getSurveyId());
		ps.setLong(4, run.getStart());
		ps.setLong(5, run.getEnd());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				run.setId(maxId.getInt(1));
			} else {
				run.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					run.setId(-1);
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}
		
		try{
			SurveyDAO surveydao = new SurveyDAO();
			Survey survey = run.getSurvey();
			surveydao.create(survey);
		}
		catch(Exception e){
			
		}

		return run;
	}

	@Override
	public Run get(Run run) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyRun WHERE Run.id = ?;");
		ps.setInt(1, run.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				run.setInstructorId(rs.getInt("instructorId"));
				run.setSessionId(rs.getInt("sessionId"));
				run.setSurveyId(rs.getInt("surveyId"));
				run.setStart(rs.getInt("start"));
				run.setEnd(rs.getInt("end"));

			} else {
				run.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return run;
	}

	@Override
	public boolean update(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE SurveyRun SET instructorId = ?, sessionId = ?, "
				+ "surveyId = ?, start = ?, end = ? WHERE id = ?");

		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getSessionId());
		ps.setInt(3, run.getSurveyId());
		ps.setLong(4, run.getStart());
		ps.setLong(5, run.getEnd());
		ps.setInt(6, run.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return false;
	}

	@Override
	public boolean delete(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM SurveyRun WHERE Run.id = ?;");
		ps.setInt(1, run.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return false;
	}

	@Override
	public ArrayList<Run> list() throws SQLException {

		ArrayList<Run> allRuns = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyRun");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Run run = new Run();

				run.setId(rs.getInt("id"));
				run.setInstructorId(rs.getInt("instructorId"));
				run.setSessionId(rs.getInt("sessionId"));
				run.setSurveyId(rs.getInt("surveyId"));
				run.setStart(rs.getInt("start"));
				run.setEnd(rs.getInt("end"));

				allRuns.add(run);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return allRuns;
	}

}
