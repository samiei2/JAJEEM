package com.jajeem.message.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.message.model.Message;

public interface IMessageDAO {
	
	Message create(Message message) throws SQLException;
	boolean update(Message message) throws SQLException;
	boolean delete(Message message) throws SQLException;
	Message get(Message message) throws SQLException;
	ArrayList<Message> list() throws SQLException;
}