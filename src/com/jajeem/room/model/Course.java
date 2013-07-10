package com.jajeem.room.model;

import java.io.Serializable;


public class Course implements Serializable{
	private static final long serialVersionUID = 1L;
	//properties
	private int id;
	private String name;
	
	public Course(int int1) {
		this.id = int1;
	}
	public Course() {
		// TODO Auto-generated constructor stub
	}
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
}
