package license4j.examples;

import com.license4j.License;
import com.license4j.LicenseValidator;

public class sample {

	public static void main(String[] args) {

		String key = "6RW2U-XPS74-2NV67-I2UR5-Z6RQT";

		String publickey = "30819f300d06092a864886f70d010101050003818d003081893032301006072a8648ce3d02002EC311215SHA512withECDSA106052b81040006031e000436f9b6680b79c06ab8c79bf0f5aa718e4f8fa0425579cc6d3d22fde7G02818100c7d23439292d1c1d37252afbe83596e8cbf9c24e0659f6d09266d87a7a81ca9805d55907eafcd6ea7615ad538ab45edb95061cbfd339b07a4bf249d43612ea2ab41c943d7ce61b89b3c6f35959afec75945461ff9fae972ff210bb1f4a5feb8d03RSA4102413SHA512withRSA9ff1b1334230f0aca2d87e03410411d8f1c4b0da4f2dba41c82b7d53e7e6bb650203010001";

		String internalString = "1376557885160";
		String nameforValidation = "License4J Trial";
		String companyforValidation = null;
		int hardwareIDMethod = 2;

		License license = LicenseValidator.validate(key, publickey,
				internalString, nameforValidation, companyforValidation,
				hardwareIDMethod);
		System.out.println("Validating with all correct parameters: "
				+ license.getValidationStatus());

		license = LicenseValidator.validate(key, publickey,
				"incorrect-internal", nameforValidation, companyforValidation,
				hardwareIDMethod);
		System.out
				.println("Validating with incorrect internal string ('incorrect-internal'): "
						+ license.getValidationStatus());

		license = LicenseValidator.validate(key, publickey, internalString,
				"incorrect-name", companyforValidation, hardwareIDMethod);
		System.out
				.println("Validating with incorrect name ('incorrect-name'): "
						+ license.getValidationStatus());

		license = LicenseValidator.validate(key, publickey, internalString,
				nameforValidation, companyforValidation, hardwareIDMethod);
		System.out
				.println("Validating with obtained local computer hardware id: "
						+ license.getValidationStatus());

		license = LicenseValidator.validate(key, publickey, internalString,
				nameforValidation, companyforValidation, hardwareIDMethod);
		System.out.println("License Validation Status before activation: "
				+ license.getValidationStatus());

		// Auto activate license.
		License activatedLicense = LicenseValidator.autoActivate(license,
				"http://online.license4j.com/c/autoactivate");
		System.out.println("License Activation Status: "
				+ activatedLicense.getActivationStatus());

	}
}
