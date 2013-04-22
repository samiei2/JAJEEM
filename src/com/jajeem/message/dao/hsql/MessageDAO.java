package com.jajeem.message.dao.hsql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.message.dao.IMessageDAO;
import com.jajeem.message.model.Message;
import com.jajeem.util.HSQLDBConnectionImpl;

public class MessageDAO implements IMessageDAO {

	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	Logger logger = Logger.getLogger(MessageDAO.class);

	public MessageDAO() {
		PropertyConfigurator.configure("conf/log4j.conf");
	}

	@Override
	public Message create(Message message) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("INSERT INTO Message VALUES( fromId=:1,toId=:1, content=:1, date=:1, type=:1, anonymouse=:1)");
			ps.setInt(1, message.getFromId());
			ps.setInt(2, message.getToId());
			ps.setString(3, message.getContent());
			ps.setString(4, message.getDate());
			ps.setInt(5, message.getType());
			ps.setInt(6, message.getAnonymous());

			rs = ps.executeQuery();

			if (rs.next()) {
				message.setId(rs.getInt(1));
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
	public boolean update(Message message) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("UPDATE Message SET fromId=:1,toId=:1, content=:1, date=:1, type=:1, anonymouse=:1 WHERE id=:1 ");
			ps.setInt(1, message.getFromId());
			ps.setInt(2, message.getToId());
			ps.setString(3, message.getContent());
			ps.setString(4, message.getDate());
			ps.setInt(5, message.getType());
			ps.setInt(6, message.getAnonymous());
			ps.setInt(7, message.getId());

			rs = ps.executeUpdate();

			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
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
	public boolean delete(Message message) {
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("DELETE FROM Message WHERE id=:1 ");
			ps.setInt(1, message.getId());
			rs = ps.executeUpdate();
			if (rs == 1) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message.setId(-1);
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
	public Message get(Message message) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = conn.getConnection();

			ps = con.prepareStatement("SELECT id, fromId,toId, content, date, type, anonymouse FROM Message WHERE id=:1 ");
			ps.setInt(7, message.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				ps.setInt(1, message.getFromId());
				ps.setInt(2, message.getToId());
				ps.setString(3, message.getContent());
				ps.setString(4, message.getDate());
				ps.setInt(5, message.getType());
				ps.setInt(6, message.getAnonymous());
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
	public void list() {
		// TODO Auto-generated method stub

	}

}
