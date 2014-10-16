package com.jajeem.licensing.util;

import java.util.prefs.Preferences;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.WinReg.HKEY;

public class WindowsRegistry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        
	}
	
	public void CreateKey(String key,String hexString,String value){
		// Create a key and write a string
        Advapi32Util.registryCreateKey(WinReg.HKEY_LOCAL_MACHINE, key);
        Advapi32Util.registrySetStringValue(WinReg.HKEY_LOCAL_MACHINE, key, hexString, value);
	}

	public void ReadStringKey(String key){
		// Read a string
        String productName = Advapi32Util.registryGetStringValue(
            WinReg.HKEY_LOCAL_MACHINE, key, "MachineGUID");
        System.out.printf("Product Name: %s\n", productName);
	}
	
	public void DeleteKey(){
		// Delete a key
        Advapi32Util.registryDeleteKey(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\StackOverflow");
	}
	
	public void ReadIntKey(){
		// Read an int (& 0xFFFFFFFFL for large unsigned int)
        int timeout = Advapi32Util.registryGetIntValue(
            WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Windows", "ShutdownWarningDialogTimeout");
        System.out.printf("Shutdown Warning Dialog Timeout: %d (%d as unsigned long)\n", timeout, timeout & 0xFFFFFFFFL);
	}

	public static String getMachineGUID() {
		return Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE,
				"Software\\Microsoft\\Cryptography","MachineGuid");
	}

	public void createRootKey(String key, String string2, String string3) {
		Advapi32Util.registryCreateKey(WinReg.HKEY_CLASSES_ROOT, key);
        Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, key, string2, string3);
	}

	public boolean Exists(HKEY hkeyClassesRoot, String key) {
		return Advapi32Util.registryKeyExists(hkeyClassesRoot, key);
	}

	public String ReadStringKey(HKEY hkeyClassesRoot, String key,
			String licenseRegKey) {
		return Advapi32Util.registryGetStringValue(hkeyClassesRoot, key, licenseRegKey);
	}

	public void setRootKey(String key, String licenseRegKey, String encode) {
		Advapi32Util.registrySetStringValue(WinReg.HKEY_CLASSES_ROOT, key, licenseRegKey, encode);
	}
}
