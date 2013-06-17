package com.jajeem.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Audio {

	static Process process;
	private String path;
	
	
	public Audio() {
		if(System.getProperty("os.arch").contains("64")) {
			
//			path = Audio.class.getResource("/com/jajeem/extra/nircmdc_64.exe").getPath();
			path = "util/nircmdc_64.exe";
		} else {
//			path = Audio.class.getResource("/com/jajeem/extra/nircmdc_32.exe").getPath();
			path = "util/nircmdc_32.exe";
		}
		
	}

	public void mute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "1");
		processBuilder.start();
	}

	public void unMute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "0");
		processBuilder.start();
	}

	public void switchMute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "2");
		processBuilder.start();
	}
	
	public void increaseVol() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"changesysvolume", "2000");
		processBuilder.start();
	}
	
	public void decreaseVol() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"changesysvolume", "-5000");
		processBuilder.start();
	}
	
	public void setVol(int vol) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"setsysvolume", Integer.toString(vol));
		processBuilder.start();
	}
	

}
