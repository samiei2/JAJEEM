package com.jajeem.command.handler;

import java.util.ArrayList;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.GetCourseListCommand;
import com.jajeem.core.design.account.CourseListDialog;
import com.jajeem.room.model.Course;

public class GetCourseListCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws Exception {
		ArrayList<Course> courseList = ((GetCourseListCommand) cmd)
				.getCourseList();

		new CourseListDialog(courseList);
	}
}
