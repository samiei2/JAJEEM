package com.jajeem.message.dao.h2;

import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.util.BaseDAO;
import com.jajeem.util.H2Connection;

import com.jajeem.message.dao.IMessageDAO;
import com.jajeem.message.model.Message;

public class MessageDAO implements IMessageDAO {

	Logger logger = Logger.getLogger(MessageDAO.class);

	public MessageDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Message create(Message message) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();

		ps = con.prepareStatement("INSERT INTO Message (fromId, toId, content, date, type, anonymous) " +
				" VALUES (?, ?, ?, ?, ?, ?);");
		ps.setInt(1, message.getFromId());
		ps.setInt(2, message.getToId());
		ps.setString(3, message.getContent());
		ps.setString(4, message.getDate());
		ps.setInt(5, message.getType());
		ps.setInt(6, message.getAnonymous());

		try {
			rs = ps.executeUpdate();

			// get last id
			ResultSet maxId = null;
			maxId = ps.getGeneratedKeys();
			if (maxId.next()) {
				message.setId(maxId.getInt(1));
			} else {
				message.setId(0);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
		} finally {
			try {
				if (rs == 1) {

				} else {
					message.setId(-1);
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

		return message;
	}

	@Override
	public Message get(Message message) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Message WHERE Message.id = ?;");
		ps.setInt(1, message.getId());

		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				message.setFromId(rs.getInt("fromId"));
				message.setToId(rs.getInt("toId"));
				message.setContent(rs.getString("content"));
				message.setDate(rs.getString("date"));
				message.setType(rs.getShort("type"));
				message.setAnonymous(rs.getByte("anonymous"));
			} else {
				message.setId(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
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

		return message;
	}

	@Override
	public boolean update(Message message) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("UPDATE Message SET fromId=?, toId=?, content=?, date=?, type=?, anonymous=? WHERE id = ?");
		
		ps.setInt(1, message.getFromId());
		ps.setInt(2, message.getToId());
		ps.setString(3, message.getContent());
		ps.setString(4, message.getDate());
		ps.setInt(5, message.getType());
		ps.setInt(6, message.getAnonymous());
		ps.setInt(7, message.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
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
	public boolean delete(Message message) throws SQLException {
		
		PreparedStatement ps = null;
		int rs = 0;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("DELETE FROM Message WHERE Message.id = ?;");
		ps.setInt(1, message.getId());

		try {
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
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
	public ArrayList<Message> list() throws SQLException {
		
		ArrayList<Message> allMessages = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Connection con = BaseDAO.getConnection();
		
		ps = con.prepareStatement("SELECT * FROM Message");

		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				Message message = new Message();

				message.setId(rs.getInt("id"));
				message.setFromId(rs.getInt("fromId"));
				message.setToId(rs.getInt("toId"));
				message.setContent(rs.getString("content"));
				message.setDate(rs.getString("date"));
				message.setType(rs.getShort("type"));
				message.setAnonymous(rs.getByte("anonymous"));

				allMessages.add(message);
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

		return allMessages;
	}

}
