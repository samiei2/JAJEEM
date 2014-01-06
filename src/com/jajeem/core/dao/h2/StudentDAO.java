package com.jajeem.core.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.core.dao.IStudentDAO;
import com.jajeem.core.model.Student;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.util.BaseDAO;

public class StudentDAO implements IStudentDAO {

	Logger logger = Logger.getLogger(StudentDAO.class);

	public StudentDAO() {

	}

	@Override
	public boolean authenticate(String username, char[] pass)
			throws SQLException {

		String password = new String(pass);

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Student WHERE Student.username = ?;");
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
					logger.debug("authenticate: didn't match student's password [student no="
							+ username + "]");
					return false;
				}
			} else {
				logger.debug("authenticate: didn't match student no ... [student no="
						+ username + "]");
				return false;
			}
		} catch (SQLException e) {
			logger.error("authenticate: throwed an exception: "
					+ e.getMessage());
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
		return false;
	}

	@Override
	public Student create(Student student) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Student (firstName, middleName, lastName, username, password, language) "
				+ " VALUES (?, ?, ?, ?, ?, ?);");
		ps.setString(1, student.getFirstName());
		ps.setString(2, student.getMiddleName());
		ps.setString(3, student.getLastName());
		ps.setString(4, student.getUsername());
		ps.setString(5, student.getPassword());
		ps.setString(6, student.getLanguage());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				student.setId(maxId.getInt(1));
			} else {
				student.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					student.setId(-1);
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

		return student;
	}

	@Override
	public Student get(Student student) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Student WHERE Student.id = ?;");
		ps.setInt(1, student.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				student.setFirstName(rs.getString("firstName"));
				student.setMiddleName(rs.getString("middleName"));
				student.setLastName(rs.getString("lastName"));
				student.setUsername(rs.getString("username"));
				student.setPassword(rs.getString("password"));
				student.setLanguage(rs.getString("language"));
			} else {
				student.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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

		return student;
	}

	public Student get(String username) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Student student = new Student();

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Student WHERE Student.username = ?;");
		ps.setString(1, username);

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("firstName"));
				student.setMiddleName(rs.getString("middleName"));
				student.setLastName(rs.getString("lastName"));
				student.setUsername(rs.getString("username"));
				student.setPassword(rs.getString("password"));
				student.setLanguage(rs.getString("language"));
			} else {
				student.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
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

		return student;
	}

	@Override
	public boolean update(Student student) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Student SET firstName=?, middleName=?, lastName=?, username=?, password=?, language=? WHERE id = ?");

		ps.setString(1, student.getFirstName());
		ps.setString(2, student.getMiddleName());
		ps.setString(3, student.getLastName());
		ps.setString(4, student.getUsername());
		ps.setString(5, student.getPassword());
		ps.setString(6, student.getLanguage());
		ps.setInt(7, student.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
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

	@Override
	public boolean delete(Student student) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Student WHERE Student.id = ?;");
		ps.setInt(1, student.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			student.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
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

	@Override
	public ArrayList<Student> list() throws SQLException {

		ArrayList<Student> allStudents = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Student");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Student student = new Student();

				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("firstName"));
				student.setMiddleName(rs.getString("middleName"));
				student.setLastName(rs.getString("lastName"));
				student.setUsername(rs.getString("username"));
				student.setPassword(rs.getString("password"));
				student.setLanguage(rs.getString("language"));

				allStudents.add(student);
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

		return allStudents;
	}

}
