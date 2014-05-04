package com.jajeem.licensing;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.jajeem.licensing.util.WindowsRegistry;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import sun.awt.Win32ColorModel24;


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
	
	public static void main(String[] args) {
		LicenseManager.getInstance().Validate("jajeem.lic");
	}
}
