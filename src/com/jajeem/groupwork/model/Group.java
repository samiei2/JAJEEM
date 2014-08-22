package com.jajeem.groupwork.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

	// properties
	private int id;
	private int groupworkId;
	private String name;
	private String color;
	private List<String> StudentIps = new ArrayList<String>();
	private List<String> StudentNames = new ArrayList<String>();

	public Group(String name, int id, String color) {
		this.name = name;
		this.id = id;
		this.setColor(color);
	}

	// getter & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupworkId() {
		return groupworkId;
	}

	public void setGroupworkId(int groupworkId) {
		this.groupworkId = groupworkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getStudentIps() {
		return StudentIps;
	}

	public void setStudentIps(List<String> studentIps) {
		StudentIps = studentIps;
	}

	public List<String> getStudentNames() {
		return StudentNames;
	}

	public void setStudentNames(List<String> studentNames) {
		StudentNames = studentNames;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
