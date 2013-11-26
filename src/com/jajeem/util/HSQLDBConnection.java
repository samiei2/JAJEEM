package com.jajeem.util;

/**
 * 
 * @author Armin
 */
public class HSQLDBConnection extends Connection {
	public HSQLDBConnection() {
		conman.connectDriver = "org.hsqldb.jdbc.JDBCDriver";
		conman.connectURI = "jdbc:hsqldb:file:jajeem.db";
	}
}
