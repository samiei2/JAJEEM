package com.jajeem.licensing;

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

	private String MakeActivationKey() {
		return licenseInfo.get("hardwarekey") + licenseInfo.get("serialnumber");
	}

	public void validate() throws InvalidActivationKey {
		String localActivationKey = MakeActivationKey();
		if (localActivationKey != activationCode) {
			throw new InvalidActivationKey();
		}
	}
}