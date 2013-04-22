package com.jajeem.groupwork.dao.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.groupwork.dao.IGroupMemberDAO;
import com.jajeem.groupwork.model.GroupMember;
import com.jajeem.util.HSQLDBConnectionImpl;

public class GroupMemberDAO implements IGroupMemberDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(GroupMemberDAO.class);

	public GroupMemberDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	public GroupMember create(GroupMember groupMember) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO GroupMember VALUES( groupId=:1, seatId=:1");
			ps.setInt(1, groupMember.getGroupId());
			ps.setInt(2, groupMember.getSeatId());

			rs = ps.executeQuery();

			if (rs.next()) {
				groupMember.setId(rs.getInt(1));
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
	public boolean update(GroupMember groupMember) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE GroupMember SET groupId=':1', seatId=':1' WHERE id=:1 ");
			ps.setInt(1, groupMember.getGroupId());
			ps.setInt(2, groupMember.getSeatId());
			ps.setInt(7, groupMember.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
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
	public boolean delete (GroupMember groupMember) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM GroupMember WHERE id=:1 ");
			ps.setInt(1, groupMember.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupMember.setId(-1);
		} finally {
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
	public GroupMember get(GroupMember groupMember) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, groupId, seatId FROM GroupMember WHERE id=:1 ");
			ps.setInt(1, groupMember.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				groupMember.setId(rs.getInt(1));
				groupMember.setGroupId(rs.getInt(2));
				groupMember.setSeatId(rs.getInt(3));
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
	public void list() {
		// TODO Auto-generated method stub

	}

}
