/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.Util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Armin
 */
public interface IConnectionManager {
    public void StartConnectionPool() throws SQLException, ClassNotFoundException;
    public Connection getConnection() throws SQLException;
}
