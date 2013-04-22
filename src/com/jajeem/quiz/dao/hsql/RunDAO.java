package com.jajeem.quiz.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jajeem.room.model.Seat;
import com.jajeem.survey.dao.IRunDAO;
import com.jajeem.survey.model.Run;
import com.jajeem.util.HSQLDBConnectionImpl;


public class RunDAO implements IRunDAO{
	HSQLDBConnectionImpl conn = new HSQLDBConnectionImpl();

	@Override
	public void create(Run run) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "insert into runs (id,instructorid,sessionid,surveyid,start,end) values (+"+run.getId()+","+run.getInstructorId()
				+","+run.getSessionId()+","+run.getSurveyId()+","+run.getStart()+","+run.getEnd()+");";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public void update(Run run) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "update runs set start = "+run.getStart()+",end = "+run.getEnd()
				+"where runs.id="+run.getId()+" and runs.surveyid="+run.getSurveyId()+";";
		
		try(Statement statement = con.createStatement()){
			statement.executeUpdate(query);
		}
		con.close();
	}

	@Override
	public Run get(int id) throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from runs "
				+"where runs.id="+id+";";
		Run result = null;
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()){
				result = new Run();
				result.setId(id);
				result.setInstructorId(rs.getInt("instructorid"));
				result.setSessionId(rs.getInt("sessionid"));
				result.setSurveyId(rs.getInt("surveyid"));
				result.setStart(rs.getInt("start"));
				result.setEnd(rs.getInt("end"));
			}
		}
		con.close();
		return result;
	}

	@Override
	public ArrayList<Run> list() throws SQLException {
		Connection con = conn.getConnection();
		String query = "";
		query += "select * from runs;";
		
		ArrayList<Run> allRuns = new ArrayList<>();
		try(Statement statement = con.createStatement()){
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				Run run = new Run();
				run.setId(rs.getInt("id"));
				run.setInstructorId(rs.getInt("instructorid"));
				run.setSessionId(rs.getInt("sessionid"));
				run.setSurveyId(rs.getInt("surveyid"));
				run.setStart(rs.getInt("start"));
				run.setEnd(rs.getInt("end"));
				allRuns.add(run);
			}
		}
		con.close();
		return allRuns;
	}

}
