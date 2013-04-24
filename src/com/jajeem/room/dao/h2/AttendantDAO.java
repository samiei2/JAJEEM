package com.jajeem.room.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.H2ConnectionImpl;

import com.jajeem.room.dao.IAttendantDAO;
import com.jajeem.room.model.Attendant;

public class AttendantDAO implements IAttendantDAO {

	Logger logger = Logger.getLogger(AttendantDAO.class);

	public AttendantDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Attendant create(Attendant attendant) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();

		ps = con.prepareStatement("INSERT INTO Attendant (sessionId, studentId, seatId, status) " +
				" VALUES (?, ?, ?, ?);");
		ps.setInt(1, attendant.getSessionId());
		ps.setInt(2, attendant.getStudentId());
		ps.setInt(3, attendant.getSeatId());
		ps.setInt(4, attendant.getStatus());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				attendant.setId(maxId.getInt(1));
			} else {
				attendant.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			attendant.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					attendant.setId(-1);
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

		return attendant;
	}

	@Override
	public Attendant get(Attendant attendant) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Attendant WHERE Attendant.id = ?;");
		ps.setInt(1, attendant.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				attendant.setSessionId(rs.getInt("sessionId"));
				attendant.setStudentId(rs.getInt("studentId"));
				attendant.setSeatId(rs.getInt("seatId"));
				attendant.setStatus(rs.getShort("status"));
			} else {
				attendant.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			attendant.setId(-1);
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

		return attendant;
	}

	@Override
	public boolean update(Attendant attendant) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("UPDATE Attendant SET sessionId=?, StudentId=?, seatId=?, status=? WHERE id = ?");
		
		ps.setInt(1, attendant.getSessionId());
		ps.setInt(2, attendant.getStudentId());
		ps.setInt(3, attendant.getSeatId());
		ps.setInt(4, attendant.getStatus());
		ps.setInt(5, attendant.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			attendant.setId(-1);
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
	public boolean delete(Attendant attendant) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("DELETE FROM Attendant WHERE Attendant.id = ?;");
		ps.setInt(1, attendant.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			attendant.setId(-1);
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
	public ArrayList<Attendant> list() throws SQLException {
		
		ArrayList<Attendant> allAttendants = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Attendant");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Attendant attendant = new Attendant();

				attendant.setId(rs.getInt("id"));
				attendant.setSessionId(rs.getInt("sessionId"));
				attendant.setStudentId(rs.getInt("studentId"));
				attendant.setSeatId(rs.getInt("seatId"));
				attendant.setStatus(rs.getShort("status"));

				allAttendants.add(attendant);
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

		return allAttendants;
	}

}
