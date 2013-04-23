/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

import java.sql.SQLException;

/**
 *
 * @author Armin
 */
public class ConnectionImpl implements IConnection {
    protected ConnectionManagerImpl conman;
    private java.sql.Connection connection;
    int queryTimeOut = 30;
    
    public ConnectionImpl(){
        conman = new ConnectionManagerImpl();
    }
    
    public java.sql.Connection getConnection() throws SQLException{
        if(connection == null)
            connection = conman.getConnection();
        return connection;
    }
    
}
