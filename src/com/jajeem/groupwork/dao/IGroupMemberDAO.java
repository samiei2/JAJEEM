package com.jajeem.groupwork.dao;

import com.jajeem.groupwork.model.GroupMember;

public interface IGroupMemberDAO {

	GroupMember create(GroupMember groupMember);
	boolean update(GroupMember groupMember);
	boolean delete(GroupMember groupMember);
	GroupMember get(GroupMember groupMember);
	void list();
}
