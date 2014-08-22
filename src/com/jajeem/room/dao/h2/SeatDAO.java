package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.dao.ISeatDAO;
import com.jajeem.room.model.Seat;
import com.jajeem.util.BaseDAO;

public class SeatDAO implements ISeatDAO {

	Logger logger = Logger.getLogger(SeatDAO.class);

	public SeatDAO() {

	}

	@Override
	public Seat create(Seat seat) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Seat (Roomid, name, row, col) "
				+ " VALUES (?, ?, ? , ?);");
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
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					seat.setId(-1);
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
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
				seat.setClassId(rs.getInt("Roomid"));
				seat.setName(rs.getString("name"));
				seat.setRow(rs.getInt("row"));
				seat.setColumn(rs.getInt("col"));
			} else {
				seat.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			seat.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		return seat;
	}

	@Override
	public boolean update(Seat seat) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Seat SET Roomid=?, name=?, row=?, col=? WHERE id = ?");

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
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
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
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
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
				seat.setClassId(rs.getInt("Roomid"));
				seat.setName(rs.getString("name"));
				seat.setRow(rs.getInt("row"));
				seat.setColumn(rs.getInt("col"));

				allSeats.add(seat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				new JajeemExceptionHandler(e);
			}
		}

		return allSeats;
	}

}
