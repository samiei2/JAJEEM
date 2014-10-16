package com.jajeem.room.model;

import java.io.Serializable;

public class Attendant implements Serializable {
	private static final long serialVersionUID = 1L;
	// properties
	private int id;
	private int sessionId;
	private int studentId;
	private int seatId;
	private short status;

	public Attendant(int int1) {
		this.id = int1;
	}

	public Attendant() {
		// TODO Auto-generated constructor stub
	}

	// getter & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

}
