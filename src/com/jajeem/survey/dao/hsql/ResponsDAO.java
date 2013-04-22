package com.jajeem.survey.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.room.model.Seat;
import com.jajeem.survey.dao.IResponseDAO;
import com.jajeem.survey.model.Response;
import com.jajeem.util.HSQLDBConnectionImpl;

public class ResponsDAO implements IResponseDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();

	@Override
	public void create(Response respons) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into responses (id,runid,studentid,ans1,ans2,ans3,ans4,ans5) values (+"+
		respons.getId()+","+respons.getRunId()+","+respons.getStudentId()+","+respons.getAnswer1()+","+respons.getAnswer2()+","+respons.getAnswer3()+","+respons.getAnswer4()+","+respons.getAnswer5()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Response respons) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update responses set answer1 = "+respons.getAnswer1()+",answer2 = "+respons.getAnswer2()+",answer3 = "+respons.getAnswer3()+",answer4 = "+respons.getAnswer4()+",answer5 ="+respons.getAnswer5()
				+"where responses.id="+respons.getId()+" and responses.runid="+respons.getRunId()+" and responses.studentid="+ respons.getStudentId() +";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Response get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from responses "
				+"where responses.id="+id+";";
		Response result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Response();
				result.setId(id);
				result.setRunId(rs.getInt("runid"));
				result.setStudentId(rs.getInt("studentid"));
				result.setAnswer1(rs.getString("ans1"));
				result.setAnswer2(rs.getString("ans2"));
				result.setAnswer3(rs.getString("ans3"));
				result.setAnswer4(rs.getString("ans4"));
				result.setAnswer5(rs.getString("ans5"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Response> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from responses;";
		
		ArrayList<Response> allResponses = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Response response = new Response();
				response.setId(rs.getInt("id"));
				response.setRunId(rs.getInt("runid"));
				response.setStudentId(rs.getInt("studentid"));
				response.setAnswer1(rs.getString("ans1"));
				response.setAnswer1(rs.getString("ans2"));
				response.setAnswer1(rs.getString("ans3"));
				response.setAnswer1(rs.getString("ans4"));
				response.setAnswer1(rs.getString("ans5"));
				allResponses.add(response);
			}
		}
		con.close();
		return allResponses;
	}

}
