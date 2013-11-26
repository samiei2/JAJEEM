package com.jajeem.room.model;

import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	// properties
	private int id;
	private String name;
	private byte signInType;
	private byte attendanceType;
	private int seatSize;

	public Room(int int1) {
		this.id = int1;
	}

	public Room() {
		// TODO Auto-generated constructor stub
	}

	// getter & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getSignInType() {
		return signInType;
	}

	public void setSignInType(byte signInType) {
		this.signInType = signInType;
	}

	public byte getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(byte attendanceType) {
		this.attendanceType = attendanceType;
	}

	public int getSeatSize() {
		return seatSize;
	}

	public void setSeatSize(int seatSize) {
		this.seatSize = seatSize;
	}
}
