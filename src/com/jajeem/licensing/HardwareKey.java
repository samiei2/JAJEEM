package com.jajeem.licensing;

import com.jajeem.licensing.util.WindowsRegistry;

public class HardwareKey {
	static String hardwareKey = "";

	static String windowsMachineGUID = "";

	public static HardwareKey getHardwareKey() {
		return new HardwareKey();
	}

	public static String getHardwareKeyString() {
		if (hardwareKey == "") {
			return getWindowsMachineGUID();
		} else {
			return hardwareKey;
		}
	}

	public static String getWindowsMachineGUID() {
		return WindowsRegistry.getMachineGUID();
	}

	{
		hardwareKey = loadHardwareKey();
	}

	public native String loadHardwareKey();
}
