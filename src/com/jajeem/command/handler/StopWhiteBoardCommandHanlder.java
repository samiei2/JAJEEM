package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopWhiteBoardCommand;
import com.jajeem.util.ClientSession;
import com.jajeem.whiteboard.client.Client.WhiteboardClient;


public class StopWhiteBoardCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		StopWhiteBoardCommand command = (StopWhiteBoardCommand)cmd;
		WhiteboardClient wndHndl = ClientSession.getWhiteboardWindowHndl();
		if(wndHndl != null)
			wndHndl.dispose();
	}

}
