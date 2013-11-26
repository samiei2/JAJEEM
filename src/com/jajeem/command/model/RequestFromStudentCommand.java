package com.jajeem.command.model;

import com.jajeem.core.model.Student;

public class RequestFromStudentCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5737607136098935225L;
	private Student student;

	public RequestFromStudentCommand(String from, String to, int port) {
		super(from, to, port);

	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
