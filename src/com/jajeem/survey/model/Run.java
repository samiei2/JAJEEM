package com.jajeem.survey.model;

import java.io.Serializable;
import java.util.UUID;

import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.room.model.Course;

public class Run implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// properties
	private UUID id;
	private Instructor instructor = new Instructor();
	private Course course = new Course();
	private Survey survey = new Survey();
	private Student student = new Student();
	private long start;
	private long end;

	// getter & setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getInstructorId() {
		return instructor.getId();
	}

	public void setInstructorId(int instructorId) {
		this.instructor.setId(instructorId);
	}

	public int getCourseId() {
		return course.getId();
	}

	public void setCourseId(int courseId) {
		this.course.setId(courseId);
	}

	public UUID getSurveyId() {
		return survey.getId();
	}

	public void setSurveyId(UUID surveyId) {
		this.survey.setId(surveyId);
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public Survey getSurvey() {
		return this.survey;
	}

	public void setSurvey(Survey currentSurvey) {
		this.survey = currentSurvey;
	}

	public Instructor getInstructor() {
		return this.instructor;
	}

	public void setInstructor(Instructor currentInstructor) {
		this.instructor = currentInstructor;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course currentCourse) {
		this.course = currentCourse;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student privateStudent) {
		this.student = privateStudent;
	}
}
