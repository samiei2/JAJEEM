
package com.jajeem.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 *
 * @author Armin
 */
public class ConnectionManager implements IConnectionManager {
    
    private PoolingDataSource dataSource;
    public String connectURI;
    public String connectDriver;
    public String username;
    public String passwd;
    
    @Override
    public void StartConnectionPool() throws SQLException, ClassNotFoundException{
        Class.forName(connectDriver);
        
	GenericObjectPool connectionPool = new GenericObjectPool(null);
        connectionPool.setMinIdle( 5 );
        connectionPool.setMaxActive( 10 );

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, username, passwd);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);
        dataSource = new PoolingDataSource(connectionPool);
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        if(dataSource == null) 
            try {
            	StartConnectionPool();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       return dataSource.getConnection();
    }
    
}