package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.VolumeCommand;
import com.jajeem.util.Audio;

public class SetVolumeCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {

		Audio audio = new Audio();

		switch (((VolumeCommand) cmd).getType()) {
		case "mute":
			audio.mute();
			break;
		case "unMute":
			audio.unMute();
			break;

		case "switchMute":
			audio.switchMute();
			break;

		case "increase":
			audio.increaseVol();
			break;

		case "decrease":
			audio.decreaseVol();
			break;

		case "set":
			audio.setVol(((VolumeCommand) cmd).getVol());
			break;

		default:
			break;
		}
	}
}
