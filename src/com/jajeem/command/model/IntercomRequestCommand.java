package com.jajeem.command.model;

import com.jajeem.core.model.Student;

public class IntercomRequestCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4780626236611125738L;
	private Student student;

	public IntercomRequestCommand(String from, String to, int port,
			Student student) {
		super(from, to, port);
		this.setStudent(student);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
