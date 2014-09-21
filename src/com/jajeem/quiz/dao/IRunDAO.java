package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Run;

public interface IRunDAO {

	Run create(Run run) throws SQLException;

	Run get(Run run) throws SQLException;

	ArrayList<com.jajeem.quiz.model.Run> list() throws SQLException;

	boolean update(Run run) throws SQLException;

	boolean delete(Run run) throws SQLException;
}
