/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.Util;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Armin
 */
public class test {

    /**
     * @param args the command line arguments
     */
    
    public void run() throws ClassNotFoundException, IOException {
        try {
            HSQLDBConnectionImpl db = new HSQLDBConnectionImpl();
            //db.ExecuteUpdate("drop table if exists person");
            //db.ExecuteUpdate("create table person (id integer, name varchar(100))");
            //db.ExecuteUpdate("insert into person values(1, 'leo')");
            //db.ExecuteUpdate("insert into person values(2, 'yui')");
            //ResultSet rs = db.ExecuteQuery("select * from person");
            
            //while(rs.next()){
            //    System.out.println("name : "+rs.getString("name"));
            //}
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
