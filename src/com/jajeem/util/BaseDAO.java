package com.jajeem.util;

import java.sql.SQLException;

public class BaseDAO {

	private static H2Connection h2Connection = new H2Connection();// TODO remove
																	// new h2..

	public static H2Connection getH2Connection() {
		return h2Connection;
	}

	public static java.sql.Connection getConnection() throws SQLException {
		return h2Connection.getConnection();
	}

	public static void setH2Connection(H2Connection h2Connection) {
		BaseDAO.h2Connection = h2Connection;
	}

}
