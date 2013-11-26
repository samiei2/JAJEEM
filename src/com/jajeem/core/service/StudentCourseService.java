package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.dao.h2.StudentCourseDAO;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.room.model.Course;

public class StudentCourseService implements IStudentCourseService {
	private StudentCourseDAO studentCourseDAO = new StudentCourseDAO();

	@Override
	public StudentCourse get(StudentCourse studentCourse) throws SQLException {
		if (studentCourseDAO != null) {
			return studentCourseDAO.get(studentCourse);
		}
		return null;
	}

	@Override
	public StudentCourse create(StudentCourse studentCourse)
			throws SQLException {
		if (studentCourseDAO != null) {
			studentCourseDAO.create(studentCourse);
		}
		return null;
	}

	@Override
	public boolean update(StudentCourse studentCourse) throws SQLException {
		if (studentCourseDAO != null) {
			return studentCourseDAO.update(studentCourse);
		}
		return false;
	}

	@Override
	public boolean delete(StudentCourse studentCourse) throws SQLException {
		if (studentCourseDAO != null) {
			studentCourseDAO.delete(studentCourse);
		}
		return false;
	}

	public boolean delete(int studentId, int courseId) throws SQLException {
		if (studentCourseDAO != null) {
			studentCourseDAO.delete(studentId, courseId);
		}
		return false;
	}

	@Override
	public ArrayList<StudentCourse> list() throws SQLException {
		if (studentCourseDAO != null) {
			return studentCourseDAO.list();
		}
		return null;
	}

	public ArrayList<Student> getcourseStudentsById(int courseId)
			throws SQLException {
		if (studentCourseDAO != null) {
			return studentCourseDAO.getCourseStudentsById(courseId);
		}
		return null;
	}

	public ArrayList<Course> getStudentCoursesById(int studentId)
			throws SQLException {
		if (studentCourseDAO != null) {
			return studentCourseDAO.getStudentCoursesById(studentId);
		}
		return null;
	}

	public StudentCourseDAO getStudentDAO() {
		return studentCourseDAO;
	}

	public void setStudentDAO(StudentCourseDAO localStudent) {
		this.studentCourseDAO = localStudent;
	}

}
