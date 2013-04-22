package com.jajeem.message.dao;

import com.jajeem.message.model.Message;

public interface IMessageDAO {
	
	Message create(Message message);
	boolean update(Message message);
	boolean delete(Message message);
	Message get(Message message);
	void list();
}