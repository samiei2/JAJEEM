package com.jajeem.licensing;

import java.io.IOException;

import javax.swing.JOptionPane;


public class LicenseManager {
	static LicenseManager manager;
	License lic;
	static{
		
	}
	
	public LicenseManager(){
//		System.loadLibrary("/lib/jni/Sample2");
	}

	public void Validate(String licPath) {
		lic = new License(licPath);
		try {
			lic.validate();
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
