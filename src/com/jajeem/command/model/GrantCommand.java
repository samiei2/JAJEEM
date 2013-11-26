package com.jajeem.command.model;

import com.jajeem.core.model.Student;

public class GrantCommand extends Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6651359089589532508L;

	private boolean granted = false;
	private Student student;

	public GrantCommand(String from, String to, int port, boolean grant,
			Student stu) {
		super(from, to, port);

		this.setGranted(grant);
		this.setStudent(stu);
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
