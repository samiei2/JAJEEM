package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.util.ClientSession;

public class StartRecorderCommandHandler implements ICommandHandler {

	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartStudentRecordCommand command = (StartStudentRecordCommand)cmd;
		
		CaptureScreenToFile recorder = new CaptureScreenToFile(true);
		recorder.setClient(true);
		ClientSession.setRecorder(recorder);
		recorder.StartCaputreWithAudio("");
	}
}
