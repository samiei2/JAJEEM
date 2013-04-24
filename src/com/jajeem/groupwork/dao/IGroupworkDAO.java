package com.jajeem.groupwork.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.groupwork.model.Groupwork;

public interface IGroupworkDAO {

	Groupwork create(Groupwork groupwork) throws SQLException;
	boolean update(Groupwork groupwork) throws SQLException;
	boolean delete(Groupwork groupwork) throws SQLException;
	Groupwork get(Groupwork groupwork) throws SQLException;
	ArrayList<Groupwork> list() throws SQLException;
}
