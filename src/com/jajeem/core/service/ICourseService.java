package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.StudentCourse;
import com.jajeem.room.model.Course;

public interface ICourseService {

	Course get(Course course) throws SQLException;

	Course create(Course course) throws SQLException;

	boolean update(Course course) throws SQLException;

	boolean delete(Course studentCourse) throws SQLException;

	ArrayList<Course> list() throws SQLException;

}
