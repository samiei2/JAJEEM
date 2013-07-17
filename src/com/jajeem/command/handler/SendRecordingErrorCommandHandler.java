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
import com.jajeem.command.model.SendRecordingSuccessCommand;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.util.Config;
import com.jajeem.util.Session;

public class SendRecordingErrorCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendRecordingSuccessCommand command = (SendRecordingSuccessCommand)cmd;
		if(Session.getRecordingList().contains(command.getFrom()))
			Session.getRecordingList().remove(command.getFrom());
	}
}
