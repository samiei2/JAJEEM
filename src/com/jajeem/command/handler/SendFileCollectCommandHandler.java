package com.jajeem.command.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.alee.extended.filechooser.FileListCellEditor;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;

public class SendFileCollectCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendFileCollectCommand sendFileCommand = (SendFileCollectCommand) cmd;
		try {
			File file = new File("Outbox");
			if (!file.exists())
				return;
			ArrayList<File> filesList = getDirectoryContent(file);
			for (int i = 0; i < filesList.size(); i++) {
				if(filesList.get(i).exists())
					SendFile(filesList.get(i), sendFileCommand.getFrom());
			}
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected Collection<? extends String> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i).getPath());
		}
		return list;
	}

	protected void SendFile(final File file,final String server) {
		try {
			Thread fileSender = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Socket clientSocket = new Socket(server, 54321);
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

						System.out.println(file.getAbsolutePath() + " Sent");
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
