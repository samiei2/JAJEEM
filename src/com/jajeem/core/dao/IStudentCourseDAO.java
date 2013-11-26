package com.jajeem.core.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.StudentCourse;

public interface IStudentCourseDAO extends IUserDAO {
	StudentCourse get(StudentCourse studentCourse) throws SQLException;

	StudentCourse create(StudentCourse studentCourse) throws SQLException;

	boolean update(StudentCourse studentCourse) throws SQLException;

	boolean delete(StudentCourse studentCourse) throws SQLException;

	ArrayList<StudentCourse> list() throws SQLException;
}
