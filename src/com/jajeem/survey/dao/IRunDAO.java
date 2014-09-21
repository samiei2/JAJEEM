package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Run;

public interface IRunDAO {

	Run create(Run run) throws SQLException;

	boolean update(Run run) throws SQLException;

	Run get(Run run) throws SQLException;

	boolean delete(Run run) throws SQLException;

	ArrayList<Run> list() throws SQLException;
}
