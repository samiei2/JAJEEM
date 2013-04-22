package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Room;

public interface IRoomDAO {
	
	void update(Room room) throws SQLException;
	void delete(Room room) throws SQLException;
	Room get(int id) throws SQLException;
	ArrayList<Room> list() throws SQLException;
	void create(Room room) throws SQLException;
}
