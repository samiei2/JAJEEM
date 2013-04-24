package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Room;

public interface IRoomDAO {
	
	Room create(Room room) throws SQLException;
	Room get(Room room) throws SQLException;
	boolean update(Room room) throws SQLException;
	boolean delete(Room room) throws SQLException;
	ArrayList<Room> list() throws SQLException;
}
