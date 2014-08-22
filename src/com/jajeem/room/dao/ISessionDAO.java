package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Session;

public interface ISessionDAO {

	Session create(Session session) throws SQLException;

	Session get(Session session) throws SQLException;

	boolean update(Session session) throws SQLException;

	boolean delete(Session session) throws SQLException;

	ArrayList<Session> list() throws SQLException;
}
