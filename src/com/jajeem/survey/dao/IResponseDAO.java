package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Response;

public interface IResponseDAO {

	Response create(Response respons) throws SQLException;

	boolean update(Response respons) throws SQLException;

	Response get(Response response) throws SQLException;

	boolean delete(Response response) throws SQLException;

	ArrayList<Response> list() throws SQLException;
}
