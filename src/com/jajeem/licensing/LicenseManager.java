package com.jajeem.licensing;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.jajeem.licensing.exception.LicenseServerErrorException;



public class LicenseManager {
	static LicenseManager manager;
	LicenseFrame frame;
	private boolean isOnlineValidationEnabled = false;
	LicenseValidationContext licContext;
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
		licContext = new LicenseValidationContext(this);
		try {
			licContext = licContext.validate(licPath);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		openFrameIfNeeded();
	}

	private void openFrameIfNeeded() {
		if(licContext.getLicense().isActivated())
			return;
		frame.setSerial(licContext.getLicense().getLicenseInfo().get(LicenseConstants.SERIAL_NUMBER));
		frame.setHardwareKey(licContext.getLicense().getLicenseInfo().get(LicenseConstants.HARDWARE_KEY));
		frame.setActivationKey(licContext.getLicense().getLicenseInfo().get(LicenseConstants.ACTIVATION_CODE));
		frame.setLicensorName(licContext.getLicense().getLicenseInfo().get(LicenseConstants.NAME));
		frame.setCompany(licContext.getLicense().getLicenseInfo().get(LicenseConstants.COMPANY));
		frame.setPhone(licContext.getLicense().getLicenseInfo().get(LicenseConstants.PHONE));
		frame.setTimeLeft(licContext.getLicense().getLicenseInfo().get(LicenseConstants.TIME_LEFT));
		frame.setVersion(licContext.getLicense().getLicenseInfo().get(LicenseConstants.Version));
		
		frame.setVisible(true);
	}

	boolean isOnlineValidationEnabled() {
		return isOnlineValidationEnabled;
	}

	void setOnlineValidationEnabled(boolean isOnlineValidationEnabled) {
		this.isOnlineValidationEnabled = isOnlineValidationEnabled;
	}

	public void ActivateOnline(String Name,
			String Company, String Phone) throws GeneralSecurityException, IOException, LicenseServerErrorException {
		licContext.getLicense().activateOnline(Name, Company, Phone);
	}

	public void ActivateOffline(String Name,
			String Company, String Phone) {
		
	}
}
