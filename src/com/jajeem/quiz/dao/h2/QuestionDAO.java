package com.jajeem.quiz.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.dao.IQuestionDAO;
import com.jajeem.quiz.model.Question;
import com.jajeem.util.BaseDAO;

public class QuestionDAO implements IQuestionDAO {

	Logger logger = Logger.getLogger(QuestionDAO.class);

	public QuestionDAO() {

	}

	@SuppressWarnings("unused")
	@Override
	public Question create(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("INSERT INTO QuizQuestion (instructorId, title, quizId, type, point, imagePath, url,"
					+ " answer1, answer2, answer3, answer4, answer5, bool1, bool2, bool3, bool4, bool5,iid) "
					+ " VALUES (?, ?, ?, ?, ? , ?, ?, ?, ? , ? , ? , ?, ? ,? ,? ,? ,?,?);");

			ps.setInt(1, question.getInstructorId());
			ps.setString(2, question.getTitle());
			ps.setObject(3, question.getQuizId());
			ps.setByte(4, question.getType());
			ps.setInt(5, question.getPoint());
			ps.setString(6, question.getImagePath());
			ps.setString(7, question.getUrl());
			ps.setString(8, question.getAnswer1());
			ps.setString(9, question.getAnswer2());
			ps.setString(10, question.getAnswer3());
			ps.setString(11, question.getAnswer4());
			ps.setString(12, question.getAnswer5());
			ps.setBoolean(13, question.getCorrectAnswer()[0]);
			ps.setBoolean(14, question.getCorrectAnswer()[1]);
			ps.setBoolean(15, question.getCorrectAnswer()[2]);
			ps.setBoolean(16, question.getCorrectAnswer()[3]);
			ps.setBoolean(17, question.getCorrectAnswer()[4]);
			ps.setObject(18, question.getId());

			rs = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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

		return question;
	}

	@Override
	public Question get(Question question) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizQuestion WHERE iid = ?;");
		ps.setObject(1, question.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId((UUID) rs.getObject("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("points"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));
				boolean[] list = new boolean[] { rs.getBoolean("bool1"),
						rs.getBoolean("bool2"), rs.getBoolean("bool3"),
						rs.getBoolean("bool4"), rs.getBoolean("bool5") };
				question.setCorrectAnswer(list);

			} else {
				question.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
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

		return question;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean update(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE QuizQuestion SET instructorId = ?, title = ?, quizId = ?, type = ?, point = ?, imagePath = ?"
				+ ", url = ?, answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, answer5 = ?, bool1 = ?, bool2 = ?, bool3 = ?, bool4 = ?, bool5 = ? WHERE iid = ?");

		ps.setInt(1, question.getInstructorId());
		ps.setString(2, question.getTitle());
		ps.setObject(3, question.getQuizId());
		ps.setByte(4, question.getType());
		ps.setInt(5, question.getPoint());
		ps.setString(6, question.getImagePath());
		ps.setString(7, question.getUrl());
		ps.setString(8, question.getAnswer1());
		ps.setString(9, question.getAnswer2());
		ps.setString(10, question.getAnswer3());
		ps.setString(11, question.getAnswer4());
		ps.setString(12, question.getAnswer5());
		ps.setBoolean(13, question.getCorrectAnswer()[0]);
		ps.setBoolean(14, question.getCorrectAnswer()[1]);
		ps.setBoolean(15, question.getCorrectAnswer()[2]);
		ps.setBoolean(16, question.getCorrectAnswer()[3]);
		ps.setBoolean(17, question.getCorrectAnswer()[4]);
		ps.setObject(18, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
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
	public boolean delete(Question question) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM QuizQuestion WHERE QuizQuestion.iid = ?;");
		ps.setObject(1, question.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			question.setId(null);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
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

				question.setId((UUID) rs.getObject("iid"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId((UUID) rs.getObject("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("point"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));
				boolean[] list = new boolean[] { rs.getBoolean("bool1"),
						rs.getBoolean("bool2"), rs.getBoolean("bool3"),
						rs.getBoolean("bool4"), rs.getBoolean("bool5") };
				question.setCorrectAnswer(list);

				allQuestions.add(question);
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

		return allQuestions;
	}

	public Collection<? extends Question> list(UUID id) throws SQLException {
		ArrayList<Question> allQuestions = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM QuizQuestion where Quizid=? ;");
			ps.setObject(1, id);

			rs = ps.executeQuery();
			while (rs.next()) {
				Question question = new Question();

				question.setId((UUID) rs.getObject("iid"));
				question.setInstructorId(rs.getInt("instructorId"));
				question.setTitle(rs.getString("title"));
				question.setQuizId((UUID) rs.getObject("quizId"));
				question.setType(rs.getByte("type"));
				question.setPoint(rs.getInt("point"));
				question.setImagePath(rs.getString("imagePath"));
				question.setUrl(rs.getString("url"));
				question.setAnswer1(rs.getString("answer1"));
				question.setAnswer2(rs.getString("answer2"));
				question.setAnswer3(rs.getString("answer3"));
				question.setAnswer4(rs.getString("answer4"));
				question.setAnswer5(rs.getString("answer5"));
				boolean[] list = new boolean[] { rs.getBoolean("bool1"),
						rs.getBoolean("bool2"), rs.getBoolean("bool3"),
						rs.getBoolean("bool4"), rs.getBoolean("bool5") };
				question.setCorrectAnswer(list);

				allQuestions.add(question);
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

		return allQuestions;
	}

	public boolean Contains(Question question) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizQuestion WHERE iid = ?;");
		ps.setObject(1, question.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
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
		return false;
	}

}
