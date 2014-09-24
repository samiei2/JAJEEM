package com.jajeem.licensing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.alee.utils.encryption.Base64;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.InvalidLicenseTimeException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicenseException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;
import com.jajeem.licensing.util.JsonConvert;
import com.jajeem.licensing.util.WindowsRegistry;
import com.sun.jna.platform.win32.WinReg;

public class License {

	private String filePath;
	private HashMap<String, String> licenseInfo;
	private LicenseValidationContext context;
	private boolean initialized = false;
	private boolean isActivated = false;

	public License() {

	}

	public License(LicenseValidationContext context, String licPath) {
		setFilePath(licPath);
		licenseInfo = new HashMap<>();
		this.setContext(context);
	}

	private void checkLicenseValidity()
			throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey,
			UninitializedLicensingContextException, ParseException,
			GeneralSecurityException, InvalidLicenseTimeException {
		LicenseServer server = new LicenseServer();
		try{
			if (server.isAvailable() && LicenseManager.getInstance().isOnlineValidationEnabled()) {
				server.setLicensingContext(this.getContext());
				server.validateOnline(this);
			} 
		}
		catch(LicenseServerErrorException e)
		{
			validateOffline(this);
			throw e;
		}
		validateOffline(this);
	}

	private void createRegisteryKey(String json)
			throws UnsupportedEncodingException, GeneralSecurityException {
		WindowsRegistry registry = new WindowsRegistry();
		String key = LicenseConstants.CLSID
				+ LicenseEncryptionFunctions.getSecureHashKey()
				+ LicenseConstants.IN_PROC_SERVER32;
		while (registry.Exists(WinReg.HKEY_CLASSES_ROOT, key)) {
			key = LicenseConstants.CLSID
					+ LicenseEncryptionFunctions.getSecureHashKey()
					+ LicenseConstants.IN_PROC_SERVER32;
		}
		registry.createRootKey(key, LicenseConstants.LICENSE_REG_KEY,
				Base64.encode((LicenseEncryptionFunctions.encrypt(json))));
	}

	private void decryptLicFile() throws InvalidLicenseException, IOException,
			GeneralSecurityException {
		FileInputStream fis = new FileInputStream(getFilePath());
		ArrayList<Byte> buffer = new ArrayList<>();
		int inp;
		while ((inp = fis.read()) != -1) {
			buffer.add((byte) inp);
		}

		int i = 0;
		byte[] finalBuffer = new byte[buffer.size()];
		while (i != buffer.size()) {
			finalBuffer[i] = buffer.get(i);
			i++;
		}

		byte[] decryptedBuffer = LicenseEncryptionFunctions
				.decrypt(finalBuffer);
		String licStr = LicenseUtil.convertByteToString(decryptedBuffer);
		loadLicenseFromString(licStr);
	}

	public LicenseValidationContext getContext() {
		return context;
	}

	public String getFilePath() {
		return filePath;
	}

	public HashMap<String, String> getLicenseInfo() {
		return licenseInfo;
	}

