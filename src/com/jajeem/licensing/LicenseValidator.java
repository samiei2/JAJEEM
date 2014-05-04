package com.jajeem.licensing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.alee.utils.encryption.Base64;
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
		FileOutputStream fos = new FileOutputStream(decLic.getFilePath());
		JsonConvert convert = new JsonConvert();
		String json = convert.ConvertToJson(decLic.getLicenseInfo());
		saveToRegistryEntry(json);
		fos.write(LicenseEncryptionFunctions.encrypt(json));
		fos.flush();
		fos.close();
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

	public void Validate(License decLic) throws InvalidLicenseException,
			GeneralSecurityException, IOException, ParseException,
			InvalidActivationKey {
		this.decLic = decLic;
		if (!HardwareKey.getHardwareKeyString().equals(
				decLic.getLicenseInfo().get("hardwarekey"))) {
			throw new InvalidLicenseException();
		}
		validateRegisteryEntry();
		validateDates();

		String timeLeft = validateTimeLeft();
		decLic.getLicenseInfo().put(LicenseConstants.TIME_LEFT, timeLeft);

		validateActivationKey();
		saveLicenseInfo();
	}

	private void validateActivationKey() throws InvalidActivationKey,
			InvalidLicenseException {
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
		}
	}

	private void validateDates() throws InvalidLicenseException, ParseException {
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

		if (systemDate.before(installDate) || systemDate.before(lastrunDate)
				|| systemDate.after(expireDate)) {
			throw new InvalidLicenseException();
		}
		if (startDate.after(lastrunDate)) {
			throw new InvalidLicenseException();
		}
		if (expireDate.before(startDate) || expireDate.before(installDate)
				|| expireDate.before(lastrunDate)) {
			throw new InvalidLicenseException();
		}
	}

	private void validateRegisteryEntry() throws InvalidLicenseException,
			GeneralSecurityException, IOException {
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
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.SERIAL_NUMBER)
					.equals(decLic.getLicenseInfo().get(
							LicenseConstants.SERIAL_NUMBER))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.START_DATE).equals(
					decLic.getLicenseInfo().get(LicenseConstants.START_DATE))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.START_MILIS).equals(
					decLic.getLicenseInfo().get(LicenseConstants.START_MILIS))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.EXPIRE_DATE).equals(
					decLic.getLicenseInfo().get(LicenseConstants.EXPIRE_DATE))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.INSTALL_DATE).equals(
					decLic.getLicenseInfo().get(LicenseConstants.INSTALL_DATE))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.INSTALL_MILIS)
					.equals(decLic.getLicenseInfo().get(
							LicenseConstants.INSTALL_MILIS))) {
				throw new InvalidLicenseException();
			}
			if (!registeryData.get(LicenseConstants.TIME_LEFT).equals(
					decLic.getLicenseInfo().get(LicenseConstants.TIME_LEFT))) {
				throw new InvalidLicenseException();
			}
			if (registeryData.containsKey(LicenseConstants.ACTIVATION_CODE)) {
				if (!registeryData.get(LicenseConstants.ACTIVATION_CODE)
						.equals(decLic.getLicenseInfo().get(
								LicenseConstants.ACTIVATION_CODE))) {
					throw new InvalidLicenseException();
				}
			}
		} else {
			throw new InvalidLicenseException();
		}
	}

	private String validateTimeLeft() throws InvalidLicenseException,
			ParseException {
		Date expireDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.EXPIRE_DATE));
		Date startDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.START_DATE));
		Date lastrunDate = new SimpleDateFormat(
				LicenseConstants.LICENSE_TIME_FORMAT).parse(decLic
				.getLicenseInfo().get(LicenseConstants.LAST_RUN_DATE));
		long remaining = getDateDiff(startDate, expireDate, TimeUnit.DAYS);
		long rem1 = getDateDiff(startDate, lastrunDate, TimeUnit.DAYS);
		long rem2 = getDateDiff(lastrunDate, expireDate, TimeUnit.DAYS);

		if ((rem1 + rem2) != getDateDiff(startDate, expireDate, TimeUnit.DAYS)) {
			throw new InvalidLicenseException();
		}
		if (rem2 != remaining) {
			throw new InvalidLicenseException();
		}

		if (remaining > 0) {
			return String.valueOf(remaining);
		} else {
			throw new InvalidLicenseException();
		}

	}
}
