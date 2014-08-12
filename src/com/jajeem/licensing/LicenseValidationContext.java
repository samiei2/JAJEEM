package com.jajeem.licensing;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.InvalidLicenseTimeException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicenseException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;

public class LicenseValidationContext {

	private License lic;
	private LicenseManager manager;

	public LicenseValidationContext(LicenseManager licenseManager) {
		setManager(licenseManager);
	}

	public License getLicense() {
		return lic;
	}

	public LicenseValidationContext validate(String licPath) throws LicenseServerErrorException,
			IOException, InvalidLicenseException, InvalidActivationKey,
			UninitializedLicensingContextException,
			UninitializedLicenseException, ParseException,
			GeneralSecurityException, InvalidLicenseTimeException {
		if (lic == null) {
			lic = new License(this, licPath);
		}
		lic = lic.initLicense();
		lic = lic.validate();
		return this;
	}

	public LicenseManager getManager() {
		return manager;
	}

	public void setManager(LicenseManager manager) {
		this.manager = manager;
	}
}
