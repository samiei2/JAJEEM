package com.jajeem.room.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.room.dao.ISeatDAO;
import com.jajeem.room.model.Room;
import com.jajeem.room.model.Seat;
import com.jajeem.util.HSQLDBConnectionImpl;

public class SeatDAO implements ISeatDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
	
	@Override
	public void create(Seat seat) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into seats (id,name,row,column,classid) values (+"+seat.getId()+","+seat.getName()+","+seat.getRow()+","+seat.getColumn()+","+seat.getClassId()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Seat seat) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update seats set name = "+seat.getName()+",column = "+seat.getColumn()+",row = "+seat.getRow()+",classid = "+seat.getClassId()
				+"where seats.id="+seat.getId()+" and seats.classid="+seat.getClassId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void delete(Seat seat) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "delete from seats "
				+"where seats.id="+seat.getId()+" and seats.classid="+seat.getClassId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Seat get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from seats "
				+"where seats.id="+id+";";
		Seat result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Seat();
				result.setId(id);
				result.setName(rs.getString("name"));
				result.setRow(rs.getInt("row"));
				result.setColumn(rs.getInt("column"));
				result.setClassId(rs.getInt("classid"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Seat> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from seats;";
		
		ArrayList<Seat> allSeats = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Seat seat = new Seat();
				seat.setId(rs.getInt("id"));
				seat.setName(rs.getString("name"));
				seat.setRow(rs.getInt("rows"));
				seat.setColumn(rs.getInt("column"));
				seat.setClassId(rs.getInt("classid"));
				allSeats.add(seat);
			}
		}
		con.close();
		return allSeats;
	}
	
}
