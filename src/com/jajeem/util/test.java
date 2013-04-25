/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

import java.io.IOException;
import java.sql.Connection;
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
            H2Connection db = new H2Connection();
            Connection con = db.getConnection();
            
    		String query = "";
  		query += "drop table if exists Student;CREATE TABLE Student ( id INT auto_increment, firstName varchar(100),middleName varchar(100)," +
  				"lastName varchar(100),username varchar(100),password varchar(100),language varchar(100));";
//    		
    		
    		try(Statement statement = con.createStatement()){
    			statement.executeUpdate(query);
//    			QuestionDAO qdao = new QuestionDAO();
//            	com.jajeem.survey.model.Question q = new com.jajeem.survey.model.Question();
//            	q.setId(1);
//            	q.setAsnwer1("123");
//            	q.setAsnwer3("13221");
//            	qdao.create(q);
//            	com.jajeem.survey.model.Question s2 = new com.jajeem.survey.model.Question();
//            	s2.setId(1);
//            	s2.setAsnwer1("425");
//            	com.jajeem.survey.model.Question seat = qdao.get(1);
            	//qdao.delete(s2);
            	
//            	ArrayList<com.jajeem.survey.model.Question> seats =  qdao.list();
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
