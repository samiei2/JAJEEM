package com.jajeem.groupwork.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.groupwork.dao.h2.GroupDAO;
import com.jajeem.groupwork.dao.h2.GroupMemberDAO;
import com.jajeem.groupwork.dao.h2.GroupworkDAO;
import com.jajeem.groupwork.model.Groupwork;

public class GroupworkService implements IGroupworkService{
	private GroupworkDAO groupworkDAO;
	private GroupMemberDAO groupMemberDAO;
	private GroupDAO groupDAO;
	@Override
	public Groupwork create(Groupwork groupwork) throws SQLException {
		if(groupworkDAO != null)
			groupworkDAO.create(groupwork);
		return null;
	}

	@Override
	public boolean update(Groupwork groupwork) throws SQLException {
		if(groupworkDAO != null)
			groupworkDAO.update(groupwork);
		return false;
	}

	@Override
	public boolean delete(Groupwork groupwork) throws SQLException {
		if(groupworkDAO != null)
			groupworkDAO.delete(groupwork);
		return false;
	}

	@Override
	public Groupwork get(Groupwork groupwork) throws SQLException {
		if(groupworkDAO != null)
			groupworkDAO.get(groupwork);
		return null;
	}

	@Override
	public ArrayList<Groupwork> list() throws SQLException {
		if(groupworkDAO != null)
			groupworkDAO.list();
		return null;
	}

	public GroupworkDAO getGroupworkDAO() {
		return groupworkDAO;
	}

	public void setGroupworkDAO(GroupworkDAO groupworkDAO) {
		this.groupworkDAO = groupworkDAO;
	}

	public GroupMemberDAO getGroupmemeberDAO() {
		return groupMemberDAO;
	}

	public void setGroupMemberDAO(GroupMemberDAO groupMemberDAO) {
		this.groupMemberDAO = groupMemberDAO;
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

}
