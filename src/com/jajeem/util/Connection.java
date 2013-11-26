package com.jajeem.util;

import java.sql.SQLException;

/**
 * 
 * @author Armin
 */
public class Connection implements IConnection {
	protected ConnectionManager conman;
	int queryTimeOut = 30;

	public Connection() {
		conman = new ConnectionManager();
	}

	public java.sql.Connection getConnection() throws SQLException {
		return conman.getConnection();
	}

}
