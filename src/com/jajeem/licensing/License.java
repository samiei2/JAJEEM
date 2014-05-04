package com.jajeem.licensing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.jitsi.impl.neomedia.pulseaudio.PA.stream_request_cb_t;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicenseException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;
import com.jajeem.licensing.util.JsonConvert;
import com.jajeem.licensing.util.WindowsRegistry;
import com.sun.jna.platform.win32.WinReg;

public class License {

	private static final String LICENSE_SECRET_KEY = "LicenseSecretKey";
	private static final String ACTIVATION_CODE = "activationcode";
	private static final String LICENSE_TIME_FORMAT = "yyyy-MM-dd";
	private static final String TRIAL_TIME = "30";
	private static final String TIME_LEFT = "timeleft";
	private static final String EXPIRE_DATE = "expiredate";
	private static final String INSTALL_MILIS = "installmilis";
	private static final String START_MILIS = "startmilis";
	private static final String LAST_RUN_DATE = "lastrundate";
	private static final String INSTALL_DATE = "installdate";
	private static final String START_DATE = "startdate";
	private static final String HARDWARE_KEY = "hardwarekey";
	private static final String SERIAL_NUMBER = "serialnumber";
	private static final String JAJEEM = "Jajeem";
	private static final String UTF_8 = "UTF-8";
	private static final String LICENSE_REG_KEY = "ThreadingModel";
	private static final String IN_PROC_SERVER32 = "\\InProcServer32";
	private static final String CLSID = "CLSID\\TEST";
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
			this.initialized = true;
		}
		else{ // First run license creation
			try {
				if(new WindowsRegistry().Exists(WinReg.HKEY_CLASSES_ROOT, CLSID + getSecureHashKey() + IN_PROC_SERVER32))
					throw new InvalidLicenseException();
				FileOutputStream fos = initiateLicenseFile(licenseFile);
				
				this.initialized = true;
				JsonConvert convert = new JsonConvert();
				String json = convert.ConvertToJson(licenseInfo);
				createRegisteryKey(json);
				fos.write(encrypt(json));
				fos.flush();
				fos.close();
			} catch (IOException | InvalidLicenseException | GeneralSecurityException e) {
				JOptionPane.showMessageDialog(null, "Could not initialize license.Please contact administrator!");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	private void createRegisteryKey(String json) throws UnsupportedEncodingException, GeneralSecurityException {
		WindowsRegistry registry = new WindowsRegistry();
		String key = CLSID+getSecureHashKey()+IN_PROC_SERVER32;
		while(registry.Exists(WinReg.HKEY_CLASSES_ROOT,key)){
			key = CLSID+getSecureHashKey()+IN_PROC_SERVER32;
		}
		registry.createRootKey(key,LICENSE_REG_KEY,new String(encrypt(json),UTF_8));
	}

	private String getSecureHashKey() throws NoSuchAlgorithmException {
		String pwd = JAJEEM;
		String hkey = WindowsRegistry.getMachineGUID();
		String concat = pwd+hkey;
		
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(concat.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
	}

	private FileOutputStream initiateLicenseFile(File licenseFile)
			throws IOException, FileNotFoundException {
		licenseFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(licenseFile);
		
		this.getLicenseInfo().put(SERIAL_NUMBER, SerialNumber.generateSerialNumber());
		this.getLicenseInfo().put(HARDWARE_KEY,HardwareKey.getHardwareKeyString());
		this.getLicenseInfo().put(START_DATE, new SimpleDateFormat(LICENSE_TIME_FORMAT).format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(INSTALL_DATE, new SimpleDateFormat(LICENSE_TIME_FORMAT).format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(LAST_RUN_DATE, new SimpleDateFormat(LICENSE_TIME_FORMAT).format(new Date(System.currentTimeMillis())));
		this.getLicenseInfo().put(START_MILIS, Long.toString(System.currentTimeMillis()));
		this.getLicenseInfo().put(INSTALL_MILIS, Long.toString(System.currentTimeMillis()));
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		c.add(Calendar.DATE, Integer.parseInt(TRIAL_TIME)); // Default: Trial to 30 days
		this.getLicenseInfo().put(EXPIRE_DATE, new SimpleDateFormat(LICENSE_TIME_FORMAT).format(c.getTime()));
		this.getLicenseInfo().put(TIME_LEFT, TRIAL_TIME);
		return fos;
	}

	private byte[] encrypt(String json) throws GeneralSecurityException {
		return encrypt(LICENSE_SECRET_KEY, json);
	}

	public License() throws UninitializedLicenseException {
		throw new UninitializedLicenseException();
	}

	public void validate() throws LicenseServerErrorException, IOException,
			InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException, UninitializedLicenseException, ParseException, GeneralSecurityException {
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
			InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException, ParseException, GeneralSecurityException {
		LicenseServer server = new LicenseServer();
		if (server.isAvailable()) {
			server.setLicensingContext(this.context);
			server.ValidateOnline(decLic);
		} else {
			ValidateOffline(decLic);
		}
	}

	private void ValidateOffline(License decLic) throws InvalidLicenseException, InvalidActivationKey, ParseException, GeneralSecurityException {
//		if(!HardwareKey.getHardwareKeyString().equals(decLic.getLicenseInfo().get("hardwarekey")))
//			throw new InvalidLicenseException();
		ValidateRegisteryEntry();
		ValidateDates();
		
		String timeLeft = ValidateTimeLeft();
		getLicenseInfo().put(TIME_LEFT, timeLeft);
		
		ValidateActivationKey();
		SaveLicenseInfo();
	}

	private void SaveLicenseInfo() throws GeneralSecurityException {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			JsonConvert convert = new JsonConvert();
			String json = convert.ConvertToJson(licenseInfo);
			SaveToRegistryEntry(json);
			fos.write(encrypt(json));
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void SaveToRegistryEntry(String json) {
		// TODO Auto-generated method stub
		
	}

	private void ValidateRegisteryEntry() throws InvalidLicenseException, GeneralSecurityException {
		WindowsRegistry registry = new WindowsRegistry();
		try {
			String key = CLSID +getSecureHashKey()+IN_PROC_SERVER32;
			if(registry.Exists(WinReg.HKEY_CLASSES_ROOT,key)){
				String value = registry.ReadStringKey(WinReg.HKEY_CLASSES_ROOT,key,LICENSE_REG_KEY);
				String raw = convertByteToString(decrypt(value.getBytes()));
				JsonConvert converter = new JsonConvert();
				HashMap<String, String> registeryData = converter.ConvertFromJson(raw, HashMap.class);
				
				if (!registeryData.get(HARDWARE_KEY).equals(getLicenseInfo().get(HARDWARE_KEY))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(SERIAL_NUMBER).equals(getLicenseInfo().get(SERIAL_NUMBER))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(START_DATE).equals(getLicenseInfo().get(START_DATE))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(START_MILIS).equals(getLicenseInfo().get(START_MILIS))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(EXPIRE_DATE).equals(getLicenseInfo().get(EXPIRE_DATE))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(INSTALL_DATE).equals(getLicenseInfo().get(INSTALL_DATE))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(INSTALL_MILIS).equals(getLicenseInfo().get(INSTALL_MILIS))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(TIME_LEFT).equals(getLicenseInfo().get(TIME_LEFT))) 
					throw new InvalidLicenseException();
				if (!registeryData.get(ACTIVATION_CODE).equals(getLicenseInfo().get(ACTIVATION_CODE))) 
					throw new InvalidLicenseException();
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

	private void ValidateActivationKey() throws InvalidActivationKey, InvalidLicenseException {
		try {
			getLicenseInfo().get(ACTIVATION_CODE);
		} catch (Exception e) {
		}
		String activationCode = getLicenseInfo().get(ACTIVATION_CODE);
		if (activationCode == null
				|| activationCode == "")
//			throw new InvalidLicenseException();
			return;
		else {
			ActivationCode code = new ActivationCode(activationCode,
					getLicenseInfo());
			code.validate();
		}
	}

	private void ValidateDates() throws InvalidLicenseException, ParseException {
		Date expireDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(EXPIRE_DATE).toString());
		Date startDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(START_DATE));
		Date systemDate = new Date(System.currentTimeMillis());
		Date installDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(INSTALL_DATE));
		Date lastrunDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(LAST_RUN_DATE));
		
		if(systemDate.before(installDate) || systemDate.before(lastrunDate) || systemDate.after(expireDate))
			throw new InvalidLicenseException();
		if(startDate.after(lastrunDate))
			throw new InvalidLicenseException();
		if(expireDate.before(startDate) || expireDate.before(installDate) || expireDate.before(lastrunDate))
			throw new InvalidLicenseException();
	}

	private String ValidateTimeLeft() throws InvalidLicenseException, ParseException {
		Date expireDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(EXPIRE_DATE));
		Date startDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(START_DATE));
		Date lastrunDate = new SimpleDateFormat(LICENSE_TIME_FORMAT).parse(getLicenseInfo().get(LAST_RUN_DATE));
		long remaining = getDateDiff(startDate, expireDate, TimeUnit.DAYS) ;
		long rem1 = getDateDiff(startDate,lastrunDate,TimeUnit.DAYS);
		long rem2 = getDateDiff(lastrunDate,expireDate,TimeUnit.DAYS);
		
		if((rem1+rem2) != getDateDiff(startDate,expireDate,TimeUnit.DAYS))
			throw new InvalidLicenseException();
		if(rem2 != remaining)
			throw new InvalidLicenseException();
		
		if (remaining > 0) {
			return String.valueOf(remaining);
		}
		else{
			throw new InvalidLicenseException();
		}
		
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
		return new String(decryptedBuffer,UTF_8);
	}

	private byte[] decrypt(byte[] encrypted) throws GeneralSecurityException {
		return decrypt(LICENSE_SECRET_KEY, encrypted).getBytes();
	}
	
	private byte[] encrypt(String key, String value)
			throws GeneralSecurityException {

		byte[] raw = key.getBytes(Charset.forName(UTF_8));
		if (raw.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[16]));
		return cipher.doFinal(value.getBytes(Charset.forName(UTF_8)));
	}

	private String decrypt(String key, byte[] encrypted)
			throws GeneralSecurityException {

		byte[] raw = key.getBytes(Charset.forName(UTF_8));
		if (raw.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(
				new byte[16]));
		byte[] original = cipher.doFinal(encrypted);

		return new String(original, Charset.forName(UTF_8));
	}
		  
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
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
