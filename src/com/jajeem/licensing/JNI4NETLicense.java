package com.jajeem.licensing;

import java.io.File;
import java.io.IOException;

import licensejni.License;

import net.sf.jni4net.Bridge;

public class JNI4NETLicense {
	public static int Validate(boolean silent) throws IOException{
		Bridge.setVerbose(true);
		Bridge.init();
		File assembly = new File("lib/jni/LicenseJNI.j4n.dll");
		File assemblyLicense = new File("lib/jni/LicenseJNI.dll");
		Bridge.LoadAndRegisterAssemblyFrom(assembly);
		Bridge.LoadAndRegisterAssemblyFrom(assemblyLicense);
		License license = new License();
		return license.Validate(silent);
	}
}
