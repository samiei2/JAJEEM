package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.dao.h2.StudentCourseDAO;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.quiz.model.Run;
import com.jajeem.room.dao.h2.CourseDAO;
import com.jajeem.room.model.Course;

public class InstructorCourseService implements ICourseService {
	private CourseDAO courseDAO = new CourseDAO();

	@Override
	public Course get(Course course) throws SQLException {
		if (courseDAO != null) {
			return courseDAO.get(course);
		}
		return null;
	}

	@Override
	public Course create(Course course)
			throws SQLException {
		if (courseDAO != null) {
			courseDAO.create(course);
		}
		return null;
	}

	@Override
	public boolean update(Course course) throws SQLException {
		if (courseDAO != null) {
			return courseDAO.update(course);
		}
		return false;
	}

	public boolean delete(Course studentCourse) throws SQLException {
		if (courseDAO != null) {
			courseDAO.delete(studentCourse);
		}
		return false;
	}

	@Override
	public ArrayList<Course> list() throws SQLException {
		if (courseDAO != null) {
			return courseDAO.list();
		}
		return null;
	}

	public ArrayList<Course> getInstructorCoursesById(int instructorId)
			throws SQLException {
		if (courseDAO != null) {
			ArrayList<Course> courselist = list();
			ArrayList<Course> returnlist = new ArrayList<Course>();
			for (int i = 0; i < courselist.size(); i++) {
				if(courselist.get(i).getInstructorId() == instructorId)
					returnlist.add(courselist.get(i));
			}
			return returnlist;
		}
		return null;
	}

	public CourseDAO getCourseDAO() {
		return courseDAO;
	}
}