package com.jajeem.licensing;

import javax.swing.JTextField;


public class LicenseManager {
	static LicenseManager manager;
	LicenseFrame frame;
	private boolean isOnlineValidationEnabled = false;
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
		frame = new LicenseFrame();
	}

	public void Validate(String licPath) {
		lic = new LicenseValidationContext(this);
		try {
			lic.validate(licPath);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		openFrameIfNeeded();
	}

	private void openFrameIfNeeded() {
		if(lic.getLicense().isActivated())
			return;
		frame.setSerial(lic.getLicense().getLicenseInfo().get(LicenseConstants.SERIAL_NUMBER));
		frame.setHardwareKey(lic.getLicense().getLicenseInfo().get(LicenseConstants.HARDWARE_KEY));
		frame.setActivationKey(lic.getLicense().getLicenseInfo().get(LicenseConstants.ACTIVATION_CODE));
		frame.setLicensorName(lic.getLicense().getLicenseInfo().get(LicenseConstants.NAME));
		frame.setCompany(lic.getLicense().getLicenseInfo().get(LicenseConstants.COMPANY));
		frame.setPhone(lic.getLicense().getLicenseInfo().get(LicenseConstants.PHONE));
		
		frame.setVisible(true);
	}

	boolean isOnlineValidationEnabled() {
		return isOnlineValidationEnabled;
	}

	void setOnlineValidationEnabled(boolean isOnlineValidationEnabled) {
		this.isOnlineValidationEnabled = isOnlineValidationEnabled;
	}

	public void ActivateOnline(JTextField textField_Name,
			JTextField textField_Company, JTextField textField_Phone) {
		// TODO Auto-generated method stub
		
	}

	public void ActivateOffline(JTextField textField_Name,
			JTextField textField_Company, JTextField textField_Phone) {
		// TODO Auto-generated method stub
		
	}
}
