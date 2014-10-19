package com.jajeem.command.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javaQuery.core.RKey;
import javaQuery.core.jqReg;
import javaQuery.core.keyType;

import com.jajeem.command.model.Command;
import com.jajeem.util.WinRegistry;


public class SetInternetCommandHandler implements ICommandHandler {

	static boolean isInternetBlocked = false;
	String[] appList = new String[]{"chrome.exe","iexplorer.exe","iexplore.exe","firefox.exe","safari.exe","opera.exe","yahoomessenger.exe"};
	
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (!isInternetBlocked) {
			killApps();
			blockApp(cmd);
			isInternetBlocked = true;
		} else {
			unblockApp(cmd);
			isInternetBlocked = false;
		}
//		if(FirewallManager.isBlocked())
//			FirewallManager.UnblockInternet();
//		else
//			FirewallManager.BlockInternet();
	}

	private void killApps() throws IOException {
		String os = System.getProperty("os.name");

		if (os.contains("Windows")) {
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
			Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
			Runtime.getRuntime().exec("taskkill /F /IM safari.exe");
			Runtime.getRuntime().exec("taskkill /F /IM opera.exe");
			Runtime.getRuntime().exec("taskkill /F /IM yahoomessenger.exe");
		} else {

			// Assuming a non Windows OS will be some version of Unix, Linux, or
			// Mac
			Runtime.getRuntime()
					.exec("kill `ps -ef | grep -i firefox | grep -v grep | awk '{print $2}'`");
			Runtime.getRuntime()
					.exec("kill `ps -ef | grep -i chrome | grep -v grep | awk '{print $2}'`");
			Runtime.getRuntime()
					.exec("kill `ps -ef | grep -i safari | grep -v grep | awk '{print $2}'`");
		}
	}

	private void unblockApp(Command cmd) throws IllegalAccessException,
			InvocationTargetException, IOException {
		String cmdToRefresh1;
		String cmdToRefresh2;

		for (int i = 0; i < appList.length; i++) {
			try{
				WinRegistry
				.deleteValue(
						WinRegistry.HKEY_CURRENT_USER,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun",
						appList[i]);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
		cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
		Runtime.getRuntime().exec(cmdToRefresh1);
		Runtime.getRuntime().exec(cmdToRefresh2);
	}

	private void blockApp(Command cmd) throws IllegalAccessException,
			InvocationTargetException, IOException {
		jqReg _jr = new jqReg();
		String message = _jr
				.jqReg(RKey.HKEY_CURRENT_USER,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer",
						keyType.Dword, "DisallowRun", "00000001");
		String cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
		String cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
		Runtime.getRuntime().exec(cmdToRefresh1);
		Runtime.getRuntime().exec(cmdToRefresh2);

		try{
			WinRegistry
					.createKey(WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		try{
			WinRegistry
					.writeStringValue(
							WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer",
							"DisallowRun", "1");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		for (int i = 0; i < appList.length; i++) {
			try{
				WinRegistry
				.writeStringValue(
						WinRegistry.HKEY_CURRENT_USER,
						"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun",
						appList[i],
						appList[i]);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
		cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
		Runtime.getRuntime().exec(cmdToRefresh1);
		Runtime.getRuntime().exec(cmdToRefresh2);
	}
}
