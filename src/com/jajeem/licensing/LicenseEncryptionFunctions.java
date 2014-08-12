package com.jajeem.licensing;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jajeem.licensing.util.WindowsRegistry;

public class LicenseEncryptionFunctions {

	static byte[] decrypt(byte[] encrypted) throws GeneralSecurityException {
		return decrypt(LicenseConstants.LICENSE_SECRET_KEY, encrypted)
				.getBytes();
	}

	static String decrypt(String key, byte[] encrypted)
			throws GeneralSecurityException {

		byte[] raw = key.getBytes();
		if (raw.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(raw));
		byte[] original = cipher.doFinal(encrypted);

		return new String(original);
	}

	static byte[] encrypt(String json) throws GeneralSecurityException {
		return encrypt(LicenseConstants.LICENSE_SECRET_KEY, json);
	}

	static byte[] encrypt(String key, String value)
			throws GeneralSecurityException {

		byte[] raw = key.getBytes();
		if (raw.length != 16) {
			throw new IllegalArgumentException("Invalid key size.");
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(raw));
		return cipher.doFinal(value.getBytes());
	}

	static String getSecureHashKey() throws NoSuchAlgorithmException {
		String pwd = LicenseConstants.JAJEEM;
		String hkey = WindowsRegistry.getMachineGUID();
		String concat = pwd + hkey;

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(concat.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public static String getMd5(String string) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(string.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
		return sb.toString();
	}
}
