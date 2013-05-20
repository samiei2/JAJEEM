package com.jajeem.quiz.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.util.H2Connection;

import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.dao.IRunDAO;
import com.jajeem.quiz.model.Run;

public class RunDAO implements IRunDAO {

	Logger logger = Logger.getLogger(RunDAO.class);

	public RunDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Run create(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO QuizRun (instructorId, sessionId, quizId, studentid, start, end) "
				+ " VALUES (?, ?, ?, ?, ? ,?);");
		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getSessionId());
		ps.setInt(3, run.getQuizId());
		ps.setInt(4, run.getStudentId());
		ps.setInt(5, run.getStart());
		ps.setInt(6, run.getEnd());
		

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

		return run;
	}

	@Override
	public Run get(Run run) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizRun WHERE QuizRun.id = ?;");
		ps.setInt(1, run.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				run.setInstructorId(rs.getInt("instructorId"));
				run.setSessionId(rs.getInt("sessionId"));
				run.setQuizId(rs.getInt("quizId"));
				run.setStudentId(rs.getInt("studentid"));
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

		ps = con.prepareStatement("UPDATE QuizRun SET instructorId = ?, sessionId = ?, "
				+ "quizId = ?, studentid=?, start = ?, end = ? WHERE id = ?");

		ps.setInt(1, run.getInstructorId());
		ps.setInt(2, run.getSessionId());
		ps.setInt(3, run.getQuizId());
		ps.setInt(4, run.getStudentId());
		ps.setInt(5, run.getStart());
		ps.setInt(6, run.getEnd());
		ps.setInt(7, run.getId());

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

		ps = con.prepareStatement("DELETE FROM QuizRun WHERE QuizRun.id = ?;");
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

		ps = con.prepareStatement("SELECT * FROM QuizRun");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Run run = new Run();

				run.setId(rs.getInt("id"));
				run.setInstructorId(rs.getInt("instructorId"));
				run.setSessionId(rs.getInt("sessionId"));
				run.setQuizId(rs.getInt("quizId"));
				run.setStudentId(rs.getInt("studentid"));
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
