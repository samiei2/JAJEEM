package com.jajeem.licensing;

public class LicenseManager {

	public native boolean Authenticate(boolean silent);
	
	{
	    String archDataModel = System.getProperty("sun.arch.data.model");
	    System.loadLibrary("/lib/jni/reg"+archDataModel);
	}
}
