package com.jajeem.groupwork.service;

public interface IConversationService {

	void create(String leader, String[] groupList, int port)
			throws NumberFormatException, Exception;
}
