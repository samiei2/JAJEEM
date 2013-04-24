package com.jajeem.groupwork.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.groupwork.model.Group;

public interface IGroupDAO {
	
	Group create(Group group) throws SQLException;
	boolean update(Group group) throws SQLException;
	boolean delete(Group group) throws SQLException;
	Group get(Group group) throws SQLException;
	ArrayList<Group> list() throws SQLException;
}