	private FileOutputStream initiateLicenseFile(File licenseFile)
			throws IOException, FileNotFoundException {
		licenseFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(licenseFile);

		this.getLicenseInfo().put(LicenseConstants.SERIAL_NUMBER,
				SerialNumber.generateSerialNumber());
		this.getLicenseInfo().put(LicenseConstants.HARDWARE_KEY,
				HardwareKey.getHardwareKeyString());
		this.getLicenseInfo().put(
				LicenseConstants.START_DATE,
				new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
						.format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(
				LicenseConstants.INSTALL_DATE,
				new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
						.format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(
				LicenseConstants.LAST_RUN_DATE,
				new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
						.format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(LicenseConstants.START_MILIS,
				Long.toString(System.currentTimeMillis()));
		this.getLicenseInfo().put(LicenseConstants.INSTALL_MILIS,
				Long.toString(System.currentTimeMillis()));
		this.getLicenseInfo().put(LicenseConstants.USERS, LicenseConstants.NUMUSERS);
		this.getLicenseInfo().put(LicenseConstants.STATUS, LicenseConstants.TRIAL);
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DATE, Integer.parseInt(LicenseConstants.TRIAL_TIME)); // Default:
																				// Trial
																				// to
		// 30 days
		this.getLicenseInfo().put(
				LicenseConstants.EXPIRE_DATE,
				new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
						.format(c.getTime()));
		this.getLicenseInfo().put(LicenseConstants.TIME_LEFT,
				LicenseConstants.TRIAL_TIME);
		this.getLicenseInfo().put(LicenseConstants.VERSION, LicenseConstants.APPVERSIONNO);
		return fos;
	}

	public License initLicense() {
		// Check for last run time
		File licenseFile = new File(getFilePath());
		if (licenseFile.exists()) {
			this.initialized = true;
		} else { // First run license creation
			try {
				if (new WindowsRegistry().Exists(
						WinReg.HKEY_CLASSES_ROOT,
						LicenseConstants.CLSID
								+ LicenseEncryptionFunctions.getSecureHashKey()
								+ LicenseConstants.IN_PROC_SERVER32)) {
					throw new InvalidLicenseException("License tampering detected!");
				}
				FileOutputStream fos = initiateLicenseFile(licenseFile);

				this.initialized = true;
				JsonConvert convert = new JsonConvert();
				String json = convert.ConvertToJson(licenseInfo);
				fos.write(LicenseEncryptionFunctions.encrypt(json));
				fos.flush();
				fos.close();
				createRegisteryKey(json);
			} catch (IOException | InvalidLicenseException
					| GeneralSecurityException e) {
				JOptionPane
						.showMessageDialog(null,
								"Could not initialize license.Please contact administrator!");
				e.printStackTrace();
				System.exit(0);
			}
		}
		return this;
	}

	private boolean isInitialized() {
		return initialized;
	}

	private void loadLicenseFromString(String licStr) throws IOException {
		JsonConvert converter = new JsonConvert();
		HashMap map2 = converter.ConvertFromJson(licStr, HashMap.class);
		getLicenseInfo().putAll(map2);
	}

	public void setContext(LicenseValidationContext context) {
		this.context = context;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setLicenseInfo(HashMap<String, String> respondedLicense) {
		this.licenseInfo = respondedLicense;
	}

	public License validate() throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey,
			UninitializedLicensingContextException,
			UninitializedLicenseException, ParseException,
			GeneralSecurityException, InvalidLicenseTimeException {
		if (!this.isInitialized()) {
			throw new UninitializedLicenseException("Uninitialized license exception!");
		}
		decryptLicFile();
		checkLicenseValidity();
		return this;
	}

	private void validateOffline(License decLic)
			throws InvalidLicenseException, InvalidActivationKey,
			ParseException, GeneralSecurityException, IOException, InvalidLicenseTimeException {
		LicenseValidator validator = new LicenseValidator();
		decLic = validator.validate(decLic);
		if(getLicenseInfo().containsKey(LicenseConstants.ACTIVATION_CODE))
			setActivated(true);
	}

	public boolean isActivated() {
		return isActivated;
	}

	void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public void activateOnline(String name, String company, String phone) throws GeneralSecurityException, IOException, LicenseServerErrorException {
		LicenseServer licServer = new LicenseServer();
		this.getLicenseInfo().put(LicenseConstants.NAME, name);
		this.getLicenseInfo().put(LicenseConstants.COMPANY, company);
		this.getLicenseInfo().put(LicenseConstants.PHONE, phone);
		saveLicenseInfo();
		licServer.activateOnline(this);
	}

	public void saveLicenseInfo() {
		try{
			FileOutputStream fos = new FileOutputStream(this.getFilePath());
			JsonConvert convert = new JsonConvert();
			String json = convert.ConvertToJson(this.getLicenseInfo());
			saveToRegistryEntry(json);
			fos.write(LicenseEncryptionFunctions.encrypt(json));
			fos.flush();
			fos.close();
		}
		catch(Exception e){
			JOptionPane.showOptionDialog(null, e.getMessage(), "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
		}
	}
	
	private void saveToRegistryEntry(String json)
			throws GeneralSecurityException {
		WindowsRegistry registry = new WindowsRegistry();
		String key = LicenseConstants.CLSID
				+ LicenseEncryptionFunctions.getSecureHashKey()
				+ LicenseConstants.IN_PROC_SERVER32;
		registry.setRootKey(key, LicenseConstants.LICENSE_REG_KEY,
				Base64.encode((LicenseEncryptionFunctions.encrypt(json))));
	}
}
