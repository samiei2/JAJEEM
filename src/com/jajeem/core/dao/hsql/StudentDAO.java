package com.jajeem.core.dao.hsql;

import java.sql.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.HSQLDBConnectionImpl;

import com.jajeem.core.dao.IStudentDAO;
import com.jajeem.core.model.*;

public class StudentDAO implements IStudentDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(StudentDAO.class);

	public StudentDAO () {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Student authenticate(String username, String password) {

		Student student = new Student();

		// checking...
		if (username.length() == 0) {
			logger.error("authenticate: failed ... invalid username");
			return student;
		}

		if (password.length() == 0) {
			logger.error("authenticate: failed ... invalid password");
			return student;
		}

		// create connection
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// get connection
			con = conn.getConnection();
			ps = con.prepareStatement("SELECT id FROM Student WHERE username=:1");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString(2).compareTo(rs.getString(3)) == 0) {
					logger.debug("authenticate: matched student's password [student no="
							+ username + "]");
					student.setId(rs.getInt(1));
				} else {
					logger.debug("authenticate: didn't match student's password [student no="
							+ username + "]");
					student.setId(0);
				}
			} else {
				logger.debug("authenticate: didn't match student no ... [student no="
						+ username + "]");
			}
		} catch (SQLException e) {
			logger.error("authenticate: throwed an exception: "
					+ e.getMessage());
			e.printStackTrace();
			student.setId(-1);
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
		return student;
	}

	@Override
	public Student create(Student student) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO Student VALUES( firstName=:1, middleName=:1, lastName=:1, "
					+ "username=:1, password=:1, langauge=:1");
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getMiddleName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getUsername());
			ps.setString(5, student.getPassword());
			ps.setString(6, student.getLangauge());

			rs = ps.executeQuery();

			if (rs.next()) {
				student.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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

		return student;
	}

	@Override
	public boolean update(Student student) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE Student SET firstName=':1', middleName=':1', lastName=':1', "
					+ "username=':1', password=':1', langauge=':1' WHERE id=:1 ");
			ps.setString(1, student.getFirstName());
			ps.setString(2, student.getMiddleName());
			ps.setString(3, student.getLastName());
			ps.setString(4, student.getUsername());
			ps.setString(5, student.getPassword());
			ps.setString(6, student.getLangauge());
			ps.setInt(7, student.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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
	public boolean delete(Student student) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM Student WHERE id=:1 ");
			ps.setInt(1, student.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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
	public Student get(Student student) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, firstName, middleName, lastName, username, password, langauge"
					+ " FROM Student WHERE id=:1 ");
			ps.setInt(1, student.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				student.setId(rs.getInt(1));
				student.setFirstName(rs.getString(2));
				student.setMiddleName(rs.getString(3));
				student.setLastName(rs.getString(4));
				student.setUsername(rs.getString(5));
				student.setPassword(rs.getString(6));
				student.setLangauge(rs.getString(7));
			} else {
				student.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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

		return student;
	}

	@Override
	public void list() {
		// TODO Auto-generated method stub

	}

}
