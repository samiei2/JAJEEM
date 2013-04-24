package com.jajeem.core.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.H2ConnectionImpl;

import com.jajeem.core.dao.IInstructorDAO;
import com.jajeem.core.model.*;

public class InstructorDAO implements IInstructorDAO {

	Logger logger = Logger.getLogger(InstructorDAO.class);

	public InstructorDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public boolean authenticate(String username, String password)
			throws SQLException {

		String query = String.format(
				"SELECT * FROM Instructor WHERE Instructor.username = '%s';",
				username);

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		ResultSet rs = null;

		// checking...
		if (username.length() == 0) {
			logger.error("authenticate: failed ... invalid username");
			return false;
		}

		if (password.length() == 0) {
			logger.error("authenticate: failed ... invalid password");
			return false;
		}

		try (Statement statement = con.createStatement()) {
			rs = statement.executeQuery(query);
			if (rs.next()) {
				if (password.compareTo(rs.getString("password")) == 0) {
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
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	@Override
	public Instructor create(Instructor instructor) throws SQLException {

		String query = String
				.format("INSERT INTO Instructor (firstName, middleName, lastName, username, password, language)"
						+ " VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
						instructor.getFirstName(), instructor.getMiddleName(),
						instructor.getLastName(), instructor.getUsername(),
						instructor.getPassword(), instructor.getLanguage());

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		int rs = 0;

		try (Statement statement = con.createStatement()) {
			rs = statement.executeUpdate(query);

			// get last id

			ResultSet maxId = null;
			maxId = statement.getGeneratedKeys();
			if (maxId.next()) {
				instructor.setId(maxId.getInt(1));
			} else {
				instructor.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					instructor.setId(-1);
				}
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}

		return instructor;
	}

	@Override
	public Instructor get(Instructor instructor) throws SQLException {
		String query = String.format(
				"SELECT * FROM Instructor WHERE Instructor.id = %d;",
				instructor.getId());

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		ResultSet rs = null;

		try (Statement statement = con.createStatement()) {
			rs = statement.executeQuery(query);
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
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}

		return instructor;
	}

	@Override
	public boolean update(Instructor instructor) throws SQLException {

		String query = String
				.format("UPDATE Instructor SET firstName='%s', middleName='%s', lastName='%s', "
						+ " username='%s', password='%s', language='%s' WHERE id = '%d'",
						instructor.getFirstName(), instructor.getMiddleName(),
						instructor.getLastName(), instructor.getUsername(),
						instructor.getPassword(), instructor.getLanguage(),
						instructor.getId());

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		int rs = 0;

		try (Statement statement = con.createStatement()) {
			rs = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
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
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean delete(Instructor instructor) throws SQLException {
		String query = String.format(
				"DELETE FROM Instructor WHERE Instructor.id = '%d';",
				instructor.getId());

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		int rs = 0;

		try (Statement statement = con.createStatement()) {
			rs = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
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
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}

		return false;
	}

	@Override
	public ArrayList<Instructor> list() throws SQLException {
		String query = "SELECT * FROM Instructor";
		ArrayList<Instructor> allInstructors = new ArrayList<>();

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		ResultSet rs = null;

		try (Statement statement = con.createStatement()) {
			rs = statement.executeQuery(query);
			while (rs.next()) {
				Instructor instructor = new Instructor();

				instructor.setId(rs.getInt("id"));
				instructor.setFirstName(rs.getString("firstName"));
				instructor.setMiddleName(rs.getString("middleName"));
				instructor.setLastName(rs.getString("lastName"));
				instructor.setUsername(rs.getString("username"));
				instructor.setPassword(rs.getString("password"));
				instructor.setLanguage(rs.getString("language"));
				logger.info(instructor.getUsername() + "   "
						+ instructor.getId());

				allInstructors.add(instructor);
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
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
		}

		return allInstructors;
	}

}
