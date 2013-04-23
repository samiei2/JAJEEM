package com.jajeem.survey.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.survey.dao.IQuestionDAO;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.H2ConnectionImpl;
import com.jajeem.util.HSQLDBConnectionImpl;


public class QuestionDAO implements IQuestionDAO{

	@Override
	public void create(Question question) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into questions (id,instructorid,imgpath,surveyid,title,type,url,ans1,ans2,ans3,ans4,ans5) values ("
		+question.getId()+","+question.getInstructorId()+",'"+question.getImagePath()
				+"',"+question.getSurveyId()+",'"+question.getTitle()+"',"+question.getType()+",'"+question.getUrl()
				+"','"+question.getAsnwer1()+"','"+question.getAsnwer2()+"','"+question.getAsnwer3()+"','"+question.getAsnwer4()+"','"+question.getAsnwer5()+"');";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Question question) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "update questions set title = '"+question.getTitle()+"',imgpath = '"+question.getImagePath()
				+"',url = '"+question.getUrl()+"',instructorid = "+question.getInstructorId()+",surveyid = "+question.getSurveyId()+",type = "+question.getType()
				+", ans1 = '"+question.getAsnwer1()+"', ans2 = '"+question.getAsnwer2()+"', ans3 = '"+question.getAsnwer3()+"',ans4 = '"+question.getAsnwer4()+"', ans5 = '"+question.getAsnwer5()+"' "
				+"where questions.id="+question.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Question get(int id) throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from questions "
				+"where questions.id="+id+";";
		Question result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Question();
				result.setId(id);
				result.setInstructorId(rs.getInt("instructorid"));
				result.setTitle(rs.getString("title"));
				result.setImagePath(rs.getString("imgpath"));
				result.setSurveyId(rs.getInt("surveyid"));
				result.setType(rs.getByte("type"));
				result.setUrl(rs.getString("url"));
				result.setAsnwer1(rs.getString("ans1"));
				result.setAsnwer2(rs.getString("ans2"));
				result.setAsnwer3(rs.getString("ans3"));
				result.setAsnwer4(rs.getString("ans4"));
				result.setAsnwer5(rs.getString("ans5"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Question> list() throws SQLException {
		H2ConnectionImpl conn = new H2ConnectionImpl();
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from questions;";
		
		ArrayList<Question> allQuestions = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setInstructorId(rs.getInt("instructorid"));
				question.setTitle(rs.getString("title"));
				question.setImagePath(rs.getString("imgpath"));
				question.setSurveyId(rs.getInt("surveyid"));
				question.setType(rs.getByte("type"));
				question.setUrl(rs.getString("url"));
				question.setAsnwer1(rs.getString("ans1"));
				question.setAsnwer2(rs.getString("ans2"));
				question.setAsnwer3(rs.getString("ans3"));
				question.setAsnwer4(rs.getString("ans4"));
				question.setAsnwer5(rs.getString("ans5"));
				allQuestions.add(question);
			}
		}
		con.close();
		return allQuestions;
	}

	

}
