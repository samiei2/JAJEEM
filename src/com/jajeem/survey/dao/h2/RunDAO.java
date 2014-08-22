package com.jajeem.survey.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
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
		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO SurveyRun (instructorId, courseId, surveyId, start, end,iid) "
				+ " VALUES (?, ?, ?, ?, ?, ?);");
		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getCourseId());
		ps.setObject(3, run.getSurveyId());
		ps.setLong(4, run.getStart());
		ps.setLong(5, run.getEnd());
		ps.setObject(6, run.getId());

		try {
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		try {
			SurveyDAO surveydao = new SurveyDAO();
			Survey survey = run.getSurvey();
			if (surveydao.Contains(survey)) {
				surveydao.update(survey);
			} else {
				surveydao.create(survey);
			}
		} catch (Exception e) {

		}

		return run;
	}

	@Override
	public Run get(Run run) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyRun WHERE SurveyRun.iid = ?;");
		ps.setObject(1, run.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				run.setInstructorId(rs.getInt("instructorId"));
				run.setCourseId(rs.getInt("courseId"));
				run.setSurveyId((UUID) rs.getObject("surveyId"));
				run.setStart(rs.getInt("start"));
				run.setEnd(rs.getInt("end"));

			} else {
				run.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		return run;
	}

	@Override
	public boolean update(Run run) throws SQLException {

		PreparedStatement ps = null;
		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE SurveyRun SET instructorId = ?, courseId = ?, "
				+ "surveyId = ?, start = ?, end = ? WHERE iid = ?");

		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getCourseId());
		ps.setObject(3, run.getSurveyId());
		ps.setLong(4, run.getStart());
		ps.setLong(5, run.getEnd());
		ps.setObject(6, run.getId());

		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		try {
			SurveyDAO surveydao = new SurveyDAO();
			Survey survey = run.getSurvey();
			if (surveydao.Contains(survey)) {
				surveydao.update(survey);
			} else {
				surveydao.create(survey);
			}
		} catch (Exception e) {

		}

		return false;
	}

	@Override
	public boolean delete(Run run) throws SQLException {

		PreparedStatement ps = null;
		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM SurveyRun WHERE SurveyRun.iid = ?;");
		ps.setObject(1, run.getId());

		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
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

				run.setId((UUID) rs.getObject("iid"));
				run.setInstructorId(rs.getInt("instructorId"));
				run.setCourseId(rs.getInt("courseId"));
				run.setSurveyId((UUID) rs.getObject("surveyId"));
				run.setStart(rs.getInt("start"));
				run.setEnd(rs.getInt("end"));

				allRuns.add(run);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		return allRuns;
	}

	public boolean Contains(Run run) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyRun WHERE SurveyRun.iid = ?;");
		ps.setObject(1, run.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			run.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		return false;
	}
}
