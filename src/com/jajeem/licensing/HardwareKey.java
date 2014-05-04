package com.jajeem.licensing;

import com.jajeem.licensing.util.WindowsRegistry;

public class HardwareKey {
	public native String loadHardwareKey();
	static String hardwareKey = "";
	static String windowsMachineGUID = "";
	
	{
		hardwareKey = loadHardwareKey();
	}

	public static String getHardwareKeyString() {
		if(hardwareKey == "")
			return getWindowsMachineGUID();
		else
			return hardwareKey;
	}

	public static HardwareKey getHardwareKey() {
		return new HardwareKey();
	}
	
	public static String getWindowsMachineGUID(){
		return WindowsRegistry.getMachineGUID();
	}
}
