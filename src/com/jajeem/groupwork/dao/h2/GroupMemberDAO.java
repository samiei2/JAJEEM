package com.jajeem.groupwork.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.H2Connection;

import com.jajeem.groupwork.dao.IGroupMemberDAO;
import com.jajeem.groupwork.model.GroupMember;

public class GroupMemberDAO implements IGroupMemberDAO {

	Logger logger = Logger.getLogger(GroupMemberDAO.class);

	public GroupMemberDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public GroupMember create(GroupMember groupMember) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("INSERT INTO GroupMember (groupId, seatId, leader) " +
				" VALUES (?, ?, ?);");
		ps.setInt(1, groupMember.getGroupId());
		ps.setInt(2, groupMember.getSeatId());
		ps.setString(3, groupMember.getLeader());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				groupMember.setId(maxId.getInt(1));
			} else {
				groupMember.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					groupMember.setId(-1);
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return groupMember;
	}

	@Override
	public GroupMember get(GroupMember groupMember) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM GroupMember WHERE GroupMember.id = ?;");
		ps.setInt(1, groupMember.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				groupMember.setGroupId(rs.getInt("groupId"));
				groupMember.setSeatId(rs.getInt("seatId"));
				groupMember.setLeader(rs.getString("leader"));
			} else {
				groupMember.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return groupMember;
	}

	@Override
	public boolean update(GroupMember groupMember) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("UPDATE GroupMember SET groupId = ?, seatId = ?, " +
				"leader = ? WHERE id = ?");
		ps.setInt(1, groupMember.getGroupId());
		ps.setInt(2, groupMember.getSeatId());
		ps.setString(3, groupMember.getLeader());
		ps.setInt(4, groupMember.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	@Override
	public boolean delete(GroupMember groupMember) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("DELETE FROM GroupMember WHERE GroupMember.id = ?;");
		ps.setInt(1, groupMember.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return false;
	}

	@Override
	public ArrayList<GroupMember> list() throws SQLException {
		
		ArrayList<GroupMember> allGroupMembers = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM GroupMember");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				GroupMember groupMember = new GroupMember();

				groupMember.setId(rs.getInt("id"));
				groupMember.setGroupId(rs.getInt("groupId"));
				groupMember.setSeatId(rs.getInt("seatId"));
				groupMember.setLeader(rs.getString("leader"));

				allGroupMembers.add(groupMember);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}

		return allGroupMembers;
	}

}