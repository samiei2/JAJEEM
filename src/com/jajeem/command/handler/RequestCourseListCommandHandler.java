package com.jajeem.command.handler;

import java.util.ArrayList;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.GetCourseListCommand;
import com.jajeem.command.model.RequestCourseListCommand;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;

public class RequestCourseListCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		Student student = ((RequestCourseListCommand) cmd).getStudent();
		StudentCourseService studentCourseService = new StudentCourseService();
		ArrayList<Course> courseList = studentCourseService
				.getStudentCoursesById(student.getId());

		GetCourseListCommand getCourseListCommand = new GetCourseListCommand(
				cmd.getTo(), cmd.getFrom(), Integer.parseInt(Config
						.getParam("port")), courseList);

		InstructorNoa.getServerService().send(getCourseListCommand);
	}
}
