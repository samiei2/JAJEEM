package com.jajeem.message.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.message.dao.h2.MessageDAO;
import com.jajeem.message.model.Message;

public class MessageService implements IMessageService{
	private MessageDAO messageDAO;
	@Override
	public Message create(Message message) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Message> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getByFrom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getByTo() {
		// TODO Auto-generated method stub
		
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

}
