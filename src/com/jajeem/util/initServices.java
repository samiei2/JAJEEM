package com.jajeem.util;

import com.jajeem.core.dao.h2.InstructorDAO;
import com.jajeem.core.dao.h2.StudentDAO;
import com.jajeem.core.service.InstructorService;
import com.jajeem.core.service.StudentService;
import com.jajeem.groupwork.dao.h2.GroupDAO;
import com.jajeem.groupwork.dao.h2.GroupMemberDAO;
import com.jajeem.groupwork.dao.h2.GroupworkDAO;
import com.jajeem.groupwork.service.GroupworkService;
import com.jajeem.message.dao.h2.MessageDAO;
import com.jajeem.message.service.MessageService;
import com.jajeem.quiz.dao.h2.QuizDAO;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.room.dao.h2.AttendantDAO;
import com.jajeem.room.dao.h2.RoomDAO;
import com.jajeem.room.dao.h2.SeatDAO;
import com.jajeem.room.dao.h2.SessionDAO;
import com.jajeem.room.service.RoomService;
import com.jajeem.survey.dao.h2.SurveyDAO;
import com.jajeem.survey.service.SurveyService;

public class initServices {

	/*
	 * define DAO s
	 */
	
	// Core
	StudentDAO studentDAO = new StudentDAO();
	InstructorDAO instructorDAO = new InstructorDAO();
	
	// Group work
	GroupworkDAO groupworkDAO = new GroupworkDAO();
	GroupMemberDAO groupMemberDAO = new GroupMemberDAO();
	GroupDAO groupDAO = new GroupDAO();
	
	// Message
	MessageDAO messageDAO = new MessageDAO();
	
	// Quiz
	com.jajeem.quiz.dao.h2.QuestionDAO quizQuestionDAO = new com.jajeem.quiz.dao.h2.QuestionDAO();
	QuizDAO quizDAO = new QuizDAO();
	com.jajeem.quiz.dao.h2.ResponseDAO quizResponseDAO = new com.jajeem.quiz.dao.h2.ResponseDAO();
	com.jajeem.quiz.dao.h2.RunDAO quizRunDAO = new com.jajeem.quiz.dao.h2.RunDAO();
	
	// Room
	AttendantDAO attendantDAO = new AttendantDAO();
	RoomDAO roomDAO = new RoomDAO();
	SeatDAO seatDAO = new SeatDAO();
	SessionDAO sessionDAO = new SessionDAO();
	
	// Survey
	com.jajeem.survey.dao.h2.QuestionDAO surveyQuestionDAO = new com.jajeem.survey.dao.h2.QuestionDAO();
	com.jajeem.survey.dao.h2.ResponseDAO surveyResponseDAO = new com.jajeem.survey.dao.h2.ResponseDAO();
	com.jajeem.survey.dao.h2.RunDAO surveyRunDAO = new com.jajeem.survey.dao.h2.RunDAO();
	SurveyDAO surveyDAO = new SurveyDAO();
	
	/*
	 * define Services
	 */
	
	static StudentService studentService;
	static InstructorService instructorService;
	
	static GroupworkService groupworkService;
	
	static MessageService messageService;
	
	static QuizService quizService;
	
	static RoomService roomService;
	
	static SurveyService surveyService;
	
	
	public initServices () {
		
		studentService = new StudentService();
		studentService.setStudentDAO(studentDAO);
		
		instructorService = new InstructorService();
		instructorService.setInstructorDAO(instructorDAO);
		
		groupworkService = new GroupworkService();
		groupworkService.setGroupworkDAO(groupworkDAO);
		groupworkService.setGroupDAO(groupDAO);
		groupworkService.setGroupMemberDAO(groupMemberDAO);
		
		messageService = new MessageService();
		messageService.setMessageDAO(messageDAO);
		
		quizService = new QuizService();
		quizService.setQuizDAO(quizDAO);
		quizService.setQuestionDAO(quizQuestionDAO);
		quizService.setResponseDAO(quizResponseDAO);
		quizService.setRunDAO(quizRunDAO);
		
		roomService = new RoomService();
		roomService.setRoomDAO(roomDAO);
		roomService.setSessionDAO(sessionDAO);
		roomService.setAttendantDAO(attendantDAO);
		
		surveyService = new SurveyService();
		surveyService.setSurveyDAO(surveyDAO);
		surveyService.setQuestionDAO(surveyQuestionDAO);
		surveyService.setResponseDAO(surveyResponseDAO);
		surveyService.setRunDAO(surveyRunDAO);
	}
	
}
