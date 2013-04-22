package com.jajeem.survey.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.survey.dao.IQuestionDAO;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.HSQLDBConnectionImpl;


public class QuestionDAO implements IQuestionDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();

	@Override
	public void create(Survey survey) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into surveys (id,title,category,description,instructor) values (+"+survey.getId()+","+survey.getTitle()+","+survey.getCategory()+","+survey.getDescription()+","+survey.getInstructorId()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Survey survey) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update surveys set title = "+survey.getTitle()+",category = "+survey.getCategory()+",description = "+survey.getDescription()+",instructorid = "+survey.getInstructorId()
				+"where surveys.id="+survey.getId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Survey get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from surveys "
				+"where surveys.id="+id+";";
		Survey result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Survey();
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
	public ArrayList<Survey> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from surveys;";
		
		ArrayList<Survey> allSurveys = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Survey survey = new Survey();
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
