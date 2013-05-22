package com.jajeem.groupwork.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.groupwork.dao.IGroupworkDAO;
import com.jajeem.groupwork.model.Groupwork;
import com.jajeem.util.BaseDAO;

public class GroupworkDAO implements IGroupworkDAO {

	Logger logger = Logger.getLogger(GroupworkDAO.class);

	public GroupworkDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Groupwork create(Groupwork groupwork) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("INSERT INTO Groupwork (classId, name) " +
				" VALUES (?, ?);");
		ps.setInt(1, groupwork.getClassId());
		ps.setString(2, groupwork.getName());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				groupwork.setId(maxId.getInt(1));
			} else {
				groupwork.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					groupwork.setId(-1);
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return groupwork;
	}

	@Override
	public Groupwork get(Groupwork groupwork) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Groupwork WHERE Groupwork.id = ?;");
		ps.setInt(1, groupwork.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				groupwork.setClassId(rs.getInt("classId"));
				groupwork.setName(rs.getString("name"));
			} else {
				groupwork.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return groupwork;
	}

	@Override
	public boolean update(Groupwork groupwork) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("UPDATE Groupwork SET classId=?, name=? WHERE id = ?");
		ps.setInt(1, groupwork.getClassId());
		ps.setString(2, groupwork.getName());
		ps.setInt(3, groupwork.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return false;
	}

	@Override
	public boolean delete(Groupwork groupwork) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("DELETE FROM Groupwork WHERE Groupwork.id = ?;");
		ps.setInt(1, groupwork.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			groupwork.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return false;
	}

	@Override
	public ArrayList<Groupwork> list() throws SQLException {
		
		ArrayList<Groupwork> allGroupworks = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Groupwork");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Groupwork groupwork = new Groupwork();

				groupwork.setId(rs.getInt("id"));
				groupwork.setClassId(rs.getInt("classId"));
				groupwork.setName(rs.getString("name"));

				allGroupworks.add(groupwork);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				new JajeemExcetionHandler(e);
			}
		}

		return allGroupworks;
	}

}