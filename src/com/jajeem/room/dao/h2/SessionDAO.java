package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.dao.ISessionDAO;
import com.jajeem.room.model.Attendant;
import com.jajeem.room.model.Course;
import com.jajeem.room.model.Room;
import com.jajeem.room.model.Session;
import com.jajeem.util.BaseDAO;

public class SessionDAO implements ISessionDAO {

	Logger logger = Logger.getLogger(SessionDAO.class);

	public SessionDAO() {

	}

	@Override
	public Session create(Session session) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Session (roomId, instructorId, attendantId, courseId, start, end) "
				+ " VALUES (?, ?, ? ,? ,? ,?);");
		ps.setInt(1, session.getRoom().getId());
		ps.setInt(2, session.getInstructor().getId());
		ps.setInt(3, session.getAttendant().getId());
		ps.setInt(4, session.getCourse().getId());
		ps.setInt(5, session.getStart());
		ps.setInt(6, session.getEnd());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				session.setId(maxId.getInt(1));
			} else {
				session.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
			new JajeemExceptionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					session.setId(-1);
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

		return session;
	}

	@Override
	public Session get(Session session) throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Session WHERE Session.id = ?;");
		ps.setInt(1, session.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				session.setRoom(new Room(rs.getInt("roomId")));
				session.setInstructor(new Instructor(rs.getInt("instructorId")));
				session.setAttendant(new Attendant(rs.getInt("attendantid")));
				session.setCourse(new Course(rs.getInt("courseid")));
				session.setStart(rs.getInt("start"));
				session.setEnd(rs.getInt("end"));
			} else {
				session.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
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

		return session;
	}

	@Override
	public boolean update(Session session) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("UPDATE Session SET roomId=?, instructorId=?, attendantId=?, courseId=?, start=?, end=? WHERE id = ?");

		ps.setInt(1, session.getRoom().getId());
		ps.setInt(2, session.getInstructor().getId());
		ps.setInt(3, session.getAttendant().getId());
		ps.setInt(4, session.getCourse().getId());
		ps.setInt(5, session.getStart());
		ps.setInt(6, session.getEnd());
		ps.setInt(7, session.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
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
	public boolean delete(Session session) throws SQLException {

		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("DELETE FROM Session WHERE Session.id = ?;");
		ps.setInt(1, session.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
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
	public ArrayList<Session> list() throws SQLException {

		ArrayList<Session> allSessions = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("SELECT * FROM Session");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Session session = new Session();

				session.setId(rs.getInt("id"));
				session.setRoom(new Room(rs.getInt("roomId")));
				session.setInstructor(new Instructor(rs.getInt("instructorId")));
				session.setAttendant(new Attendant(rs.getInt("attendantId")));
				session.setCourse(new Course(rs.getInt("courseId")));
				session.setStart(rs.getInt("start"));
				session.setEnd(rs.getInt("end"));

				allSessions.add(session);
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

		return allSessions;
	}

}
