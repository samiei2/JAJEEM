package com.jajeem.licensing;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import system.io.FileStream;

public class License {

	private boolean IsValid;
	private String filePath;
	private HashMap<String, String> licenseInfo;
	private LicenseModel model;

	public License(String licPath) {
		filePath = licPath;
		licenseInfo = new HashMap<>();
	}

	public License() {
		// TODO Auto-generated constructor stub
	}

	public void validate() throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey {
		License decLic = decryptLicFile();
		checkLicenseValidity(decLic);
	}

	private void checkLicenseValidity(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey {
		LicenseServer server = new LicenseServer();
		if (server.isAvailable()) {
			server.ValidateOnline(decLic);
		} else {
			ValidateOffline(decLic);
		}
	}

	private void ValidateOffline(License decLic) {

	}

	private License decryptLicFile() throws InvalidLicenseException {
		License lic;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			ArrayList<Byte> buffer = new ArrayList<>();
			int inp;
			while ((inp = fis.read()) != -1) {
				buffer.add((byte)inp);
			}

			int i = 0;
			byte[] finalBuffer = new byte[buffer.size()];
			while (i != buffer.size()) {
				finalBuffer[i] = buffer.get(i);
				i++;
			}
			
			byte[] decryptedBuffer = decrypt(finalBuffer);
			lic = convertByteToString(decryptedBuffer);
			return lic;
		} catch (Exception e) {
			throw new InvalidLicenseException();
		}
	}

	private License convertByteToString(byte[] decryptedBuffer) {
		// TODO Auto-generated method stub
		return null;
	}

	private License getLicenseFromBytes(byte[] decryptedBuffer) {
		
	}

	private byte[] decrypt(byte[] finalBuffer) {
		return finalBuffer;
	}

	public boolean isIsValid() {
		return IsValid;
	}

	public void setIsValid(boolean isValid) {
		IsValid = isValid;
	}

	public HashMap<String, String> getLicenseInfo() {
		return licenseInfo;
	}

}
