package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.StopWhiteBoardCommand;


public class StopWhiteBoardCommandHanlder implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		@SuppressWarnings("unused")
		StopWhiteBoardCommand command = (StopWhiteBoardCommand)cmd;
//		WhiteboardClient wndHndl = ClientSession.getWhiteboardWindowHndl();
//		if(wndHndl != null)
//			wndHndl.dispose();
	}

}
