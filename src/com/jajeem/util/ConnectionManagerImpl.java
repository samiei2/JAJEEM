/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src.com.jajeem.util;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
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
public class ConnectionManagerImpl implements IConnectionManager {
    
    private GenericObjectPool connectionPool;
    private PoolingDataSource dataSource;
    public String connectURI; // e.g. jdbc:oracle:thin:@192.168.1.87:1521:acportal
    public String connectDriver; // e.g. oracle.jdbc.OracleDriver
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
                Logger.getLogger(ConnectionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        
       return dataSource.getConnection();
    }
    
}
