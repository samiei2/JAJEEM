package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Session;

public interface ISessionDAO {
	
	void create(Session session) throws SQLException;
	void update(Session session) throws SQLException;
	void delete(Session session) throws SQLException;
	Session get(int id) throws SQLException;
	ArrayList<Session> list() throws SQLException;
}
