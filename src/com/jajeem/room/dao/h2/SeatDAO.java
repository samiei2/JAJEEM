package com.jajeem.room.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.dao.ISeatDAO;
import com.jajeem.room.model.Seat;

public class SeatDAO implements ISeatDAO {

	Logger logger = Logger.getLogger(SeatDAO.class);

	public SeatDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Seat create(Seat seat) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Seat (classId, name, row, col) " +
				" VALUES (?, ?, ? , ?);");
		ps.setInt(1, seat.getClassId());
		ps.setString(2, seat.getName());
		ps.setInt(3, seat.getRow());
		ps.setInt(4, seat.getColumn());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				seat.setId(maxId.getInt(1));
			} else {
				seat.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			seat.setId(-1);
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					seat.setId(-1);
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

		return seat;
	}

	@Override
	public Seat get(Seat seat) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Seat WHERE Seat.id = ?;");
		ps.setInt(1, seat.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				seat.setClassId(rs.getInt("classId"));
				seat.setName(rs.getString("name"));
				seat.setRow(rs.getInt("row"));
				seat.setColumn(rs.getInt("col"));
			} else {
				seat.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			seat.setId(-1);
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

		return seat;
	}

	@Override
	public boolean update(Seat seat) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("UPDATE Seat SET classId=?, name=?, row=?, col=? WHERE id = ?");
		
		ps.setInt(1, seat.getClassId());
		ps.setString(2, seat.getName());
		ps.setInt(3, seat.getRow());
		ps.setInt(4, seat.getColumn());
		ps.setInt(5, seat.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			seat.setId(-1);
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
	public boolean delete(Seat seat) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("DELETE FROM Seat WHERE Seat.id = ?;");
		ps.setInt(1, seat.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			seat.setId(-1);
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
	public ArrayList<Seat> list() throws SQLException {
		
		ArrayList<Seat> allSeats = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Seat");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Seat seat = new Seat();

				seat.setId(rs.getInt("id"));
				seat.setClassId(rs.getInt("classId"));
				seat.setName(rs.getString("name"));
				seat.setRow(rs.getInt("row"));
				seat.setColumn(rs.getInt("col"));

				allSeats.add(seat);
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

		return allSeats;
	}

}
