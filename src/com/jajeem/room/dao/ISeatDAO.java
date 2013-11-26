package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Seat;

public interface ISeatDAO {

	Seat create(Seat seat) throws SQLException;

	Seat get(Seat seat) throws SQLException;

	boolean update(Seat seat) throws SQLException;

	boolean delete(Seat seat) throws SQLException;

	ArrayList<Seat> list() throws SQLException;
}
