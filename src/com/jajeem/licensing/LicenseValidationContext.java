package com.jajeem.licensing;

import java.io.IOException;

import com.jajeem.licensing.exception.InvalidLicenseException;
import com.jajeem.licensing.exception.LicenseServerErrorException;
import com.jajeem.licensing.exception.UninitializedLicenseException;
import com.jajeem.licensing.exception.UninitializedLicensingContextException;

public class LicenseValidationContext {

	private String licensePath;
	private License lic;
	
	public void validate(String licPath) throws LicenseServerErrorException, IOException, InvalidLicenseException, InvalidActivationKey, UninitializedLicensingContextException, UninitializedLicenseException {
		licensePath = licPath;
		if(lic == null)
			lic = new License(this,licPath);
		lic.initLicense();
		lic.validate();
	}

	public License getLicense() {
		return lic;
	}
}
