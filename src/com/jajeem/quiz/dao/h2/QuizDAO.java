package com.jajeem.quiz.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.dao.IQuizDAO;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.BaseDAO;

public class QuizDAO implements IQuizDAO {

	Logger logger = Logger.getLogger(QuizDAO.class);

	public QuizDAO() {

	}

	@SuppressWarnings("unused")
	@Override
	public Quiz create(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("INSERT INTO Quiz (instructorId, title, category, description, points, pointing, time, shuffle,iid, courseId) "
					+ " VALUES (?,?, ?, ?, ?, ?, ?, ?, ?,?);");
			// ps.setString(1, "1ae8df14-31aa-4b57-9b2c-4eaa52dcb67d");
			ps.setInt(1, quiz.getInstructorId());
			ps.setString(2, quiz.getTitle());
			ps.setString(3, quiz.getCategory());
			ps.setString(4, quiz.getDescription());
			ps.setInt(5, quiz.getPoints());
			ps.setInt(6, quiz.getPointing());
			ps.setInt(7, quiz.getTime());
			ps.setInt(8, quiz.getShuffle());
			ps.setObject(9, quiz.getId());
			ps.setInt(10, quiz.getCourseId());

			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			ArrayList<Question> list = quiz.getQuestionList();
			for (int i = 0; i < list.size(); i++) {
				Question q = list.get(i);
				q.setQuizId(quiz.getId());
				if (qdao.Contains(q)) {
					qdao.update(q);
				} else {
					qdao.create(q);
				}
			}
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
		}

		return quiz;
	}

	@Override
	public Quiz get(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Quiz WHERE Quiz.iid = ?;");
		ps.setObject(1, quiz.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				quiz.setInstructorId(rs.getInt("instructorId"));
				quiz.setTitle(rs.getString("title"));
				quiz.setCategory(rs.getString("category"));
				quiz.setDescription(rs.getString("description"));
				quiz.setPoints(rs.getInt("points"));
				quiz.setPointing(rs.getInt("pointing"));
				quiz.setTime(rs.getInt("time"));
				quiz.setShuffle(rs.getByte("shuffle"));

			} else {
				quiz.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			quiz.getQuestionList().addAll(qdao.list(quiz.getId()));

		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
		}

		return quiz;
	}

	public Quiz get(UUID quizId) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Quiz quiz = new Quiz();
		quiz.setId(quizId);

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Quiz WHERE Quiz.iid = ?;");
		ps.setObject(1, quizId);

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				quiz.setInstructorId(rs.getInt("instructorId"));
				quiz.setTitle(rs.getString("title"));
				quiz.setCategory(rs.getString("category"));
				quiz.setDescription(rs.getString("description"));
				quiz.setPoints(rs.getInt("points"));
				quiz.setPointing(rs.getInt("pointing"));
				quiz.setTime(rs.getInt("time"));
				quiz.setShuffle(rs.getByte("shuffle"));

			} else {
				quiz.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			quiz.getQuestionList().addAll(qdao.list(quiz.getId()));

		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
		}

		return quiz;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean update(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Quiz SET instructorId = ?, title = ?, category = ?, description = ?, "
				+ "points = ?, pointing = ?, time = ?, shuffle = ? WHERE iid = ?");

		ps.setInt(1, quiz.getInstructorId());
		ps.setString(2, quiz.getTitle());
		ps.setString(3, quiz.getCategory());
		ps.setString(4, quiz.getDescription());
		ps.setInt(5, quiz.getPoints());
		ps.setInt(6, quiz.getPointing());
		ps.setInt(7, quiz.getTime());
		ps.setInt(8, quiz.getShuffle());
		ps.setObject(9, quiz.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < quiz.getQuestionList().size(); i++) {
				if (qdao.Contains(quiz.getQuestionList().get(i))) {
					qdao.update(quiz.getQuestionList().get(i));
				} else {
					qdao.create(quiz.getQuestionList().get(i));
				}
			}
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e, QuizDAO.class);
		}

		return false;
	}

	@Override
	public boolean delete(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Quiz WHERE Quiz.iid = ?;");
		ps.setObject(1, quiz.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
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
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return false;
	}

	@Override
	public ArrayList<Quiz> list() throws SQLException {

		ArrayList<Quiz> allQuizs = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM Quiz");
			// con.prepareStatement("Insert into quiz(instructorid) values(1);").executeUpdate();

			rs = ps.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();

				quiz.setId((UUID) rs.getObject("iid"));
				quiz.setInstructorId(rs.getInt("instructorId"));
				quiz.setTitle(rs.getString("title"));
				quiz.setCategory(rs.getString("category"));
				quiz.setDescription(rs.getString("description"));
				quiz.setPoints(rs.getInt("points"));
				quiz.setPointing(rs.getInt("pointing"));
				quiz.setTime(rs.getInt("time"));
				quiz.setShuffle(rs.getByte("shuffle"));

				allQuizs.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JajeemExcetionHandler.logError(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < allQuizs.size(); i++) {
				Quiz q = allQuizs.get(i);
				q.getQuestionList().addAll(qdao.list(q.getId()));
			}
		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
		}

		return allQuizs;
	}

	public ArrayList<Quiz> listByCourseId(int courseId) throws SQLException {

		ArrayList<Quiz> allQuizs = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM Quiz WHERE QUIZ.COURSEID=?");
			ps.setInt(1, courseId);

			rs = ps.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();

				quiz.setId((UUID) rs.getObject("iid"));
				quiz.setInstructorId(rs.getInt("instructorId"));
				quiz.setTitle(rs.getString("title"));
				quiz.setCategory(rs.getString("category"));
				quiz.setDescription(rs.getString("description"));
				quiz.setPoints(rs.getInt("points"));
				quiz.setPointing(rs.getInt("pointing"));
				quiz.setTime(rs.getInt("time"));
				quiz.setShuffle(rs.getByte("shuffle"));

				allQuizs.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JajeemExcetionHandler.logError(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < allQuizs.size(); i++) {
				Quiz q = allQuizs.get(i);
				q.getQuestionList().addAll(qdao.list(q.getId()));
			}
		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
		}

		return allQuizs;
	}

	public ArrayList<Quiz> listByInstructorId(int instructorId)
			throws SQLException {

		ArrayList<Quiz> allQuizs = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM Quiz WHERE QUIZ.INSTRUCTORID=?");
			ps.setInt(1, instructorId);

			rs = ps.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();

				quiz.setId((UUID) rs.getObject("iid"));
				quiz.setInstructorId(rs.getInt("instructorId"));
				quiz.setTitle(rs.getString("title"));
				quiz.setCategory(rs.getString("category"));
				quiz.setDescription(rs.getString("description"));
				quiz.setPoints(rs.getInt("points"));
				quiz.setPointing(rs.getInt("pointing"));
				quiz.setTime(rs.getInt("time"));
				quiz.setShuffle(rs.getByte("shuffle"));

				allQuizs.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JajeemExcetionHandler.logError(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				JajeemExcetionHandler.logError(e);
			}
		}

		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < allQuizs.size(); i++) {
				Quiz q = allQuizs.get(i);
				q.getQuestionList().addAll(qdao.list(q.getId()));
			}
		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
		}

		return allQuizs;
	}

	public boolean Contains(Quiz quiz) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Quiz WHERE iid = ?;");
		ps.setObject(1, quiz.getId());

		try {
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(null);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}
		return false;
	}
}
