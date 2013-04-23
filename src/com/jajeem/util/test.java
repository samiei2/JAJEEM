/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajeem.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jajeem.room.dao.hsql.AttandantDAO;
import com.jajeem.room.dao.hsql.SeatDAO;
import com.jajeem.room.model.Attendant;
import com.jajeem.room.model.Seat;
import com.jajeem.survey.dao.hsql.QuestionDAO;



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
            H2ConnectionImpl db = new H2ConnectionImpl();
            Connection con = db.getConnection();
            
//    		String query = "";
//    		query += "CREATE TABLE quizes ( id INTEGER IDENTITY, title VARCHAR(256), category varchar(256),description varchar(256),instructor integer);";
//    		
    		
    		try(Statement statement = con.createStatement()){
    			//statement.executeUpdate(query);
    			SeatDAO qdao = new SeatDAO();
            	Seat q = new Seat();
            	q.setId(1);
            	q.setClassId(3);
            	q.setColumn(4);
            	q.setRow(3);
            	q.setName("seat1");
            	qdao.create(q);
            	Seat s2 = new Seat();
            	s2.setId(1);
            	s2.setClassId(1);
            	s2.setName("seat2");
            	qdao.update(s2);
            	Seat seat = qdao.get(1);
            	qdao.delete(s2);
            	
            	ArrayList<Seat> seats =  qdao.list();
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
