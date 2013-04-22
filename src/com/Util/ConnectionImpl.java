/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Armin
 */
public class ConnectionImpl implements IConnection {
    protected static ConnectionManagerImpl conman;
    private java.sql.Connection connection;
    int queryTimeOut = 30;
    
    static{
        conman = new ConnectionManagerImpl();
    }
    
    public java.sql.Connection getConnection() throws SQLException{
        if(connection == null)
            connection = conman.getConnection();
        return connection;
    }
    
}
