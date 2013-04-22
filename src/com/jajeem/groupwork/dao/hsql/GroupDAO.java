package com.jajeem.groupwork.dao.hsql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.groupwork.dao.IGroupDAO;
import com.jajeem.groupwork.model.Group;
import com.jajeem.util.HSQLDBConnectionImpl;

public class GroupDAO implements IGroupDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(GroupDAO.class);

	public GroupDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Group create(Group group) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO Group VALUES( groupworkId=:1, name=:1, color=:1)");
			ps.setInt(1, group.getGroupworkId());
			ps.setString(2, group.getName());
			ps.setString(3, group.getColor());

			rs = ps.executeQuery();

			if (rs.next()) {
				group.setId(rs.getInt(1));
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
	public boolean update(Group group) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE Group SET groupworkId=:1, name=:1, color=:1 WHERE id=:1 ");
			ps.setInt(1, group.getGroupworkId());
			ps.setString(2, group.getName());
			ps.setString(3, group.getColor());
			ps.setInt(4, group.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
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
	public boolean delete(Group group) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM Group WHERE id=:1 ");
			ps.setInt(1, group.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			group.setId(-1);
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
	public Group get(Group group) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, groupworkId, name, color FROM Group WHERE id=:1 ");
			ps.setInt(1, group.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				ps.setInt(1, group.getId());
				ps.setInt(2, group.getGroupworkId());
				ps.setString(3, group.getName());
				ps.setString(4, group.getColor());
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
	public void list() {
		// TODO Auto-generated method stub

	}

}
