package com.jajeem.room.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.model.Attendant;


public interface IAttendantDAO {
	void create(Attendant attendant) throws SQLException;
	void update(Attendant attendant) throws SQLException;
	void delete(Attendant attendant) throws SQLException;
	Attendant get(int id) throws SQLException;
	ArrayList<Attendant> list() throws SQLException;
}
