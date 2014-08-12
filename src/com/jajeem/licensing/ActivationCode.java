package com.jajeem.licensing;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class ActivationCode {

	String activationCode;
	HashMap<String, String> licenseInfo;

	public ActivationCode(String activationCode,
			HashMap<String, String> respondedLicense) {
		this.activationCode = activationCode;
		licenseInfo = respondedLicense;
	}

	public void getActivation() {

	}

	private String MakeActivationKey() throws NoSuchAlgorithmException {
		return getMd5(licenseInfo.get("hardwarekey") + licenseInfo.get("serialnumber"));
	}

	private String getMd5(String string) throws NoSuchAlgorithmException {
		return LicenseEncryptionFunctions.getMd5(string);
	}

	public void validate() throws InvalidActivationKey, NoSuchAlgorithmException {
		String localActivationKey = MakeActivationKey().toUpperCase();
		if (!localActivationKey.equals(activationCode.toUpperCase())) {
			throw new InvalidActivationKey("Invalid activation code.");
		}
	}
}