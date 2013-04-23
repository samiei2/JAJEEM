package com.jajeem.room.dao.h2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.room.dao.ISessionDAO;
import com.jajeem.room.model.Session;
import com.jajeem.util.H2ConnectionImpl;
import com.jajeem.util.HSQLDBConnectionImpl;

public class SessionDAO implements ISessionDAO{
	

	@Override
	public void create(Session session) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into sessions (id,instructorid,start,end,classid) values ("+session.getId()+","+session.getInstructorId()+","+session.getStart()+","+session.getEnd()+","+session.getClassId()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Session session) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "update sessions set instructorid = "+session.getInstructorId()+",start = "+session.getStart()+",end = "+session.getEnd()
				+"where sessions.id="+session.getId()+" and sessions.classid="+session.getClassId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void delete(Session session) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "delete from sessions "
				+"where sessions.id="+session.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Session get(int id) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from sessions "
				+"where sessions.id="+id+";";
		Session result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Session();
				result.setId(id);
				result.setInstructorId(rs.getInt("instructorid"));
				result.setStart(rs.getInt("start"));
				result.setEnd(rs.getInt("end"));
				result.setClassId(rs.getInt("classid"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Session> list() throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from sessions;";
		
		ArrayList<Session> allSessions = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Session session = new Session();
				session.setId(rs.getInt("id"));
				session.setInstructorId(rs.getInt("instructorid"));
				session.setStart(rs.getInt("start"));
				session.setEnd(rs.getInt("end"));
				session.setClassId(rs.getInt("classid"));
				allSessions.add(session);
			}
		}
		con.close();
		return allSessions;
	}


}
