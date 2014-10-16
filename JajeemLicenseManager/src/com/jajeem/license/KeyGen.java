package com.jajeem.license;

import java.io.IOException;
import java.security.KeyPair;

import net.nicholaswilliams.java.licensing.encryption.RSAKeyPairGenerator;
import net.nicholaswilliams.java.licensing.encryption.RSAKeyPairGeneratorInterface.GeneratedClassDescriptor;
import net.nicholaswilliams.java.licensing.exception.AlgorithmNotSupportedException;
import net.nicholaswilliams.java.licensing.exception.InappropriateKeyException;
import net.nicholaswilliams.java.licensing.exception.InappropriateKeySpecificationException;
import net.nicholaswilliams.java.licensing.exception.RSA2048NotSupportedException;

public class KeyGen {
	public void Generate() {
		RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
		KeyPair keyPair;
		try {
			keyPair = generator.generateKeyPair();
		} catch (RSA2048NotSupportedException e) {
			return;
		}
		GeneratedClassDescriptor rkd = new GeneratedClassDescriptor()
				.setPackageName("com.jajeem.license").setClassName(
						"PrivateKeyProvider");
		GeneratedClassDescriptor ukd = new GeneratedClassDescriptor()
				.setPackageName("com.jajeem.license").setClassName(
						"PublicKeyProvider");
		GeneratedClassDescriptor pd = new GeneratedClassDescriptor()
				.setPackageName("com.jajeem.license").setClassName(
						"PasswordProvider");
		try {
			generator.saveKeyPairToProviders(keyPair, rkd, ukd,
					"key password".toCharArray());
			generator.savePasswordToProvider("key password".toCharArray(), pd);
		} catch (AlgorithmNotSupportedException | InappropriateKeyException
				| InappropriateKeySpecificationException e) {
			return;
		}
		System.out
				.println(rkd.getJavaFileContents() + "\n\n"
						+ ukd.getJavaFileContents() + "\n\n"
						+ pd.getJavaFileContents());

	}
}
