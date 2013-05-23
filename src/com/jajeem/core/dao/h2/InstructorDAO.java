package com.jajeem.core.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.command.service.ServerService;
import com.jajeem.core.dao.IInstructorDAO;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.BaseDAO;

public class InstructorDAO implements IInstructorDAO {

	Logger logger = Logger.getLogger(InstructorDAO.class);

	public InstructorDAO() {
		PropertyConfigurator.configure(InstructorDAO.class.getResource("/conf/log4j.conf").getPath());
	}

	@Override
	public boolean authenticate(String username, char[] pass) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String password = new String(pass);

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Instructor WHERE Instructor.username = ?;");
		ps.setString(1, username);

		// checking...
		if (username.length() == 0) {
			logger.error("authenticate: failed ... invalid username");
			return false;
		}

		if (password.length() == 0) {
			logger.error("authenticate: failed ... invalid password");
			return false;
		}

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				if (password.compareTo(rs.getString("PASSWORD")) == 0) {
					return true;
				} else {
					logger.debug("authenticate: didn't match instructor's password [instructor no="
							+ username + "]");
					return false;
				}
			} else {
				logger.debug("authenticate: didn't match instructor no ... [instructor no="
						+ username + "]");
				return false;
			}
		} catch (SQLException e) {
			logger.error("authenticate: throwed an exception: "
					+ e.getMessage());
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
		return false;
	}

	@Override
	public Instructor create(Instructor instructor) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Instructor (firstName, middleName, lastName, username, password, language) "
				+ " VALUES (?, ?, ?, ?, ?, ?);");
		ps.setString(1, instructor.getFirstName());
		ps.setString(2, instructor.getMiddleName());
		ps.setString(3, instructor.getLastName());
		ps.setString(4, instructor.getUsername());
		ps.setString(5, instructor.getPassword());
		ps.setString(6, instructor.getLanguage());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				instructor.setId(maxId.getInt(1));
			} else {
				instructor.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					instructor.setId(-1);
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

		return instructor;
	}

	@Override
	public Instructor get(Instructor instructor) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Instructor WHERE Instructor.id = ?;");
		ps.setInt(1, instructor.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				instructor.setFirstName(rs.getString("firstName"));
				instructor.setMiddleName(rs.getString("middleName"));
				instructor.setLastName(rs.getString("lastName"));
				instructor.setUsername(rs.getString("username"));
				instructor.setPassword(rs.getString("password"));
				instructor.setLanguage(rs.getString("language"));
			} else {
				instructor.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
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

		return instructor;
	}

	@Override
	public boolean update(Instructor instructor) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Instructor SET firstName=?, middleName=?, lastName=?, username=?, password=?, language=? WHERE id = ?");

		ps.setString(1, instructor.getFirstName());
		ps.setString(2, instructor.getMiddleName());
		ps.setString(3, instructor.getLastName());
		ps.setString(4, instructor.getUsername());
		ps.setString(5, instructor.getPassword());
		ps.setString(6, instructor.getLanguage());
		ps.setInt(7, instructor.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
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
	public boolean delete(Instructor instructor) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Instructor WHERE Instructor.id = ?;");
		ps.setInt(1, instructor.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
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
	public ArrayList<Instructor> list() throws SQLException {

		ArrayList<Instructor> allInstructors = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Instructor");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Instructor instructor = new Instructor();

				instructor.setId(rs.getInt("id"));
				instructor.setFirstName(rs.getString("firstName"));
				instructor.setMiddleName(rs.getString("middleName"));
				instructor.setLastName(rs.getString("lastName"));
				instructor.setUsername(rs.getString("username"));
				instructor.setPassword(rs.getString("password"));
				instructor.setLanguage(rs.getString("language"));

				allInstructors.add(instructor);
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

		return allInstructors;
	}
}
