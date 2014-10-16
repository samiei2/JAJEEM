package com.jajeem.licensing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.alee.utils.encryption.Base64;
import com.jajeem.licensing.exception.InvalidLicenseTimeException;
import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.util.JsonConvert;
import com.jajeem.licensing.util.WindowsRegistry;
import com.sun.jna.platform.win32.WinReg;

public class LicenseValidator {
	License decLic;

	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	private void saveLicenseInfo() throws GeneralSecurityException, IOException {
		decLic.saveLicenseInfo();
	}

	public License validate(License decLic) throws InvalidLicenseException, GeneralSecurityException, InvalidActivationKey, IOException, ParseException, InvalidLicenseTimeException {
		this.decLic = decLic;
		
		if (decLic.getLicenseInfo().get(LicenseConstants.STATUS).equals("-1")) {
			throw new InvalidLicenseException("License tampering detected.License disabled!");
		}
		
		if (decLic.getLicenseInfo().get(LicenseConstants.STATUS).equals("1")) {
			throw new InvalidLicenseException("Trial license disabled.For further info contact support.");
		}
		
		if (decLic.getLicenseInfo().get(LicenseConstants.STATUS).equals("3")) {
			throw new InvalidLicenseException("Active license disabled.For further info contact support.");
		}
		
		if (decLic.getLicenseInfo().get(LicenseConstants.STATUS).equals("4")) {
			throw new InvalidLicenseException("License deactivate.For further info contact support.");
		}
		
		if (!HardwareKey.getHardwareKeyString().equals(
				decLic.getLicenseInfo().get(LicenseConstants.HARDWARE_KEY))) {
			throw new InvalidLicenseException("Invalid Hardware key!");
		}
		try{
			validateRegisteryEntry();
			
			validateDates();
	
			validateTimeLeft();
	
			validateActivationKey();
		}
		catch (GeneralSecurityException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (InvalidActivationKey e) {
			
			throw e;
		} catch (ParseException e) {
			throw e;
		} catch (InvalidLicenseTimeException e) {
			saveLicenseInfo();
			throw e;
		}
		saveLicenseInfo();
		return decLic;
	}

	private void validateActivationKey() throws InvalidActivationKey,
			InvalidLicenseException, NoSuchAlgorithmException {
		try {
			decLic.getLicenseInfo().get(LicenseConstants.ACTIVATION_CODE);
		} catch (Exception e) {
		}
		String activationCode = decLic.getLicenseInfo().get(
				LicenseConstants.ACTIVATION_CODE);
		if (activationCode == null || activationCode == "") {
			// throw new InvalidLicenseException();
			return;
		} else {
			ActivationCode code = new ActivationCode(activationCode,
					decLic.getLicenseInfo());
			code.validate();
			decLic.setActivated(true);
		}
	}

	private void validateDates() throws InvalidLicenseException, ParseException, InvalidLicenseTimeException {
		Date expireDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.EXPIRE_DATE).toString());
		Date startDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.START_DATE));
		Date systemDate = new Date(System.currentTimeMillis());
		Date installDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.INSTALL_DATE));
		Date lastrunDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.LAST_RUN_DATE));
		

		if (systemDate.before(installDate) || systemDate.before(lastrunDate)) {
			throw new InvalidLicenseTimeException("Time Tampering Detected!");
		}
