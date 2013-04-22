package com.jajeem.room.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.room.dao.IAttendantDAO;
import com.jajeem.room.model.Attendant;
import com.jajeem.room.model.Seat;
import com.jajeem.util.HSQLDBConnectionImpl;

public class AttandantDAO implements IAttendantDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	
	@Override
	public void create(Attendant attendant) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into attendants (id,sessionid,studentid,seatid,status) values (+"+attendant.getId()+","+attendant.getSessionId()+","+attendant.getStudentId()+","+attendant.getSeatId()+","+attendant.getStatus()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Attendant attendant) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update attendants set status = "+attendant.getStatus()
				+" where attendants.id="+attendant.getId()+" and attendants.sessionid="+attendant.getSessionId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void delete(Attendant attendant) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "delete from attendants "
				+"where attendants.id="+attendant.getId()+" and attendants.sessionid="+attendant.getSessionId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Attendant get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from attendants "
				+"where attendants.id="+id+";";
		Attendant result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Attendant();
				result.setId(id);
				result.setSeatId(rs.getInt("seatid"));
				result.setSessionId(rs.getInt("sessionid"));
				result.setStudentId(rs.getInt("studentid"));
				result.setStatus(rs.getShort("status"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Attendant> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from attendants;";
		
		ArrayList<Attendant> allAttendants = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Attendant attendant = new Attendant();
				attendant.setId(rs.getInt("id"));
				attendant.setSessionId(rs.getInt("sessionid"));
				attendant.setStudentId(rs.getInt("studentid"));
				attendant.setSeatId(rs.getInt("seatid"));
				attendant.setStatus(rs.getShort("status"));
				allAttendants.add(attendant);
			}
		}
		con.close();
		return allAttendants;
	}
	
}
