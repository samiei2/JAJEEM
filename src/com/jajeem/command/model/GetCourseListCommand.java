package com.jajeem.command.model;

import java.util.ArrayList;

import com.jajeem.room.model.Course;

public class GetCourseListCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;
	private ArrayList<Course> courseList;

	public GetCourseListCommand(String from, String to, int port,
			ArrayList<Course> courseList) {
		super(from, to, port);

		this.setCourseList(courseList);
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

}
