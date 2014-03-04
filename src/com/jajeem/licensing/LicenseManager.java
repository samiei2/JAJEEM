package com.jajeem.licensing;

public class LicenseManager {

	public native int Authenticate(boolean silent);
	
	static{
//		System.load("F:\\Kar\\Jajeem\\jajeem\\lib\\jni\\reglic.dll");
//		Runtime.getRuntime().load("F:/Kar/Jajeem/jajeem/lib/jni/reglic.dll");
//	    String archDataModel = System.getProperty("sun.arch.data.model");
//	    if(archDataModel!=null && archDataModel!="")
//	    	System.loadLibrary("lib/jni/lic"+archDataModel);
//	    else
//	    	System.loadLibrary("lib/jni/lic32");
	}
	
	public LicenseManager(){
		System.loadLibrary("/lib/jni/Sample2");
	}
}
