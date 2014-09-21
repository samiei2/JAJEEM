package com.jajeem.licensing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.jajeem.licensing.exception.InvalidLicenseTimeException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.util.JsonConvert;



public class LicenseManager {
	static LicenseManager manager;
	LicenseFrame frame;
	private boolean isOnlineValidationEnabled = true;
	private LicenseValidationContext licContext;
	private String localLicPath;

	public static LicenseManager getInstance() {
		if (manager == null) {
			manager = new LicenseManager();
		}
		return manager;
	}

	public static void main(String[] args) {
//		FileOutputStream fos;
//		try {
//			fos = new FileOutputStream("server.adr");
//			fos.write(LicenseEncryptionFunctions.encrypt("http://register.noavaran-eng.com:65000/"));
//			fos.flush();
//			fos.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


 		LicenseManager.getInstance().Validate("jajeem.lic");
	}

	public LicenseManager() {
		// System.loadLibrary("/lib/jni/Sample2");
		LicenseSettings.load();
		frame = new LicenseFrame();
	}

	public void Validate(String licPath) {
		localLicPath = licPath;
		licContext = new LicenseValidationContext(this);
		try {
			licContext = licContext.validate(licPath);
		}
		catch (InvalidLicenseTimeException e) {
			licContext.getLicense().saveLicenseInfo();
			JOptionPane.showOptionDialog(null, e.getMessage(), "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			if(e.getMessage().contains("Tampering"))
				System.exit(0);
		}
		catch (InvalidActivationKey e) {
			licContext.getLicense().saveLicenseInfo();
			JOptionPane.showOptionDialog(null, "Invalid activation key.", "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
		}
		catch (LicenseServerErrorException e){
			licContext.getLicense().saveLicenseInfo();
			System.out.println("Error code: " + e.getErrorCode());
//			JOptionPane.showOptionDialog(null, "Server license error.", "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
		}
		catch (Exception e) {
			licContext.getLicense().saveLicenseInfo();
			JOptionPane.showOptionDialog(null, e.getMessage(), "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			e.printStackTrace();
			System.exit(0);
		}
		
		printLicenseInfo();
		
		openFrameIfNeeded();
	}

	private void printLicenseInfo() {
		System.out.println(LicenseConstants.HARDWARE_KEY + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.HARDWARE_KEY));
		System.out.println(LicenseConstants.SERIAL_NUMBER + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.SERIAL_NUMBER));
		System.out.println(LicenseConstants.START_DATE + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.START_DATE));
		System.out.println(LicenseConstants.EXPIRE_DATE + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.EXPIRE_DATE));
		System.out.println(LicenseConstants.STATUS + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.STATUS));
		System.out.println(LicenseConstants.ACTIVATION_CODE + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.ACTIVATION_CODE));
		System.out.println(LicenseConstants.USERS + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.USERS));
		System.out.println(LicenseConstants.VERSION + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.VERSION));
		System.out.println(LicenseConstants.TIME_LEFT + " : " + licContext.getLicense().getLicenseInfo().get(LicenseConstants.TIME_LEFT));
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
		String timeleft = licContext.getLicense().getLicenseInfo().get(LicenseConstants.TIME_LEFT);
		int lefttime = Integer.valueOf(timeleft);
		if (lefttime<0){
			lefttime = 0;
			frame.setContinueTrialEnabled(false);
		}
		frame.setTimeLeft(String.valueOf(lefttime));
		frame.setVersion(licContext.getLicense().getLicenseInfo().get(LicenseConstants.VERSION));
		
		frame.pack();
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
			String Company, String Phone, String activationCode) throws InvalidActivationKey, GeneralSecurityException, IOException, ParseException {
		
		char a = 'A';
		activationCode = activationCode.trim();
		String newExpireDate = activationCode.substring(14, 22);
		newExpireDate = newExpireDate.substring(0, 4)+newExpireDate.substring(4, 6)+newExpireDate.substring(6, 8);
		String decodedExpireDate = "";
		decodedExpireDate+=newExpireDate.charAt(0) - a;
		decodedExpireDate+=newExpireDate.charAt(1) - a;
		decodedExpireDate+=newExpireDate.charAt(2) - a;
		decodedExpireDate+=newExpireDate.charAt(3) - a;
		decodedExpireDate+="-";
		decodedExpireDate+=newExpireDate.charAt(4) - a;
		decodedExpireDate+=newExpireDate.charAt(5) - a;
		decodedExpireDate+="-";
		decodedExpireDate+=newExpireDate.charAt(6) - a;
		decodedExpireDate+=newExpireDate.charAt(7) - a;
		newExpireDate = decodedExpireDate;
		Date expDate = new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT).parse(newExpireDate);
		Date startDate = new Date(System.currentTimeMillis());
		String newTimeLeft = String.valueOf(getDateDiff(startDate, expDate, TimeUnit.DAYS));
		String newNoUsers = activationCode.substring(6,8);
		newNoUsers = String.valueOf(Integer.parseInt(newNoUsers,16) - 16);
		activationCode = activationCode.substring(0,6)+activationCode.substring(8,14)+activationCode.substring(22,activationCode.length());
		
		licContext.getLicense().getLicenseInfo().put(LicenseConstants.ACTIVATION_CODE, activationCode);
		ActivationCode code = new ActivationCode(activationCode,licContext.getLicense().getLicenseInfo());
		String oldStartDate = licContext.getLicense().getLicenseInfo().get(LicenseConstants.START_DATE);
		String oldExpireDate = licContext.getLicense().getLicenseInfo().get(LicenseConstants.EXPIRE_DATE);
		String oldTimeLeft = licContext.getLicense().getLicenseInfo().get(LicenseConstants.TIME_LEFT);
		try {
			code.validate();
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.NAME, Name);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.COMPANY, Company);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.PHONE, Phone);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.USERS, newNoUsers);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.START_DATE, new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT)
			.format(startDate));
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.EXPIRE_DATE, newExpireDate);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.TIME_LEFT, newTimeLeft);
			licContext.getLicense().saveLicenseInfo();
		} catch (InvalidActivationKey e) {
			//JOptionPane.showOptionDialog(null, "Invalid activation key.", "License Exception", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			licContext.getLicense().getLicenseInfo().remove(LicenseConstants.ACTIVATION_CODE);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.START_DATE,oldStartDate);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.EXPIRE_DATE,oldExpireDate);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.TIME_LEFT,oldTimeLeft);
			throw e;
		} catch (NoSuchAlgorithmException e) {
			licContext.getLicense().getLicenseInfo().remove(LicenseConstants.ACTIVATION_CODE);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.START_DATE,oldStartDate);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.EXPIRE_DATE,oldExpireDate);
			licContext.getLicense().getLicenseInfo().put(LicenseConstants.TIME_LEFT,oldTimeLeft);
			throw e;
		}
	}
	
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public LicenseValidationContext getLicContext() {
		return licContext;
	}

	public void setLicContext(LicenseValidationContext licContext) {
		this.licContext = licContext;
	}

	public void revalidate() {
		if(!localLicPath.isEmpty())
			Validate(localLicPath);
	}

	public void saveInfoOffline(String Name, String Company, String Phone) {
		licContext.getLicense().getLicenseInfo().put(LicenseConstants.NAME, Name);
		licContext.getLicense().getLicenseInfo().put(LicenseConstants.COMPANY, Company);
		licContext.getLicense().getLicenseInfo().put(LicenseConstants.PHONE, Phone);
		licContext.getLicense().saveLicenseInfo();
	}
}
