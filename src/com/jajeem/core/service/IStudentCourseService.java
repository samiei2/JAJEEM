package com.jajeem.core.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.core.model.StudentCourse;

public interface IStudentCourseService {
	StudentCourse get(StudentCourse studentCourse) throws SQLException;

	StudentCourse create(StudentCourse studentCourse) throws SQLException;

	boolean update(StudentCourse studentCourse) throws SQLException;

	boolean delete(StudentCourse studentCourse) throws SQLException;

	ArrayList<StudentCourse> list() throws SQLException;
}
