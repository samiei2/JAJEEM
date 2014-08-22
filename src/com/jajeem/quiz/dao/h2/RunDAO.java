package com.jajeem.quiz.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.dao.IRunDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.util.BaseDAO;

public class RunDAO implements IRunDAO {

	Logger logger = Logger.getLogger(RunDAO.class);

	public RunDAO() {

	}

	@SuppressWarnings("unused")
	@Override
	public Run create(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO QuizRun (instructorId, quizId, studentid,score, start, end, iid, courseId) "
				+ " VALUES (?,  ?, ? ,? ,? ,? ,?,?);");
		ps.setInt(1, run.getInstructorId());
		ps.setObject(2, run.getQuizId());
		ps.setInt(3, run.getStudentId());
		ps.setInt(4, run.getScore());
		ps.setLong(5, run.getStart());
		ps.setLong(6, run.getEnd());
		ps.setObject(7, run.getId());
		ps.setInt(8, run.getCourseId());

		try {
			rs = ps.executeUpdate();

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
			QuizDAO quizdao = new QuizDAO();
			Quiz quiz = run.getQuiz();
			if (quizdao.Contains(quiz)) {
				quizdao.update(quiz);
			} else {
				quizdao.create(quiz);
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

		ps = con.prepareStatement("SELECT * FROM QuizRun WHERE QuizRun.iid = ?;");
		ps.setObject(1, run.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				run.setInstructorId(rs.getInt("instructorId"));
				run.setQuizId((UUID) rs.getObject("quizId"));
				run.setStudentId(rs.getInt("studentid"));
				run.setScore(rs.getInt("score"));
				run.setStart(rs.getLong("start"));
				run.setEnd(rs.getLong("end"));
				run.setCourseId(rs.getInt("courseId"));

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

	@SuppressWarnings("unused")
	@Override
	public boolean update(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE QuizRun SET instructorId = ?, "
				+ "quizId = ?, studentid=?, start = ?, end = ? ,score = ?, courseId=? WHERE iid = ?");

		ps.setInt(1, run.getInstructorId());
		ps.setObject(2, run.getQuizId());
		ps.setInt(3, run.getStudentId());
		ps.setLong(4, run.getStart());
		ps.setLong(5, run.getEnd());
		ps.setInt(6, run.getScore());
		ps.setInt(7, run.getCourseId());
		ps.setObject(8, run.getId());

		try {
			rs = ps.executeUpdate();
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

	@SuppressWarnings("unused")
	@Override
	public boolean delete(Run run) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM QuizRun WHERE QuizRun.iid = ?;");
		ps.setObject(1, run.getId());

		try {
			rs = ps.executeUpdate();
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

		ps = con.prepareStatement("SELECT * FROM QuizRun");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Run run = new Run();

				run.setId((UUID) rs.getObject("iid"));
				run.setInstructorId(rs.getInt("instructorId"));
				run.setQuizId((UUID) rs.getObject("quizId"));
				run.setStudentId(rs.getInt("studentid"));
				run.setScore(rs.getInt("score"));
				run.setStart(rs.getLong("start"));
				run.setEnd(rs.getLong("end"));
				run.setCourseId(rs.getInt("courseId"));

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

	public ArrayList<Run> list(int Courseid) throws SQLException {

		ArrayList<Run> allRuns = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizRun where courseid="
				+ Courseid);

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Run run = new Run();

				run.setId((UUID) rs.getObject("iid"));
				run.setCourseId(Courseid);
				run.setInstructorId(rs.getInt("instructorId"));
				run.setQuizId((UUID) rs.getObject("quizId"));
				run.setStudentId(rs.getInt("studentid"));
				run.setScore(rs.getInt("score"));
				run.setStart(rs.getLong("start"));
				run.setEnd(rs.getLong("end"));
				run.setCourseId(rs.getInt("courseId"));

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

		ps = con.prepareStatement("SELECT * FROM QuizRun WHERE QuizRun.iid = ?;");
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
