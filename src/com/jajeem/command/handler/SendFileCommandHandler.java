package com.jajeem.command.handler;

import java.io.File;
import java.io.FileOutputStream;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendFileCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;

public class SendFileCommandHandler implements ICommandHandler{

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendFileCommand sendFileCommand = (SendFileCommand)cmd;
		File file = sendFileCommand.getFile();
		try{
			new Config();
			String clientFolder = Config.getParam("clientInbox");
			File parent = new File(clientFolder);
			if(!parent.exists())
				parent.mkdirs();
			
			if(file.isDirectory()){
				File newfile = new File(parent, file.getName());
				newfile.mkdir();
			}
			else{
				File newfile = new File(parent, file.getName());
				newfile.createNewFile();
				FileOutputStream out = new FileOutputStream(newfile);
			}
		}
		catch(Exception e){
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

}
