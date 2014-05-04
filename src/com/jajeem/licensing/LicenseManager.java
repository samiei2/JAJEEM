package com.jajeem.licensing;


public class LicenseManager {
	static LicenseManager manager;
	LicenseValidationContext lic;
	static {

	}

	public static LicenseManager getInstance() {
		if (manager == null) {
			manager = new LicenseManager();
		}
		return manager;
	}

	public static void main(String[] args) {
		LicenseManager.getInstance().Validate("jajeem.lic");
	}

	public LicenseManager() {
		// System.loadLibrary("/lib/jni/Sample2");
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
}