//		if (systemDate.after(expireDate)){
//			throw new InvalidLicenseTimeException("License expired!");
//		}
		if (startDate.after(lastrunDate)) {
			throw new InvalidLicenseTimeException("Time Tampering Detected!");
		}
		if (expireDate.before(startDate) || expireDate.before(installDate)
				|| expireDate.before(lastrunDate)) {
			throw new InvalidLicenseTimeException("Time Tampering Detected!");
		}
	}

	private void validateRegisteryEntry() throws InvalidLicenseException,
			GeneralSecurityException, IOException, InvalidActivationKey {
		WindowsRegistry registry = new WindowsRegistry();
		String key = LicenseConstants.CLSID
				+ LicenseEncryptionFunctions.getSecureHashKey()
				+ LicenseConstants.IN_PROC_SERVER32;
		if (registry.Exists(WinReg.HKEY_CLASSES_ROOT, key)) {
			String value = registry.ReadStringKey(WinReg.HKEY_CLASSES_ROOT,
					key, LicenseConstants.LICENSE_REG_KEY);
			String raw = LicenseUtil
					.convertByteToString(LicenseEncryptionFunctions
							.decrypt(Base64.decode(value)));
			JsonConvert converter = new JsonConvert();
			HashMap<String, String> registeryData = converter.ConvertFromJson(
					raw, HashMap.class);

			if (!registeryData.get(LicenseConstants.HARDWARE_KEY).equals(
					decLic.getLicenseInfo().get(LicenseConstants.HARDWARE_KEY))) {
				throw new InvalidLicenseException("Invalid Hardware key!");
			}
			if (!registeryData.get(LicenseConstants.SERIAL_NUMBER)
					.equals(decLic.getLicenseInfo().get(
							LicenseConstants.SERIAL_NUMBER))) {
				throw new InvalidLicenseException("Invalid Serial Number!");
			}
//			if (!registeryData.get(LicenseConstants.START_DATE).equals(
//					decLic.getLicenseInfo().get(LicenseConstants.START_DATE))) {
//				throw new InvalidLicenseException("Invalid start date!");
//			}
			if (!registeryData.get(LicenseConstants.START_MILIS).equals(
					decLic.getLicenseInfo().get(LicenseConstants.START_MILIS))) {
				throw new InvalidLicenseException("Invalid start milis!");
			}
//			if (!registeryData.get(LicenseConstants.EXPIRE_DATE).equals(
//					decLic.getLicenseInfo().get(LicenseConstants.EXPIRE_DATE))) {
//				throw new InvalidLicenseException("Invalid expire date!");
//			}
			if (!registeryData.get(LicenseConstants.INSTALL_DATE).equals(
					decLic.getLicenseInfo().get(LicenseConstants.INSTALL_DATE))) {
				throw new InvalidLicenseException("Invalid install date!");
			}
			if (!registeryData.get(LicenseConstants.INSTALL_MILIS)
					.equals(decLic.getLicenseInfo().get(
							LicenseConstants.INSTALL_MILIS))) {
				throw new InvalidLicenseException("Invalid install milis!");
			}
//			if (!registeryData.get(LicenseConstants.TIME_LEFT).equals(
//					decLic.getLicenseInfo().get(LicenseConstants.TIME_LEFT))) {
//				throw new InvalidLicenseException("Invalid remaining time!");
//			}
			if (registeryData.containsKey(LicenseConstants.ACTIVATION_CODE)) {
				if (!registeryData.get(LicenseConstants.ACTIVATION_CODE)
						.equals(decLic.getLicenseInfo().get(
								LicenseConstants.ACTIVATION_CODE))) {
					throw new InvalidActivationKey("Invalid activation code!");
				}
			}
		} else {
			throw new InvalidLicenseException("Invalid License!");
		}
	}

	private String validateTimeLeft() throws InvalidLicenseException,
			ParseException, InvalidLicenseTimeException {
		Date expireDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.EXPIRE_DATE));
		Date startDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.START_DATE));
		Date lastrunDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.LAST_RUN_DATE));
		Date systemTime = new Date(System.currentTimeMillis());
		long remaining = getDateDiff(systemTime, expireDate, TimeUnit.DAYS);
		long usedTime = getDateDiff(startDate, systemTime, TimeUnit.DAYS);
		long remainedTime = getDateDiff(systemTime, expireDate, TimeUnit.DAYS);
		decLic.getLicenseInfo().put(LicenseConstants.TIME_LEFT, String.valueOf(remainedTime));
		decLic.getLicenseInfo().put(LicenseConstants.LAST_RUN_DATE, new SimpleDateFormat(LicenseConstants.LICENSE_TIME_FORMAT).format(lastrunDate));

		if ((usedTime + remainedTime + 1) != getDateDiff(startDate, expireDate, TimeUnit.DAYS)) {
			throw new InvalidLicenseTimeException("License Expired.");
		}
		
		if (remainedTime != remaining) {
			throw new InvalidLicenseTimeException("Time tampering detected!");
		}

		if (remaining > 0) {
			return String.valueOf(remaining);
		} else {
			throw new InvalidLicenseTimeException("License Expired.");
		}
	}
}
