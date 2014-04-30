package com.jajeem.licensing;

public class HardwareKey {
	public native String loadHardwareKey();
	static String hardwareKey;
	
	{
		hardwareKey = loadHardwareKey();
	}

	public static String getHardwareKeyString() {
		return hardwareKey;
	}

	public static HardwareKey getHardwareKey() {
		return new HardwareKey();
	}
	
	
}
