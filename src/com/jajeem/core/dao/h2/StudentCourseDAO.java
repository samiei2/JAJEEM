package com.jajeem.core.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.core.dao.IStudentCourseDAO;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.core.service.InstructorService;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.model.Run;
import com.jajeem.room.model.Course;
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
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					studentCourse.setId(-1);
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

		return allStudentCourses;
	}

	public ArrayList<Course> getStudentCoursesById(int studentId)
			throws SQLException {

		ArrayList<Course> allCourses = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM COURSE C JOIN STUDENTCOURSE SC ON (C.ID=SC.COURSEID) WHERE SC.STUDENTID=?");
		ps.setInt(1, studentId);

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

	public ArrayList<Run> getStudentQuizesById(int id, int courseId) throws SQLException {
		ArrayList<Run> allQuizes = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		InstructorDAO instructordao = new InstructorDAO();
		

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM QUIZRUN");
//		ps.setInt(1, id);
//		ps.setInt(2, courseId);

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Run quizRun = new Run();

				int insId = rs.getInt("instructorId");
				quizRun.setInstructorId(insId);
				Instructor instructor = instructordao.getById(courseId);
				quizRun.setInstructor(instructor);
				quizRun.setInstructorId(insId);
				quizRun.setStart(rs.getLong("start"));
				quizRun.setEnd(rs.getLong("end"));
				quizRun.setStart(rs.getLong("score"));

				allQuizes.add(quizRun);
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

		return allQuizes;
	}
}
