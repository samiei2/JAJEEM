package com.jajeem.util;

public class BaseDAO {
	
	private static H2Connection h2Connection;

	public static H2Connection getH2Connection() {
		return h2Connection;
	}

	public static void setH2Connection(H2Connection h2Connection) {
		BaseDAO.h2Connection = h2Connection;
	}

}
