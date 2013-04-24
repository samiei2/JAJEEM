package com.jajeem.survey.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.H2ConnectionImpl;

import com.jajeem.survey.dao.IResponseDAO;
import com.jajeem.survey.model.Response;

public class ResponseDAO implements IResponseDAO {

	Logger logger = Logger.getLogger(ResponseDAO.class);

	public ResponseDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Response create(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("INSERT INTO SurveyResponse (runId, studentId, answer1, answer2, answer3, answer4, answer5) "
				+ " VALUES (?, ?, ?, ?, ? , ?, ?);");
		ps.setInt(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer1());
		ps.setString(4, response.getAnswer2());
		ps.setString(5, response.getAnswer3());
		ps.setString(6, response.getAnswer4());
		ps.setString(7, response.getAnswer5());
		
		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				response.setId(maxId.getInt(1));
			} else {
				response.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					response.setId(-1);
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return response;
	}

	@Override
	public Response get(Response response) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyResponse WHERE SurveyResponse.id = ?;");
		ps.setInt(1, response.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				response.setRunId(rs.getInt("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer1(rs.getString("answer1"));
				response.setAnswer2(rs.getString("answer2"));
				response.setAnswer3(rs.getString("answer3"));
				response.setAnswer4(rs.getString("answer4"));
				response.setAnswer5(rs.getString("answer5"));

			} else {
				response.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(-1);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return response;
	}

	@Override
	public boolean update(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("UPDATE SurveyResponse SET runId = ?, studentId = ?, " +
				"answer1 = ?, answer2 = ?, answer3 = ?, answer4 = ?, answer5 = ? WHERE id = ?");

		ps.setInt(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer1());
		ps.setString(4, response.getAnswer2());
		ps.setString(5, response.getAnswer3());
		ps.setString(6, response.getAnswer4());
		ps.setString(7, response.getAnswer5());
		ps.setInt(8, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(-1);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	@Override
	public boolean delete(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("DELETE FROM SurveyResponse WHERE SurveyResponse.id = ?;");
		ps.setInt(1, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(-1);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	@Override
	public ArrayList<Response> list() throws SQLException {

		ArrayList<Response> allResponses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyResponse");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Response response = new Response();

				response.setId(rs.getInt("id"));
				response.setRunId(rs.getInt("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer1(rs.getString("answer1"));
				response.setAnswer2(rs.getString("answer2"));
				response.setAnswer3(rs.getString("answer3"));
				response.setAnswer4(rs.getString("answer4"));
				response.setAnswer5(rs.getString("answer5"));

				allResponses.add(response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return allResponses;
	}

}
