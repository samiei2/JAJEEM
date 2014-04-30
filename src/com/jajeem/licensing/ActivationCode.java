package com.jajeem.licensing;

import java.util.HashMap;

public class ActivationCode {
	
	String activationCode;
	HashMap<String, String> licenseInfo;
	public ActivationCode(String activationCode, HashMap<String, String> respondedLicense) {
		this.activationCode = activationCode;
		licenseInfo = respondedLicense;
	}

	private String calculateSecurityHash(String stringInput, String algorithmName)
			throws java.security.NoSuchAlgorithmException {
		String hexMessageEncode = "";
		byte[] buffer = stringInput.getBytes();
		java.security.MessageDigest messageDigest = java.security.MessageDigest
				.getInstance(algorithmName);
		messageDigest.update(buffer);
		byte[] messageDigestBytes = messageDigest.digest();
		for (int index = 0; index < messageDigestBytes.length; index++) {
			int countEncode = messageDigestBytes[index] & 0xff;
			if (Integer.toHexString(countEncode).length() == 1)
				hexMessageEncode = hexMessageEncode + "0";
			hexMessageEncode = hexMessageEncode
					+ Integer.toHexString(countEncode);
		}
		return hexMessageEncode;
	}
	
	public void getActivation(){
		
	}
	
	public void validate() throws InvalidActivationKey{
		String localActivationKey = MakeActivationKey();
		if(localActivationKey != activationCode){
			throw new InvalidActivationKey();
		}
	}

	private String MakeActivationKey() {
		return licenseInfo.get("hardwarekey") + licenseInfo.get("serialnumber");
	}
}