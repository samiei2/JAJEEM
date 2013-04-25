package com.jajeem.util;

import java.sql.SQLException;

public class BaseDAO {
	static H2Connection connection = new H2Connection();
	public static java.sql.Connection getConnection() throws SQLException{
		return connection.getConnection();
	}
}