package com.jajeem.quiz.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.dao.IResponseDAO;
import com.jajeem.quiz.model.Response;
import com.jajeem.util.BaseDAO;

public class ResponseDAO implements IResponseDAO {

	Logger logger = Logger.getLogger(ResponseDAO.class);

	public ResponseDAO() {

	}

	@SuppressWarnings("unused")
	@Override
	public Response create(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO QuizResponse (runId, studentId, answer, bool1, bool2, bool3, bool4, bool5,iid, answerValid, quizQuestionId) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);");
		ps.setObject(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer());
		ps.setString(4, String.valueOf(response.getBoolAnswer()[0]));
		ps.setString(5, String.valueOf(response.getBoolAnswer()[1]));
		ps.setString(6, String.valueOf(response.getBoolAnswer()[2]));
		ps.setString(7, String.valueOf(response.getBoolAnswer()[3]));
		ps.setString(8, String.valueOf(response.getBoolAnswer()[4]));
		ps.setObject(9, response.getId());
		ps.setBoolean(10, response.isAnswerValid());
		ps.setObject(11, response.getQuizQuestionId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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

		return response;
	}

	@Override
	public Response get(Response response) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizResponse WHERE QuizResponse.iid = ?;");
		ps.setObject(1, response.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				response.setRunId((UUID) rs.getObject("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer(rs.getString("answer"));
				boolean[] list = new boolean[] {
						(rs.getString("bool1") != null && rs.getString("bool1") == "true") ? true
								: false,
						(rs.getString("bool2") != null && rs.getString("bool2") == "true") ? true
								: false,
						(rs.getString("bool3") != null && rs.getString("bool3") == "true") ? true
								: false,
						(rs.getString("bool4") != null && rs.getString("bool4") == "true") ? true
								: false,
						(rs.getString("bool5") != null && rs.getString("bool5") == "true") ? true
								: false };
				response.setBoolAnswer(list);
				response.setAnswerValid(rs.getBoolean("answerValid"));

			} else {
				response.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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

		return response;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean update(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE QuizResponse SET runId = ?, studentId = ?, "
				+ "answer = ?, bool1 = ?, bool2 = ?, bool3 = ?, bool4 = ?, bool5 = ?, answerValid=? WHERE iid = ?");

		ps.setObject(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer());
		ps.setString(4, String.valueOf(response.getBoolAnswer()[0]));
		ps.setString(5, String.valueOf(response.getBoolAnswer()[1]));
		ps.setString(6, String.valueOf(response.getBoolAnswer()[2]));
		ps.setString(7, String.valueOf(response.getBoolAnswer()[3]));
		ps.setString(8, String.valueOf(response.getBoolAnswer()[4]));
		ps.setBoolean(9, response.isAnswerValid());
		ps.setObject(10, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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
	public boolean delete(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM QuizResponse WHERE QuizResponse.iid = ?;");
		ps.setObject(1, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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
	public ArrayList<Response> list() throws SQLException {

		ArrayList<Response> allResponses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizResponse");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Response response = new Response();

				response.setId((UUID) rs.getObject("iid"));
				response.setRunId((UUID) rs.getObject("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer(rs.getString("answer"));
				boolean[] list = new boolean[] {
						(rs.getString("bool1") != null && rs.getString("bool1") == "true") ? true
								: false,
						(rs.getString("bool2") != null && rs.getString("bool2") == "true") ? true
								: false,
						(rs.getString("bool3") != null && rs.getString("bool3") == "true") ? true
								: false,
						(rs.getString("bool4") != null && rs.getString("bool4") == "true") ? true
								: false,
						(rs.getString("bool5") != null && rs.getString("bool5") == "true") ? true
								: false };
				response.setBoolAnswer(list);
				response.setAnswerValid(rs.getBoolean("answerValid"));

				allResponses.add(response);
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

		return allResponses;
	}

	public boolean Contains(Response response) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QuizResponse WHERE QuizResponse.iid = ?;");
		ps.setObject(1, response.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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
