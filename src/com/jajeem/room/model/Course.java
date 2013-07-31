package com.jajeem.room.model;

import java.io.Serializable;

public class Course implements Serializable {
	private static final long serialVersionUID = 1L;
	// properties
	private int id;
	private int instructorId;
	private String name;
	private String classType;
	private String level;
	private long startDate;
	private int session;
	private String day1;
	private int startTime1;
	private int endTime1;
	private String day2;
	private int startTime2;
	private int endTime2;
	private String day3;
	private int startTime3;
	private int endTime3;
	private String day4;
	private int startTime4;
	private int endTime4;
	private String day5;
	private int startTime5;
	private int endTime5;
	private String instructor;

	public Course(String name, String ins, String classType, String level,
			long startDate, int session, String day1, int startTime1,
			int endTime1, String day2, int startTime2, int endTime2,
			String day3, int startTime3, int endTime3, String day4,
			int startTime4, int endTime4, String day5, int startTime5,
			int endTime5) {
		this.name = name;
		this.instructor = ins;
		this.classType = classType;
		this.level = level;
		this.startDate = startDate;
		this.session = session;
		this.setDay1(day1);
		this.setStartTime1(startTime1);
		this.setEndTime1(endTime1);
		this.setDay2(day2);
		this.setStartTime2(startTime2);
		this.endTime2 = endTime2;
		this.day3 = day3;
		this.startTime3 = startTime3;
		this.endTime3 = endTime3;
		this.day4 = day4;
		this.startTime4 = startTime4;
		this.endTime4 = endTime4;
		this.day5 = day5;
		this.startTime5 = startTime5;
		this.endTime5 = endTime5;
	}

	public Course(String name, String ins) {
		this.name = name;
		this.instructor = ins;
	}

	public Course(String name) {
		this.name = name;
	}

	public Course() {
	}

	public int getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(int endTime2) {
		this.endTime2 = endTime2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	public int getStartTime3() {
		return startTime3;
	}

	public void setStartTime3(int startTime3) {
		this.startTime3 = startTime3;
	}

	public int getEndTime3() {
		return endTime3;
	}

	public void setEndTime3(int endTime3) {
		this.endTime3 = endTime3;
	}

	public String getDay4() {
		return day4;
	}

	public void setDay4(String day4) {
		this.day4 = day4;
	}

	public int getStartTime4() {
		return startTime4;
	}

	public void setStartTime4(int startTime4) {
		this.startTime4 = startTime4;
	}

	public int getEndTime4() {
		return endTime4;
	}

	public void setEndTime4(int endTime4) {
		this.endTime4 = endTime4;
	}

	public String getDay5() {
		return day5;
	}

	public void setDay5(String day5) {
		this.day5 = day5;
	}

	public int getStartTime5() {
		return startTime5;
	}

	public void setStartTime5(int startTime5) {
		this.startTime5 = startTime5;
	}

	public int getEndTime5() {
		return endTime5;
	}

	public void setEndTime5(int endTime5) {
		this.endTime5 = endTime5;
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

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public int getStartTime1() {
		return startTime1;
	}

	public void setStartTime1(int startTime1) {
		this.startTime1 = startTime1;
	}

	public int getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(int endTime1) {
		this.endTime1 = endTime1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public int getStartTime2() {
		return startTime2;
	}

	public void setStartTime2(int startTime2) {
		this.startTime2 = startTime2;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
}
