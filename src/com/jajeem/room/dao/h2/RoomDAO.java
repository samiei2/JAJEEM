package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.dao.IRoomDAO;
import com.jajeem.room.model.Room;
import com.jajeem.util.BaseDAO;

public class RoomDAO implements IRoomDAO {

	Logger logger = Logger.getLogger(RoomDAO.class);

	public RoomDAO() {

	}

	@Override
	public Room create(Room room) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Room (name, signInType, attendanceType, seatSize) "
				+ " VALUES (?, ?, ?, ?);");
		ps.setString(1, room.getName());
		ps.setInt(2, room.getSignInType());
		ps.setByte(3, room.getAttendanceType());
		ps.setInt(4, room.getSeatSize());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				room.setId(maxId.getInt(1));
			} else {
				room.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			room.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					room.setId(-1);
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

		return room;
	}

	@Override
	public Room get(Room room) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Room WHERE Room.id = ?;");
		ps.setInt(1, room.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				room.setName(rs.getString("name"));
				room.setSignInType(rs.getByte("signInType"));
				room.setAttendanceType(rs.getByte("attendanceType"));
				room.setSeatSize(rs.getInt("seatSize"));
			} else {
				room.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			room.setId(-1);
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

		return room;
	}

	@Override
	public boolean update(Room room) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Room SET name=?, signInType=?, "
				+ "attendanceType = ?, seatSize = ? WHERE id = ?");

		ps.setString(1, room.getName());
		ps.setInt(2, room.getSignInType());
		ps.setByte(3, room.getAttendanceType());
		ps.setInt(4, room.getSeatSize());
		ps.setInt(5, room.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			room.setId(-1);
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
	public boolean delete(Room room) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Room WHERE Room.id = ?;");
		ps.setInt(1, room.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			room.setId(-1);
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
	public ArrayList<Room> list() throws SQLException {

		ArrayList<Room> allRooms = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Room");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Room room = new Room();

				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setSignInType(rs.getByte("signInType"));
				room.setAttendanceType(rs.getByte("attendanceType"));
				room.setSeatSize(rs.getInt("seatSize"));

				allRooms.add(room);
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

		return allRooms;
	}

}
