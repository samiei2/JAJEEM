package com.jajeem.quiz.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.dao.IQuizDAO;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;

public class QuizDAO implements IQuizDAO {

	Logger logger = Logger.getLogger(QuizDAO.class);

	public QuizDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Quiz create(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("INSERT INTO Quiz (instructorId, title, category, description, points, pointing, time, shuffle) "
					+ " VALUES (?, ?, ?, ?, ? , ?, ?, ?);");
			ps.setInt(1, quiz.getInstructorId());
			ps.setString(2, quiz.getTitle());
			ps.setString(3, quiz.getCategory());
			ps.setString(4, quiz.getDescription());
			ps.setInt(5, quiz.getPoints());
			ps.setInt(6, quiz.getPointing());
			ps.setInt(7, quiz.getTime());
			ps.setInt(8, quiz.getShuffle());

		
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				quiz.setId(maxId.getInt(1));
			} else {
				quiz.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					quiz.setId(-1);
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
			QuestionDAO qdao = new QuestionDAO();
			ArrayList<Question> list = quiz.getQuestionList();
			for (int i = 0; i < list.size(); i++) {
				Question q = list.get(i);
				q.setQuizId(quiz.getId());
				qdao.create(q);
			}
		}catch(Exception e){
			
		}

		return quiz;
	}

	@Override
	public Quiz get(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Quiz WHERE Quiz.id = ?;");
		ps.setInt(1, quiz.getId());

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
				quiz.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(-1);
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
		
		try {
			QuestionDAO qdao = new QuestionDAO();
			quiz.getQuestionList().addAll(qdao.list(quiz.getId()));
			
		} catch(Exception ex){
			
		}

		return quiz;
	}

	@Override
	public boolean update(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Quiz SET instructorId = ?, title = ?, category = ?, description = ?, "
				+ "points = ?, pointing = ?, time = ?, shuffle = ? WHERE id = ?");

		ps.setInt(1, quiz.getInstructorId());
		ps.setString(2, quiz.getTitle());
		ps.setString(3, quiz.getCategory());
		ps.setString(4, quiz.getDescription());
		ps.setInt(5, quiz.getPoints());
		ps.setInt(6, quiz.getPointing());
		ps.setInt(7, quiz.getTime());
		ps.setInt(8, quiz.getShuffle());
		ps.setInt(9, quiz.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(-1);
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
	public boolean delete(Quiz quiz) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Quiz WHERE Quiz.id = ?;");
		ps.setInt(1, quiz.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			quiz.setId(-1);
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
	public ArrayList<Quiz> list() throws SQLException {

		ArrayList<Quiz> allQuizs = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		try {
			ps = con.prepareStatement("SELECT * FROM Quiz");

		
			rs = ps.executeQuery();
			while (rs.next()) {
				Quiz quiz = new Quiz();

				quiz.setId(rs.getInt("id"));
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
		
		try {
			QuestionDAO qdao = new QuestionDAO();
			for (int i = 0; i < allQuizs.size(); i++) {
				Quiz q = allQuizs.get(i);
				q.getQuestionList().addAll(qdao.list(q.getId()));
			}
		} catch(Exception ex){
			
		}

		return allQuizs;
	}


}
