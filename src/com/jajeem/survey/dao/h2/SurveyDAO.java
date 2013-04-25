package com.jajeem.survey.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.util.H2Connection;

import com.jajeem.survey.dao.ISurveyDAO;
import com.jajeem.survey.model.Survey;

public class SurveyDAO implements ISurveyDAO {

	Logger logger = Logger.getLogger(SurveyDAO.class);

	public SurveyDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Survey create(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Survey (instructorId, title, category, description) "
				+ " VALUES (?, ?, ?, ?);");
		ps.setInt(1, survey.getInstructorId());
		ps.setString(2, survey.getTitle());
		ps.setString(3, survey.getCategory());
		ps.setString(4, survey.getDescription());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				survey.setId(maxId.getInt(1));
			} else {
				survey.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					survey.setId(-1);
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

		return survey;
	}

	@Override
	public Survey get(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Survey WHERE Survey.id = ?;");
		ps.setInt(1, survey.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				survey.setInstructorId(rs.getInt("instructorId"));
				survey.setTitle(rs.getString("title"));
				survey.setCategory(rs.getString("category"));
				survey.setDescription(rs.getString("description"));

			} else {
				survey.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(-1);
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

		return survey;
	}

	@Override
	public boolean update(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Survey SET instructorId = ?, title = ?, category = ?, description = ? WHERE id = ?");

		ps.setInt(1, survey.getInstructorId());
		ps.setString(2, survey.getTitle());
		ps.setString(3, survey.getCategory());
		ps.setString(4, survey.getDescription());
		ps.setInt(5, survey.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(-1);
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
	public boolean delete(Survey survey) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Survey WHERE Survey.id = ?;");
		ps.setInt(1, survey.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			survey.setId(-1);
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
	public ArrayList<Survey> list() throws SQLException {

		ArrayList<Survey> allSurveys = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Survey");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Survey survey = new Survey();

				survey.setId(rs.getInt("id"));
				survey.setInstructorId(rs.getInt("instructorId"));
				survey.setTitle(rs.getString("title"));
				survey.setCategory(rs.getString("category"));
				survey.setDescription(rs.getString("description"));

				allSurveys.add(survey);
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

		return allSurveys;
	}

}
