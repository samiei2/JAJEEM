package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Attendant;


public interface IAttendantDAO {
	Attendant create(Attendant attendant) throws SQLException;
	Attendant get(Attendant attendant) throws SQLException;
	boolean update(Attendant attendant) throws SQLException;
	boolean delete(Attendant attendant) throws SQLException;
	ArrayList<Attendant> list() throws SQLException;
}
