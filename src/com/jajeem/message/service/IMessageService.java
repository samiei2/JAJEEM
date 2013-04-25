package com.jajeem.message.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.message.model.Message;

public interface IMessageService {
	
	Message create(Message message) throws SQLException;
	ArrayList<Message> list() throws SQLException;
	void getByFrom();
	void getByTo();
}
