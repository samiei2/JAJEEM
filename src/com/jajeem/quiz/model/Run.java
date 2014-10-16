package com.jajeem.quiz.model;

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
	private Quiz quiz = new Quiz();
	private Student student = new Student();
	private int score;
	private long start;
	private long end;
	private Course course = new Course();

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

	public UUID getQuizId() {
		return quiz.getId();
	}

	public void setQuizId(UUID quizId) {
		this.quiz.setId(quizId);
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

	public int getStudentId() {
		return student.getId();
	}

	public void setStudentId(int studentId) {
		this.student.setId(studentId);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setCourse(Course currentCourse) {
		this.course = currentCourse;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourseId(int courseid) {
		this.course.setId(courseid);
	}

	public int getCourseId() {
		return this.course.getId();
	}
}
