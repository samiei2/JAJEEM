package com.jajeem.survey.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.survey.dao.IResponseDAO;
import com.jajeem.survey.model.Response;
import com.jajeem.util.BaseDAO;

public class ResponseDAO implements IResponseDAO {

	Logger logger = Logger.getLogger(ResponseDAO.class);

	public ResponseDAO() {
		
	}

	@Override
	public Response create(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO SurveyResponse (runId, studentId, answer, bool1, bool2, bool3, bool4, bool5,id) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
		ps.setObject(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer());
		ps.setString(4, String.valueOf(response.getBoolAnswer()[0]));
		ps.setString(5, String.valueOf(response.getBoolAnswer()[1]));
		ps.setString(6, String.valueOf(response.getBoolAnswer()[2]));
		ps.setString(7, String.valueOf(response.getBoolAnswer()[3]));
		ps.setString(8, String.valueOf(response.getBoolAnswer()[4]));
		ps.setObject(9, response.getId());
		
		try {
			rs = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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

		return response;
	}

	@Override
	public Response get(Response response) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyResponse WHERE SurveyResponse.id = ?;");
		ps.setObject(1, response.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				response.setRunId((UUID) rs.getObject("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer(rs.getString("answer"));
				boolean[] list = new boolean[]{
						(rs.getString("bool1") != null && rs.getString("bool1") == "true")? true : false,
						(rs.getString("bool2") != null && rs.getString("bool2") == "true")? true : false,
						(rs.getString("bool3") != null && rs.getString("bool3") == "true")? true : false,
						(rs.getString("bool4") != null && rs.getString("bool4") == "true")? true : false,
						(rs.getString("bool5") != null && rs.getString("bool5") == "true")? true : false
				};
				response.setBoolAnswer(list);

			} else {
				response.setId(null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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

		return response;
	}

	@Override
	public boolean update(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE SurveyResponse SET runId = ?, studentId = ?, " +
				"answer = ?, bool1 = ?, bool2 = ?, bool3 = ?, bool4 = ?, bool5 = ? WHERE id = ?");

		ps.setObject(1, response.getRunId());
		ps.setInt(2, response.getStudentId());
		ps.setString(3, response.getAnswer());
		ps.setString(4, String.valueOf(response.getBoolAnswer()[0]));
		ps.setString(5, String.valueOf(response.getBoolAnswer()[1]));
		ps.setString(6, String.valueOf(response.getBoolAnswer()[2]));
		ps.setString(7, String.valueOf(response.getBoolAnswer()[3]));
		ps.setString(8, String.valueOf(response.getBoolAnswer()[4]));
		ps.setObject(9, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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
	public boolean delete(Response response) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM SurveyResponse WHERE SurveyResponse.id = ?;");
		ps.setObject(1, response.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			response.setId(null);
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
	public ArrayList<Response> list() throws SQLException {

		ArrayList<Response> allResponses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM SurveyResponse");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Response response = new Response();

				response.setId((UUID) rs.getObject("id"));
				response.setRunId((UUID) rs.getObject("runId"));
				response.setStudentId(rs.getInt("studentId"));
				response.setAnswer(rs.getString("answer"));
				boolean[] list = new boolean[]{
						(rs.getString("bool1") != null && rs.getString("bool1") == "true")? true : false,
						(rs.getString("bool2") != null && rs.getString("bool2") == "true")? true : false,
						(rs.getString("bool3") != null && rs.getString("bool3") == "true")? true : false,
						(rs.getString("bool4") != null && rs.getString("bool4") == "true")? true : false,
						(rs.getString("bool5") != null && rs.getString("bool5") == "true")? true : false
				};
				response.setBoolAnswer(list);

				allResponses.add(response);
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

		return allResponses;
	}

}
