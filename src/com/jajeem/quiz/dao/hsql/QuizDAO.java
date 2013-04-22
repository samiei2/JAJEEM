package com.jajeem.quiz.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.quiz.dao.IQuizDAO;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.HSQLDBConnectionImpl;

public class QuizDAO implements IQuizDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();

	@Override
	public void create(Quiz survey) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into surveys (id,instructorid,title,category,description) values (+"
		+survey.getId()+","+survey.getInstructorId()+","+survey.getTitle()+","+survey.getCategory()+","+survey.getDescription()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Quiz survey) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update surveys set title = "+survey.getTitle()+",category = "+survey.getCategory()+",description = "+survey.getDescription()
				+"where surveys.id="+survey.getId()+" and surveys.instructorid="+survey.getInstructorId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Quiz get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from surveys "
				+"where surveys.id="+id+";";
		Quiz result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Quiz();
				result.setId(id);
				result.setTitle(rs.getString("title"));
				result.setCategory(rs.getString("category"));
				result.setDescription(rs.getString("description"));
				result.setInstructorId(rs.getInt("instructorid"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Quiz> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from seats;";
		
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
