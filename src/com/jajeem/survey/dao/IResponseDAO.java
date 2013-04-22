package com.jajeem.survey.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.survey.model.Response;

public interface IResponseDAO {
	
	void create(Response respons) throws SQLException;
	void update(Response respons) throws SQLException;
	Response get(int id) throws SQLException;
	ArrayList<Response> list() throws SQLException;
}
