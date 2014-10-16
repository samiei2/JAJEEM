package com.jajeem.license;

import net.nicholaswilliams.java.licensing.licensor.LicenseCreator;
import net.nicholaswilliams.java.licensing.licensor.LicenseCreatorProperties;

public class LicenseManager {
	public LicenseManager() {
		LicenseCreatorProperties
				.setPrivateKeyDataProvider(new MyPrivateKeyProvider());
		LicenseCreatorProperties
				.setPrivateKeyPasswordProvider(new MyPrivateKeyPasswordProvider());
		LicenseCreator.getInstance();
	}

	public void createLicense() {
		net.nicholaswilliams.java.licensing.License license = new net.nicholaswilliams.java.licensing.License.Builder()
				.withProductKey("5565-1039-AF89-GGX7-TN31-14AL")
				.withHolder("Customer Name").withGoodBeforeDate(1123123)
				.withFeature("FEATURE1")
				.withFeature("FEATURE2", 12312).build();
		byte[] licenseData = LicenseCreator.getInstance()
				.signAndSerializeLicense(license,
						"license password".toCharArray());
	}
}
