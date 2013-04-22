package com.jajeem.room.model;

public class Room {
	
	//properties
	private int id;
	private String name;
	private byte signInType;
	
	//getter & setters
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
	public short getSeatSize() {
		return seatSize;
	}
	public void setSeatSize(short seatSize) {
		this.seatSize = seatSize;
	}
	private byte attendanceType;
	private short seatSize;
}
