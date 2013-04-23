package com.jajeem.quiz.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.quiz.dao.IQuestionDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.HSQLDBConnectionImpl;


public class QuestionDAO implements IQuestionDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();

	@Override
	public void create(Quiz quiz) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into quizes (id,title,category,description,instructor) values ("+quiz.getId()+",'"+quiz.getTitle()
				+"','"+quiz.getCategory()+"','"+quiz.getDescription()+"',"+quiz.getInstructorId()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Quiz survey) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update quizes set title = "+survey.getTitle()+",category = "+survey.getCategory()+",description = "+survey.getDescription()+",instructorid = "+survey.getInstructorId()
				+"where quizes.id="+survey.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Quiz get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from quizes "
				+"where quizes.id="+id+";";
		Quiz result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Quiz();
				result.setId(id);
				result.setInstructorId(rs.getInt("instructorid"));
				result.setTitle(rs.getString("title"));
				result.setDescription(rs.getString("description"));
				result.setCategory(rs.getString("category"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Quiz> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from quizes;";
		
		ArrayList<Quiz> allSurveys = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Quiz survey = new Quiz();
				survey.setId(rs.getInt("id"));
				survey.setInstructorId(rs.getInt("instructorid"));
				survey.setTitle(rs.getString("title"));
				survey.setCategory(rs.getString("category"));
				survey.setDescription(rs.getString("description"));
				allSurveys.add(survey);
			}
		}
		con.close();
		return allSurveys;
	}

	

}
