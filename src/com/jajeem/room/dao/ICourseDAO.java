package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Course;

public interface ICourseDAO {

	Course create(Course course) throws SQLException;

	Course get(Course course) throws SQLException;

	boolean update(Course course) throws SQLException;

	boolean delete(Course course) throws SQLException;

	ArrayList<Course> list() throws SQLException;
}
