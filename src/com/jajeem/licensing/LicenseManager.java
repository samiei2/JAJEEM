package com.jajeem.licensing;

import java.io.IOException;

import javax.swing.JOptionPane;


public class LicenseManager {
	static LicenseManager manager;
	LicenseValidationContext lic;
	static{
		
	}
	
	public LicenseManager(){
//		System.loadLibrary("/lib/jni/Sample2");
	}

	public void Validate(String licPath) {
		lic = new LicenseValidationContext();
		
		try {
			lic.validate(licPath);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static LicenseManager getInstance() {
		if(manager == null)
			manager = new LicenseManager();
		return manager;
	}
}
