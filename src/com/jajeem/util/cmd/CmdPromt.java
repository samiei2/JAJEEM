package com.jajeem.util.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.jajeem.licensing.ServerList;
import com.jajeem.util.Threading.ThreadManager;

public class CmdPromt {
	public void runCommand(String command) throws IOException{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",
				command);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		final BufferedReader r = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		ThreadManager.getInstance().run(new Runnable() {
			
			@Override
			public void run() {
				try {
					printFromReader(r);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void printFromReader(BufferedReader r) throws IOException {
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) {
				break;
			}
			System.out.println(line);
		}
	}
}
