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

import com.jajeem.quiz.model.Question;
import com.jajeem.room.dao.h2.AttandantDAO;
import com.jajeem.room.dao.h2.RoomDAO;
import com.jajeem.room.dao.h2.SeatDAO;
import com.jajeem.room.dao.h2.SessionDAO;
import com.jajeem.room.model.Attendant;
import com.jajeem.room.model.Room;
import com.jajeem.room.model.Seat;
import com.jajeem.room.model.Session;
import com.jajeem.survey.dao.hsql.QuestionDAO;
import com.jajeem.survey.dao.hsql.ResponsDAO;
import com.jajeem.survey.dao.hsql.RunDAO;
import com.jajeem.survey.dao.hsql.SurveyDAO;
import com.jajeem.survey.model.Response;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;



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
            
    		String query = "";
  		query += "drop table if exists questions;CREATE TABLE questions ( id INT ,title varchar(100),type tinyint, instructorid int,imgpath varchar(300),url varchar(1000), surveyid int, ans1 varchar(1000),ans2 varchar(1000),ans3 varchar(1000),ans4 varchar(1000),ans5 varchar(1000));";
//    		
    		
    		try(Statement statement = con.createStatement()){
    			statement.executeUpdate(query);
    			QuestionDAO qdao = new QuestionDAO();
            	com.jajeem.survey.model.Question q = new com.jajeem.survey.model.Question();
            	q.setId(1);
            	q.setAsnwer1("123");
            	q.setAsnwer3("13221");
            	qdao.create(q);
            	com.jajeem.survey.model.Question s2 = new com.jajeem.survey.model.Question();
            	s2.setId(1);
            	s2.setAsnwer1("425");
            	com.jajeem.survey.model.Question seat = qdao.get(1);
            	//qdao.delete(s2);
            	
            	ArrayList<com.jajeem.survey.model.Question> seats =  qdao.list();
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
