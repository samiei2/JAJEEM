package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Seat;

public interface ISeatDAO {
	
	void create(Seat seat) throws SQLException;
	void update(Seat seat) throws SQLException;
	void delete(Seat seat) throws SQLException;
	Seat get(int id) throws SQLException;
	ArrayList<Seat> list() throws SQLException;
}
