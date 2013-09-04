package com.jajeem.command.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.util.ClientSession;
import com.jajeem.util.Session;

public class StopRecorderCommandHandler implements ICommandHandler {

	@SuppressWarnings({ "unused", "static-access" })
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopStudentRecordCommand command = (StopStudentRecordCommand)cmd;
		
		CaptureScreenToFile recorder = ClientSession.getRecorder();
		
		if(recorder!=null)
			recorder.StopCapture();
		
		ClientSession.setRecordedFileName(CaptureScreenToFile.getFileName());
		ClientSession.setReturnRecordedFileServer(command.getFrom());
//		Thread.sleep(1000);
//		if(new File(fileName).exists())
//			SendFileCollect(new File(fileName), server);
	}
}
