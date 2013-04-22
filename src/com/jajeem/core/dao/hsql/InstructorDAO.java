package com.jajeem.core.dao.hsql;

import java.sql.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.HSQLDBConnectionImpl;

import com.jajeem.core.dao.IInstructorDAO;
import com.jajeem.core.model.*;

public class InstructorDAO implements IInstructorDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(InstructorDAO.class);

	public InstructorDAO () {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Instructor authenticate(String username, String password) {

		Instructor instructor = new Instructor();

		// checking...
		if (username.length() == 0) {
			logger.error("authenticate: failed ... invalid username");
			return instructor;
		}

		if (password.length() == 0) {
			logger.error("authenticate: failed ... invalid password");
			return instructor;
		}

		// create connection
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// get connection
			con = conn.getConnection();
			ps = con.prepareStatement("SELECT id FROM Instructor WHERE username=:1");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(2).compareTo(rs.getString(3)) == 0) {
					logger.debug("authenticate: matched instructor's password [instructor no="
							+ username + "]");
					instructor.setId(rs.getInt(1));
				} else {
					logger.debug("authenticate: didn't match instructor's password [instructor no="
							+ username + "]");
					instructor.setId(0);
				}
			} else {
				logger.debug("authenticate: didn't match instructor no ... [instructor no="
						+ username + "]");
			}
		} catch (SQLException e) {
			logger.error("authenticate: throwed an exception: "
					+ e.getMessage());
			e.printStackTrace();
			instructor.setId(-1);
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
		return instructor;
	}

	@Override
	public Instructor create(Instructor instructor) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO Instructor VALUES( firstName=:1, middleName=:1, lastName=:1, "
					+ "username=:1, password=:1, langauge=:1");
			ps.setString(1, instructor.getFirstName());
			ps.setString(2, instructor.getMiddleName());
			ps.setString(3, instructor.getLastName());
			ps.setString(4, instructor.getUsername());
			ps.setString(5, instructor.getPassword());
			ps.setString(6, instructor.getLangauge());

			rs = ps.executeQuery();

			if (rs.next()) {
				instructor.setId(rs.getInt(1));
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

		return instructor;
	}

	@Override
	public boolean update(Instructor instructor) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE Instructor SET firstName=':1', middleName=':1', lastName=':1', "
					+ "username=':1', password=':1', langauge=':1' WHERE id=:1 ");
			ps.setString(1, instructor.getFirstName());
			ps.setString(2, instructor.getMiddleName());
			ps.setString(3, instructor.getLastName());
			ps.setString(4, instructor.getUsername());
			ps.setString(5, instructor.getPassword());
			ps.setString(6, instructor.getLangauge());
			ps.setInt(7, instructor.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
		} finally {
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
	public boolean delete(Instructor instructor) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM Instructor WHERE id=:1 ");
			ps.setInt(1, instructor.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			instructor.setId(-1);
		} finally {
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
	public Instructor get(Instructor instructor) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, firstName, middleName, lastName, username, password, langauge"
					+ " FROM Instructor WHERE id=:1 ");
			ps.setInt(1, instructor.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				instructor.setId(rs.getInt(1));
				instructor.setFirstName(rs.getString(2));
				instructor.setMiddleName(rs.getString(3));
				instructor.setLastName(rs.getString(4));
				instructor.setUsername(rs.getString(5));
				instructor.setPassword(rs.getString(6));
				instructor.setLangauge(rs.getString(7));
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

		return instructor;
	}

	@Override
	public void list() {
		// TODO Auto-generated method stub

	}

}
