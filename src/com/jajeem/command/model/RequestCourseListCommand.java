package com.jajeem.command.model;

import com.jajeem.core.model.Student;

public class RequestCourseListCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;
	private Student student;

	public RequestCourseListCommand(String from, String to, int port, Student stu) {
		super(from, to, port);
		
		this.student = stu;
		
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
