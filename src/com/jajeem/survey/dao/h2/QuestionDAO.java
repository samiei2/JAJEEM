package com.jajeem.survey.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.survey.dao.IQuestionDAO;
import com.jajeem.survey.model.Question;
import com.jajeem.util.BaseDAO;

public class QuestionDAO implements IQuestionDAO {

	Logger logger = Logger.getLogger(QuestionDAO.class);

	public QuestionDAO() {
	}

	@Override
	public Question create(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO SurveyQuestion (instructorId, title, type, imagePath, url," +
				" answer1, answer2, answer3, answer4, answer5, responseid, surveyid,id) "
				+ " VALUES (?, ?, ?, ?, ? , ?, ?, ?, ? , ?, ?, ?, ?);");
		ps.setInt(1, question.getInstructorId());
		ps.setString(2, question.getTitle());
		ps.setByte(3, question.getType());
		ps.setString(4, question.getImagePath());
		ps.setString(5, question.getUrl());
		ps.setString(6, question.getAnswer1());
		ps.setString(7, question.getAnswer2());
		ps.setString(8, question.getAnswer3());
		ps.setString(9, question.getAnswer4());
		ps.setString(10, question.getAnswer5());
		ps.setObject(11, question.getResponse().getId());
		ps.setObject(12, question.getSurveyId());
		ps.setObject(13, question.getId());

		try {
			rs = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
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

		return question;
	}

	@Override
	public Question get(Question question) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyQuestion WHERE Question.id = ?;");
		ps.setObject(1, question.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setType(rs.getByte("type"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));

			} else {
				question.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
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

		return question;
	}

	@Override
	public boolean update(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("UPDATE SurveyQuestion SET instructorId = ?, title = ?, type = ?, imagePath = ?" +
				", url, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, answer5 = ? WHERE id = ?");

		ps.setInt(1, question.getInstructorId());
		ps.setString(2, question.getTitle());
		ps.setByte(3, question.getType());
		ps.setString(4, question.getImagePath());
		ps.setString(5, question.getUrl());
		ps.setString(6, question.getAnswer1());
		ps.setString(7, question.getAnswer2());
		ps.setString(8, question.getAnswer3());
		ps.setString(9, question.getAnswer4());
		ps.setString(10, question.getAnswer5());
		ps.setObject(11, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
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
	public boolean delete(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM SurveyQuestion WHERE Question.id = ?;");
		ps.setObject(1, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
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
	public ArrayList<Question> list() throws SQLException {

		ArrayList<Question> allQuestions = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyQuestion");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();

				question.setId((UUID) rs.getObject("id"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setType(rs.getByte("type"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));
				

				allQuestions.add(question);
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

		return allQuestions;
	}
	
	public Collection<? extends Question> list(UUID id) throws SQLException {
		ArrayList<Question> allQuestions = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM SurveyQuestion where surveyid=? ;");
			ps.setObject(1, id);

		
			rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();

				question.setId((UUID) rs.getObject("id"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setSurveyId((UUID) rs.getObject("surveyid"));
				question.setType(rs.getByte("type"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));

				allQuestions.add(question);
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

		return allQuestions;
	}

}
