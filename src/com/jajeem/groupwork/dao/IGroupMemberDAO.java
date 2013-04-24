package com.jajeem.groupwork.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.groupwork.model.GroupMember;

public interface IGroupMemberDAO {

	GroupMember create(GroupMember groupMember) throws SQLException;
	boolean update(GroupMember groupMember) throws SQLException;
	boolean delete(GroupMember groupMember) throws SQLException;
	GroupMember get(GroupMember groupMember) throws SQLException;
	ArrayList<GroupMember> list() throws SQLException;
}
