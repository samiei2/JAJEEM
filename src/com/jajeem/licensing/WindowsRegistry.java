package com.jajeem.licensing;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class WindowsRegistry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        
	}
	
	public void CreateKey(){
		// Create a key and write a string
        Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow");
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow", "url", "http://stackoverflow.com/a/6287763/277307");
	}

	public void ReadStringKey(){
		// Read a string
        String productName = Advapi32Util.registryGetStringValue(
            WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "ProductName");
        System.out.printf("Product Name: %s\n", productName);
	}
	
	public void DeleteKey(){
		// Delete a key
        Advapi32Util.registryDeleteKey(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\StackOverflow");
	}
	
	public void ReadIntKey(){
		// Read an int (& 0xFFFFFFFFL for large unsigned int)
        int timeout = Advapi32Util.registryGetIntValue(
            WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Windows", "ShutdownWarningDialogTimeout");
        System.out.printf("Shutdown Warning Dialog Timeout: %d (%d as unsigned long)\n", timeout, timeout & 0xFFFFFFFFL);
	}
}
