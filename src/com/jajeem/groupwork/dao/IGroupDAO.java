package com.jajeem.groupwork.dao;

import com.jajeem.groupwork.model.Group;

public interface IGroupDAO {
	
	Group create(Group group);
	boolean update(Group group);
	boolean delete(Group group);
	Group get(Group group);
	void list();
}
