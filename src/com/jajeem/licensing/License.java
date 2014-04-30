package com.jajeem.licensing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.jitsi.impl.neomedia.pulseaudio.PA.stream_request_cb_t;

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
				this.getLicenseInfo().put("installdate", new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis())));
				this.getLicenseInfo().put("lastrundate", new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis())));
				this.getLicenseInfo().put("startmilis", Long.toString(System.currentTimeMillis()));
				this.getLicenseInfo().put("installmilis", Long.toString(System.currentTimeMillis()));
				
				Calendar c = Calendar.getInstance();
				c.setTime(new Date(System.currentTimeMillis()));
				c.add(Calendar.DATE, 30); // Default: Trial to 30 days
				this.getLicenseInfo().put("expiredate", new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
				this.getLicenseInfo().put("timeleft", "30");
				

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

	private void ValidateOffline(License decLic) throws InvalidLicenseException, InvalidActivationKey {
		if(!HardwareKey.getHardwareKeyString().equals(decLic.getLicenseInfo().get("hardwarekey")))
			throw new InvalidLicenseException();
		
		ValidateDates();
		
		String timeLeft = ValidateTimeLeft();
		getLicenseInfo().put("timeleft", timeLeft);
		
		ValidateActivationKey();
		
		if (IsValid) {
			
		}
	}

	private void ValidateActivationKey() throws InvalidActivationKey, InvalidLicenseException {
		try {
			getLicenseInfo().get("activationcode");
		} catch (Exception e) {
		}
		String activationCode = getLicenseInfo().get("activationcode");
		if (activationCode == null
				|| activationCode == "")
			throw new InvalidLicenseException();
		else {
			ActivationCode code = new ActivationCode(activationCode,
					getLicenseInfo());
			code.validate();
		}
	}

	private void ValidateDates() throws InvalidLicenseException {
		Date expireDate = Date.valueOf(getLicenseInfo().get("expiredate"));
		Date startDate = Date.valueOf(getLicenseInfo().get("startdate"));
		Date systemDate = new Date(System.currentTimeMillis());
		Date installDate = Date.valueOf(getLicenseInfo().get("installdate"));
		Date lastrunDate = Date.valueOf(getLicenseInfo().get("lastrundate"));
		
		if(systemDate.before(installDate) || systemDate.before(lastrunDate) || systemDate.after(expireDate))
			throw new InvalidLicenseException();
		if(startDate.after(lastrunDate))
			throw new InvalidLicenseException();
		if(expireDate.before(startDate) || expireDate.before(installDate) || expireDate.before(lastrunDate))
			throw new InvalidLicenseException();
	}

	private String ValidateTimeLeft() throws InvalidLicenseException {
		Date expireDate = Date.valueOf(getLicenseInfo().get("expiredate"));
		Date startDate = Date.valueOf(getLicenseInfo().get("startdate"));
		Date lastrunDate = Date.valueOf(getLicenseInfo().get("lastrundate"));
		int remaining = expireDate.compareTo(startDate);
		int rem1 = lastrunDate.compareTo(startDate);
		int rem2 = expireDate.compareTo(lastrunDate);
		
		if((rem1+rem2) != expireDate.compareTo(startDate))
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
