package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopStudentRecordCommand;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.util.ClientSession;

public class StopRecorderCommandHandler implements ICommandHandler {

	@SuppressWarnings({ "static-access" })
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopStudentRecordCommand command = (StopStudentRecordCommand) cmd;

		CaptureScreenToFile recorder = ClientSession.getRecorder();

		ClientSession.setReturnRecordedFileServer(command.getFrom());
		if (recorder != null) {
			recorder.StopCapture();
			// Thread.sleep(1000);
			// if(new File(fileName).exists())
			// SendFileCollect(new File(fileName), server);
		}
	}
}
