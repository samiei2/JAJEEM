package com.jajeem.core.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.core.dao.IStudentCourseDAO;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.BaseDAO;

public class StudentCourseDAO implements IStudentCourseDAO {

	Logger logger = Logger.getLogger(StudentCourseDAO.class);

	public StudentCourseDAO() {

	}

	@Override
	public StudentCourse create(StudentCourse studentCourse)
			throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO StudentCourse (studentId, courseId, score) "
				+ " VALUES (?, ?, ?);");
		ps.setInt(1, studentCourse.getStudentId());
		ps.setInt(2, studentCourse.getCourseId());
		ps.setInt(3, studentCourse.getScore());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				studentCourse.setId(maxId.getInt(1));
			} else {
				studentCourse.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			studentCourse.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					studentCourse.setId(-1);
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

		return studentCourse;
	}

	@Override
	public StudentCourse get(StudentCourse studentCourse) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM StudentCourse WHERE StudentCourse.id = ?;");
		ps.setInt(1, studentCourse.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				studentCourse.setStudentId(rs.getInt("studentId"));
				studentCourse.setCourseId(rs.getInt("courseId"));
				studentCourse.setScore(rs.getInt("score"));
			} else {
				studentCourse.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			studentCourse.setId(-1);
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

		return studentCourse;
	}

	@Override
	public boolean update(StudentCourse studentCourse) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE StudentCourse SET studentId=?, courseId=?, score=? WHERE id = ?");

		ps.setInt(1, studentCourse.getStudentId());
		ps.setInt(2, studentCourse.getCourseId());
		ps.setInt(3, studentCourse.getScore());
		ps.setInt(4, studentCourse.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			studentCourse.setId(-1);
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
	public boolean delete(StudentCourse studentCourse) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM StudentCourse WHERE StudentCourse.id = ?;");
		ps.setInt(1, studentCourse.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			studentCourse.setId(-1);
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
	
	public boolean delete(int studentId, int courseId) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM StudentCourse WHERE StudentCourse.studentId = ? AND StudentCourse.courseId=?;");
		ps.setInt(1, studentId);
		ps.setInt(2, courseId);

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public ArrayList<StudentCourse> list() throws SQLException {

		ArrayList<StudentCourse> allStudentCourses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM StudentCourse");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				StudentCourse studentCourse = new StudentCourse();

				studentCourse.setId(rs.getInt("id"));
				studentCourse.setStudentId(rs.getInt("studentId"));
				studentCourse.setCourseId(rs.getInt("courseId"));
				studentCourse.setScore(rs.getInt("score"));
				allStudentCourses.add(studentCourse);
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

		return allStudentCourses;
	}

	public ArrayList<Student> getCourseStudentsById(int courseId)
			throws SQLException {

		ArrayList<Student> allStudentCourses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM STUDENT S JOIN STUDENTCOURSE SC ON (S.ID=SC.STUDENTID) WHERE SC.COURSEID=?");
		ps.setInt(1, courseId);

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
				allStudentCourses.add(student);
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

		return allStudentCourses;
	}

}
