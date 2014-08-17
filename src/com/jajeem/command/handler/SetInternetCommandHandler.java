package com.jajeem.command.handler;

import com.jajeem.command.model.Command;
import com.jajeem.util.firewall.FirewallManager;

public class SetInternetCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
//		String os = System.getProperty("os.name");
//
//		if (os.contains("Windows")) {
//			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
//			Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe");
//			Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
//			Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
//			Runtime.getRuntime().exec("taskkill /F /IM safari.exe");
//			Runtime.getRuntime().exec("taskkill /F /IM opera.exe");
//		} else {
//
//			// Assuming a non Windows OS will be some version of Unix, Linux, or
//			// Mac
//			Runtime.getRuntime()
//					.exec("kill `ps -ef | grep -i firefox | grep -v grep | awk '{print $2}'`");
//			Runtime.getRuntime()
//					.exec("kill `ps -ef | grep -i chrome | grep -v grep | awk '{print $2}'`");
//			Runtime.getRuntime()
//					.exec("kill `ps -ef | grep -i safari | grep -v grep | awk '{print $2}'`");
//		}
		if(FirewallManager.isBlocked())
			FirewallManager.UnblockInternet();
		else
			FirewallManager.BlockInternet();
	}
}
