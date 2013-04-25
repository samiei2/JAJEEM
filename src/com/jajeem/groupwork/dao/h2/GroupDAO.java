package com.jajeem.groupwork.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.H2Connection;


import com.jajeem.groupwork.dao.IGroupDAO;
import com.jajeem.groupwork.model.Group;

public class GroupDAO implements IGroupDAO {

	Logger logger = Logger.getLogger(GroupDAO.class);

	public GroupDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Group create(Group group) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("INSERT INTO Group (groupworkId, name, color) VALUES (?, ?, ?);");
		ps.setInt(1, group.getGroupworkId());
		ps.setString(2, group.getName());
		ps.setInt(3, group.getColor());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				group.setId(maxId.getInt(1));
			} else {
				group.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					group.setId(-1);
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

		return group;
	}

	@Override
	public Group get(Group group) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Group WHERE Group.id = ?;");
		ps.setInt(1, group.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				group.setGroupworkId(rs.getInt("groupworkId"));
				group.setName(rs.getString("name"));
				group.setColor(rs.getInt("color"));
			} else {
				group.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
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

		return group;
	}

	@Override
	public boolean update(Group group) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("UPDATE Group SET groupworkId=?, name=?, color=? WHERE id = ?");
		ps.setInt(1, group.getGroupworkId());
		ps.setString(2, group.getName());
		ps.setInt(3, group.getColor());
		ps.setInt(4, group.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
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
	public boolean delete(Group group) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("DELETE FROM Group WHERE Group.id = ?;");
		ps.setInt(1, group.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
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
	public ArrayList<Group> list() throws SQLException {
		
		ArrayList<Group> allGroups = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2Connection conn = new H2Connection();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Group");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Group group = new Group();

				group.setId(rs.getInt("id"));
				group.setGroupworkId(rs.getInt("groupworkId"));
				group.setName(rs.getString("name"));
				group.setColor(rs.getInt("color"));

				allGroups.add(group);
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

		return allGroups;
	}

}
