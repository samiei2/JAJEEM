package com.jajeem.survey.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.survey.dao.ISurveyDAO;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.BaseDAO;

public class SurveyDAO implements ISurveyDAO {

	Logger logger = Logger.getLogger(SurveyDAO.class);

	public SurveyDAO() {

	}

	@Override
	public Survey create(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Survey (instructorId, title, category, description,iid) "
				+ " VALUES (?, ?, ?, ?, ?);");
		ps.setInt(1, survey.getInstructorId());
		ps.setString(2, survey.getTitle());
		ps.setString(3, survey.getCategory());
		ps.setString(4, survey.getDescription());
		ps.setObject(5, survey.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					survey.setId(null);
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

		try {
			QuestionDAO qdao = new QuestionDAO();
			ArrayList<Question> list = survey.getQuestionList();
			for (int i = 0; i < list.size(); i++) {
				Question q = list.get(i);
				q.setSurveyId(survey.getId());
				if (qdao.Contains(q)) {
					qdao.update(q);
				} else {
					qdao.create(q);
				}
			}
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
		}

		return survey;
	}

	@Override
	public Survey get(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Survey WHERE Survey.iid = ?;");
		ps.setObject(1, survey.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				survey.setInstructorId(rs.getInt("instructorId"));
				survey.setTitle(rs.getString("title"));
				survey.setCategory(rs.getString("category"));
				survey.setDescription(rs.getString("description"));

			} else {
				survey.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(null);
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

		return survey;
	}

	@Override
	public boolean update(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Survey SET instructorId = ?, title = ?, category = ?, description = ? WHERE iid = ?");

		ps.setInt(1, survey.getInstructorId());
		ps.setString(2, survey.getTitle());
		ps.setString(3, survey.getCategory());
		ps.setString(4, survey.getDescription());
		ps.setObject(5, survey.getId());

		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(null);
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
			QuestionDAO qdao = new QuestionDAO();
			ArrayList<Question> list = survey.getQuestionList();
			for (int i = 0; i < list.size(); i++) {
				Question q = list.get(i);
				q.setSurveyId(survey.getId());
				if (qdao.Contains(q)) {
					qdao.update(q);
				} else {
					qdao.create(q);
				}
			}
		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
		}

		return false;
	}

	@Override
	public boolean delete(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Survey WHERE Survey.iid = ?;");
		ps.setObject(1, survey.getId());

		try {
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(null);
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
	public ArrayList<Survey> list() throws SQLException {

		ArrayList<Survey> allSurveys = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Survey");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Survey survey = new Survey();

				survey.setId((UUID) rs.getObject("iid"));
				survey.setInstructorId(rs.getInt("instructorId"));
				survey.setTitle(rs.getString("title"));
				survey.setCategory(rs.getString("category"));
				survey.setDescription(rs.getString("description"));

				allSurveys.add(survey);
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

		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < allSurveys.size(); i++) {
				Survey q = allSurveys.get(i);
				q.getQuestionList().addAll(qdao.list(q.getId()));
			}
		} catch (Exception ex) {
			JajeemExceptionHandler.logError(ex);
		}

		return allSurveys;
	}

	public boolean Contains(Survey survey) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Survey WHERE Survey.iid = ?;");
		ps.setObject(1, survey.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(null);
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
