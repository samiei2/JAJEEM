package com.jajeem.quiz.dao.h2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.dao.IQuestionDAO;
import com.jajeem.quiz.model.Question;

public class QuestionDAO implements IQuestionDAO {

	Logger logger = Logger.getLogger(QuestionDAO.class);

	public QuestionDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Question create(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO QuizQuestion (instructorId, title, quizId, type, point, imagePath, url," +
				" answer1, answer2, answer3, answer4, answer5) "
				+ " VALUES (?, ?, ?, ?, ? , ?, ?, ?, ? , ? , ? , ?);");
		ps.setInt(1, question.getInstructorId());
		ps.setString(2, question.getTitle());
		ps.setInt(3, question.getQuizId());
		ps.setByte(4, question.getType());
		ps.setInt(5, question.getPoint());
		ps.setString(6, question.getImagePath());
		ps.setString(7, question.getUrl());
		ps.setString(8, question.getAnswer1());
		ps.setString(9, question.getAnswer2());
		ps.setString(10, question.getAnswer3());
		ps.setString(11, question.getAnswer4());
		ps.setString(12, question.getAnswer5());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				question.setId(maxId.getInt(1));
			} else {
				question.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					question.setId(-1);
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

		return question;
	}

	@Override
	public Question get(Question question) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizQuestion WHERE Question.id = ?;");
		ps.setInt(1, question.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId(rs.getInt("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("points"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));

			} else {
				question.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(-1);
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

		ps = con.prepareStatement("UPDATE QuizQuestion SET instructorId = ?, title = ?, quizId = ?, type = ?, point = ?, imagePath = ?" +
				", url, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, answer5 = ? WHERE id = ?");

		ps.setInt(1, question.getInstructorId());
		ps.setString(2, question.getTitle());
		ps.setInt(3, question.getQuizId());
		ps.setByte(4, question.getType());
		ps.setInt(5, question.getPoint());
		ps.setString(6, question.getImagePath());
		ps.setString(7, question.getUrl());
		ps.setString(8, question.getAnswer1());
		ps.setString(9, question.getAnswer2());
		ps.setString(10, question.getAnswer3());
		ps.setString(11, question.getAnswer4());
		ps.setString(12, question.getAnswer5());
		ps.setInt(13, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(-1);
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

		ps = con.prepareStatement("DELETE FROM QuizQuestion WHERE Question.id = ?;");
		ps.setInt(1, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(-1);
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

		ps = con.prepareStatement("SELECT * FROM QuizQuestion");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();

				question.setId(rs.getInt("id"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId(rs.getInt("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("quizId"));
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

	public Collection<? extends Question> list(int id) throws SQLException {
		ArrayList<Question> allQuestions = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM QuizQuestion where QuizQuestion.Quizid=? ;");
			ps.setInt(1, id);

		
			rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();

				question.setId(rs.getInt("id"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId(rs.getInt("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("quizId"));
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
