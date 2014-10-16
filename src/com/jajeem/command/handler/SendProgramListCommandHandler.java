package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendProgramListCommand;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.core.design.teacher.InstructorNoaUtil;

public class SendProgramListCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) {
		SendProgramListCommand progCmd = (SendProgramListCommand)cmd;
		
		if (progCmd.getExeList() == null || progCmd.getLnkList() == null)
			return;
//		InstructorNoaUtil.loadProgramRestrictList(progCmd.getLnkList(),progCmd.getExeList());
	}
}
