package com.jajeem.room.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jajeem.room.dao.IRoomDAO; 
import com.jajeem.room.model.Room;
import com.jajeem.util.HSQLDBConnectionImpl;

public class RoomDAO implements IRoomDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();
			
	
	@Override
	public void create(Room room) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into rooms (id,name,size) values (+"+room.getId()+","+room.getName()+","+room.getSeatSize()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Room room) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update rooms set name = "+room.getName()+",size = "+room.getSeatSize()
				+"where rooms.id="+room.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void delete(Room room) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "delete from rooms "
				+"where rooms.id="+room.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Room get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from rooms "
				+"where rooms.id="+id+";";
		Room result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Room();
				result.setId(id);
				result.setName(rs.getString("name"));
				result.setSeatSize(rs.getInt("size"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Room> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from rooms;";
		
		ArrayList<Room> allRooms = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Room room = new Room();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setSeatSize(rs.getInt("size"));
				allRooms.add(room);
			}
		}
		con.close();
		return allRooms;
	}

}
