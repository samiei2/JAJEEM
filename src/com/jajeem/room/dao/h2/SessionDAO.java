package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.dao.ISessionDAO;
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

		ps = con.prepareStatement("INSERT INTO Session (roomId, instructorId, attendantId, courseId, start, end) " +
				" VALUES (?, ?, ? ,? ,? ,?);");
		ps.setInt(1, session.getClassId());
		ps.setInt(2, session.getInstructorId());
		ps.setInt(3, session.getAttendantId());
		ps.setInt(4, session.getCourseId());
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
			new JajeemExcetionHandler(e);
		} finally {
			try {
				if (rs == 1) {

				} else {
					session.setId(-1);
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
				session.setClassId(rs.getInt("roomId"));
				session.setInstructorId(rs.getInt("instructorId"));
				session.setAttendantId(rs.getInt("attendantid"));
				session.setCourseId(rs.getInt("courseid"));
				session.setStart(rs.getInt("start"));
				session.setEnd(rs.getInt("end"));
			} else {
				session.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
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

		return session;
	}

	@Override
	public boolean update(Session session) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("UPDATE Session SET roomId=?, instructorId=?, attendantId=?, courseId=?, start=?, end=? WHERE id = ?");
		
		ps.setInt(1, session.getClassId());
		ps.setInt(2, session.getInstructorId());
		ps.setInt(3, session.getAttendantId());
		ps.setInt(4, session.getCourseId());
		ps.setInt(5, session.getStart());
		ps.setInt(6, session.getEnd());
		ps.setInt(7, session.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			session.setId(-1);
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
				session.setClassId(rs.getInt("roomId"));
				session.setInstructorId(rs.getInt("instructorId"));
				session.setAttendantId(rs.getInt("attendantId"));
				session.setCourseId(rs.getInt("courseId"));
				session.setStart(rs.getInt("start"));
				session.setEnd(rs.getInt("end"));

				allSessions.add(session);
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

		return allSessions;
	}

}
