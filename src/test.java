import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.jajeem.command.model.WhiteBlackAppCommand;
import com.jajeem.util.WinRegistry;


public class test {
	public static void main(String[] args){
		try {
			WinRegistry
			.createKey(WinRegistry.HKEY_CURRENT_USER,
					"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			WinRegistry
					.writeStringValue(
							WinRegistry.HKEY_CURRENT_USER,
							"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\\DisallowRun",
							"notepad.exe",
							"notepad.exe");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cmdToRefresh1 = "secedit /refreshpolicy user_policy /enforce";
		String cmdToRefresh2 = "secedit /refreshpolicy machine_policy /enforce";
		try {
			Process process1 = Runtime.getRuntime().exec(cmdToRefresh1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Process process2 = Runtime.getRuntime().exec(cmdToRefresh2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
