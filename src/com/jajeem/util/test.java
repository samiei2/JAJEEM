/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

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
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            HSQLDBConnectionImpl db = new HSQLDBConnectionImpl();
            Connection con = db.getConnection();
    		String query = "";
    		query += "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER);";
    		
    		
    		try(Statement statement = con.createStatement()){
    			query = "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER);";
    			statement.executeUpdate(query);
    			query = "insert INTO sample_table(str_col,num_col) VALUES('Ford', 100);";
    			statement.executeUpdate(query);
    			query = "select * from sample_table;";
    			ResultSet rs = statement.executeQuery(query);
    			while(rs.next()){
    				System.out.println(rs.getString("str_col"));
    				
    			}
    		}
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
