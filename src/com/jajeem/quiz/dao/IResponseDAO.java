package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Response;

public interface IResponseDAO {

	Response create(Response response) throws SQLException;

	Response get(Response response) throws SQLException;

	ArrayList<com.jajeem.quiz.model.Response> list() throws SQLException;

	boolean update(Response response) throws SQLException;

	boolean delete(Response response) throws SQLException;
}
