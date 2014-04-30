package com.jajeem.licensing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicenseException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;
import com.jajeem.licensing.util.JsonConvert;

public class License {

	private boolean IsValid = false;
	private String filePath;
	private HashMap<String, String> licenseInfo;
	private LicenseValidationContext context;
	private boolean initialized = false;

	public License(LicenseValidationContext context,String licPath) {
		filePath = licPath;
		licenseInfo = new HashMap<>();
		this.context = context;
	}

	public void initLicense() {
		// Check for last run time
		File licenseFile = new File(filePath);
		if(licenseFile.exists()){
			;
		}
		else{ // First run license creation
			try {
				licenseFile.createNewFile();
				FileOutputStream fos = new FileOutputStream(licenseFile);
				
				this.getLicenseInfo().put("serialnumber", SerialNumber.generateSerialNumber());
				this.getLicenseInfo().put("hardwarekey",HardwareKey.getHardwareKeyString());
				this.getLicenseInfo().put("startdate", new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis())));
				
				Calendar c = Calendar.getInstance();
				c.setTime(new Date(System.currentTimeMillis()));
				c.add(Calendar.DATE, 30); // Default: Trial to 30 days
				this.getLicenseInfo().put("expiredate", new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));

				this.initialized = true;
				JsonConvert convert = new JsonConvert();
				String json = convert.ConvertToJson(licenseInfo);
				fos.write(encrypt(json));
				fos.flush();
				fos.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not initialize license.Please contact administrator!");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	private byte[] encrypt(String json) {
		return json.getBytes();
	}

	public License() throws UninitializedLicenseException {
		throw new UninitializedLicenseException();
	}

	public void validate() throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException, UninitializedLicenseException {
		if(!this.isInitialized())
			throw new UninitializedLicenseException();
		decryptLicFile();
		checkLicenseValidity(this);
	}

	private boolean isInitialized() {
		return initialized ;
	}

	private void checkLicenseValidity(License decLic)
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException {
		LicenseServer server = new LicenseServer();
		if (server.isAvailable()) {
			server.setLicensingContext(this.context);
			server.ValidateOnline(decLic);
		} else {
			ValidateOffline(decLic);
		}
	}

	private void ValidateOffline(License decLic) {

	}

	private void decryptLicFile() throws InvalidLicenseException {
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
			String licStr = convertByteToString(decryptedBuffer);
			loadLicenseFromString(licStr);
		} catch (Exception e) {
			throw new InvalidLicenseException();
		}
	}

	private void loadLicenseFromString(String licStr) throws IOException {
		JsonConvert converter = new JsonConvert();
		HashMap map2 = converter.ConvertFromJson(licStr, HashMap.class);
		getLicenseInfo().putAll(map2);
	}

	private String convertByteToString(byte[] decryptedBuffer) throws UnsupportedEncodingException {
		return new String(decryptedBuffer,"UTF-8");
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
