package com.jajeem.util;

import com.jajeem.util.H2Connection;

public class StartUp {
	
	static H2Connection conn;
	
	public StartUp () {
		conn = new H2Connection();
		BaseDAO.setH2Connection(conn);
	}
	
}
