package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.util.ClientSession;

public class StartRecorderCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartStudentRecordCommand command = (StartStudentRecordCommand)cmd;
		
		CaptureScreenToFile recorder = new CaptureScreenToFile(true);
		recorder.setClient(true);
		ClientSession.setRecorder(recorder);
		recorder.StartCaputreWithAudio("");
	}
}
