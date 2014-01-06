package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.dao.ICourseDAO;
import com.jajeem.room.model.Course;
import com.jajeem.util.BaseDAO;

public class CourseDAO implements ICourseDAO {

	Logger logger = Logger.getLogger(CourseDAO.class);

	public CourseDAO() {

	}

	@Override
	public Course create(Course course) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Course (instructorId, name , classType, level, session, startDate,"
				+ " day1, startTime1, endTime1,day2, startTime2, endTime2,day3, startTime3, endTime3,day4, startTime4, endTime4,day5, startTime5, endTime5) "
				+ " VALUES (?, ?, ?, ?, ? , ?, ?, ?, ? , ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?);");
		ps.setInt(1, course.getInstructorId());
		ps.setString(2, course.getName());
		ps.setString(3, course.getClassType());
		ps.setString(4, course.getLevel());
		ps.setInt(5, course.getSession());
		ps.setLong(6, course.getStartDate());
		ps.setString(7, course.getDay1());
		ps.setInt(8, course.getStartTime1());
		ps.setInt(9, course.getEndTime1());
		ps.setString(10, course.getDay2());
		ps.setInt(11, course.getStartTime2());
		ps.setInt(12, course.getEndTime2());
		ps.setString(13, course.getDay3());
		ps.setInt(14, course.getStartTime3());
		ps.setInt(15, course.getEndTime3());
		ps.setString(16, course.getDay4());
		ps.setInt(17, course.getStartTime4());
		ps.setInt(18, course.getEndTime4());
		ps.setString(19, course.getDay5());
		ps.setInt(20, course.getStartTime5());
		ps.setInt(21, course.getEndTime5());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				course.setId(maxId.getInt(1));
			} else {
				course.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			course.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					course.setId(-1);
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

		return course;
	}

	@Override
	public Course get(Course course) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Course WHERE Course.id = ?;");
		ps.setInt(1, course.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {

				course.setName(rs.getString("name"));
				course.setInstructorId(rs.getInt("instructorId"));
				course.setClassType(rs.getString("classType"));
				course.setLevel(rs.getString("level"));
				course.setStartDate(rs.getLong("startDate"));
				course.setSession(rs.getInt("session"));
				course.setDay1(rs.getString("day1"));
				course.setStartTime1(rs.getInt("startTime1"));
				course.setEndTime1(rs.getInt("endTime1"));
				course.setDay2(rs.getString("day2"));
				course.setStartTime2(rs.getInt("startTime2"));
				course.setEndTime2(rs.getInt("endTime2"));
				course.setDay3(rs.getString("day3"));
				course.setStartTime3(rs.getInt("startTime3"));
				course.setEndTime3(rs.getInt("endTime3"));
				course.setDay4(rs.getString("day4"));
				course.setStartTime4(rs.getInt("startTime4"));
				course.setEndTime4(rs.getInt("endTime4"));
				course.setDay5(rs.getString("day5"));
				course.setStartTime5(rs.getInt("startTime5"));
				course.setEndTime5(rs.getInt("endTime5"));
			} else {
				course.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			course.setId(-1);
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

		return course;
	}

	@Override
	public boolean update(Course course) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Course SET instructorId=?, name=? , classType=?, level=?, session=?, startDate=?,"
				+ " day1=?, startTime1=?, endTime1=?,day2=?, startTime2=?, endTime2=?,day3=?, startTime3=?, endTime3=?,day4=?, startTime4=?, endTime4=?,day5=?, startTime5=?, endTime5=? "
				+ " WHERE id=?");

		ps.setInt(1, course.getInstructorId());
		ps.setString(2, course.getName());
		ps.setString(3, course.getClassType());
		ps.setString(4, course.getLevel());
		ps.setInt(5, course.getSession());
		ps.setLong(6, course.getStartDate());
		ps.setString(7, course.getDay1());
		ps.setInt(8, course.getStartTime1());
		ps.setInt(9, course.getEndTime1());
		ps.setString(10, course.getDay2());
		ps.setInt(11, course.getStartTime2());
		ps.setInt(12, course.getEndTime2());
		ps.setString(13, course.getDay3());
		ps.setInt(14, course.getStartTime3());
		ps.setInt(15, course.getEndTime3());
		ps.setString(16, course.getDay4());
		ps.setInt(17, course.getStartTime4());
		ps.setInt(18, course.getEndTime4());
		ps.setString(19, course.getDay5());
		ps.setInt(20, course.getStartTime5());
		ps.setInt(21, course.getEndTime5());
		ps.setInt(22, course.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			course.setId(-1);
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
	public boolean delete(Course course) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Course WHERE Course.id = ?;");
		ps.setInt(1, course.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			course.setId(-1);
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
	public ArrayList<Course> list() throws SQLException {

		ArrayList<Course> allCourses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Course");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course();

				course.setId(rs.getInt("id"));
				course.setInstructorId(rs.getInt("instructorId"));
				course.setName(rs.getString("name"));
				course.setClassType(rs.getString("classType"));
				course.setLevel(rs.getString("level"));
				course.setStartDate(rs.getLong("startDate"));
				course.setSession(rs.getInt("session"));
				course.setDay1(rs.getString("day1"));
				course.setStartTime1(rs.getInt("startTime1"));
				course.setEndTime1(rs.getInt("endTime1"));
				course.setDay2(rs.getString("day2"));
				course.setStartTime2(rs.getInt("startTime2"));
				course.setEndTime2(rs.getInt("endTime2"));
				course.setDay3(rs.getString("day3"));
				course.setStartTime3(rs.getInt("startTime3"));
				course.setEndTime3(rs.getInt("endTime3"));
				course.setDay4(rs.getString("day4"));
				course.setStartTime4(rs.getInt("startTime4"));
				course.setEndTime4(rs.getInt("endTime4"));
				course.setDay5(rs.getString("day5"));
				course.setStartTime5(rs.getInt("startTime5"));
				course.setEndTime5(rs.getInt("endTime5"));

				allCourses.add(course);
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

		return allCourses;
	}

	public ArrayList<Course> getCoursesByInstructorId(int instructorId)
			throws SQLException {

		ArrayList<Course> allCourses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Course WHERE Course.instructorId=?");
		ps.setInt(1, instructorId);

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Course course = new Course();

				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setClassType(rs.getString("classType"));
				course.setLevel(rs.getString("level"));
				course.setStartDate(rs.getLong("startDate"));
				course.setSession(rs.getInt("session"));
				course.setDay1(rs.getString("day1"));
				course.setStartTime1(rs.getInt("startTime1"));
				course.setEndTime1(rs.getInt("endTime1"));
				course.setDay2(rs.getString("day2"));
				course.setStartTime2(rs.getInt("startTime2"));
				course.setEndTime2(rs.getInt("endTime2"));
				course.setDay3(rs.getString("day3"));
				course.setStartTime3(rs.getInt("startTime3"));
				course.setEndTime3(rs.getInt("endTime3"));
				course.setDay4(rs.getString("day4"));
				course.setStartTime4(rs.getInt("startTime4"));
				course.setEndTime4(rs.getInt("endTime4"));
				course.setDay5(rs.getString("day5"));
				course.setStartTime5(rs.getInt("startTime5"));
				course.setEndTime5(rs.getInt("endTime5"));

				allCourses.add(course);
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

		return allCourses;
	}

}
