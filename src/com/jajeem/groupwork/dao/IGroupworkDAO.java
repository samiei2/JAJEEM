package com.jajeem.groupwork.dao;

import com.jajeem.groupwork.model.Groupwork;

public interface IGroupworkDAO {

	Groupwork create(Groupwork groupwork);
	boolean update(Groupwork groupwork);
	boolean delete(Groupwork groupwork);
	Groupwork get(Groupwork groupwork);
	void list();
}
