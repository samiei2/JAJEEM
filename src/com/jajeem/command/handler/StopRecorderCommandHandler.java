package com.jajeem.command.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StartStudentRecordCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.recorder.design.CaptureScreenToFile;
import com.jajeem.util.ClientSession;

public class StopRecorderCommandHandler implements ICommandHandler {

	@SuppressWarnings("unused")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StartStudentRecordCommand command = (StartStudentRecordCommand)cmd;
		
		CaptureScreenToFile recorder = ClientSession.getRecorder();
		String fileName = CaptureScreenToFile.getFileName();
		if(recorder!=null)
			recorder.StopCapture();
		
		String server = command.getFrom();
		if(new File(fileName).exists())
			SendFileCollect(new File(fileName), server);
	}
	
	protected void SendFileCollect(final File file,final String server) {
		try {
			Thread fileSender = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Sending recorded screen to " + server);
						Socket clientSocket = new Socket(server, 54322);
						OutputStream out = clientSocket.getOutputStream();
						FileInputStream fis = new FileInputStream(file);
						byte[] info = new byte[2048];
						byte[] temp = file.getPath().getBytes();
						int len = file.getPath().length();
						for (int k = 0; k < len; k++)
							info[k] = temp[k];
						for (int k = len; k < 2048; k++)
							info[k] = 0x00;
						out.write(info, 0, 2048);

						len = file.getName().length();
						temp = file.getName().getBytes();
						for (int k = 0; k < len; k++)
							info[k] = temp[k];
						for (int k = len; k < 2048; k++)
							info[k] = 0x00;
						out.write(info, 0, 2048);

						FileInputStream inp = new FileInputStream(file);
						len = String.valueOf(inp.available()).length();
						temp = String.valueOf(inp.available()).getBytes();
						for (int k = 0; k < len; k++)
							info[k] = temp[k];
						for (int k = len; k < 2048; k++)
							info[k] = 0x00;
						out.write(info, 0, 2048);
						inp.close();

						int x;
						byte[] b = new byte[4194304];
						while ((x = fis.read(b)) > 0) {
							out.write(b, 0, x);
						}
						out.close();
						fis.close();

						System.out.println(file.getAbsolutePath() + " Sent to " + server);
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e, SendFileCollectCommandHandler.class);
						System.out.println(file.getAbsolutePath() + " Failed");
					}
				}
			});
			fileSender.start();
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			System.out.println(file.getAbsolutePath() + " Failed");
		}
	}
}
