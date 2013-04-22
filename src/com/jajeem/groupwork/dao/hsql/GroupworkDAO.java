package com.jajeem.groupwork.dao.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.groupwork.dao.IGroupworkDAO;
import com.jajeem.groupwork.model.Groupwork;
import com.jajeem.util.HSQLDBConnectionImpl;

public class GroupworkDAO implements IGroupworkDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(GroupworkDAO.class);

	public GroupworkDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	public Groupwork create(Groupwork groupwork) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO Groupwork VALUES( classId=:1, name=:1");
			ps.setInt(1, groupwork.getClassId());
			ps.setString(2, groupwork.getName());

			rs = ps.executeQuery();

			if (rs.next()) {
				groupwork.setId(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
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

		return groupwork;
	}

	@Override
	public boolean update(Groupwork groupwork) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE Groupwork SET classId=':1', name=':1' WHERE id=:1 ");
			ps.setInt(1, groupwork.getClassId());
			ps.setString(2, groupwork.getName());
			ps.setInt(7, groupwork.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
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
	public boolean delete (Groupwork groupwork) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM Groupwork WHERE id=:1 ");
			ps.setInt(1, groupwork.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
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
	public Groupwork get(Groupwork groupwork) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, classId, name FROM Groupwork WHERE id=:1 ");
			ps.setInt(1, groupwork.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				groupwork.setId(rs.getInt(1));
				groupwork.setClassId(rs.getInt(2));
				groupwork.setName(rs.getString(3));
			} else {
				groupwork.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
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

		return groupwork;
	}

	@Override
	public void list() {
		// TODO Auto-generated method stub

	}

}
