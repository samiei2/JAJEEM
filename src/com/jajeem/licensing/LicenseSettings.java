package com.jajeem.licensing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class LicenseSettings {

	static String serverAddress = "";
	static boolean onlineValidationEnabled = false;
	
	static void load(){
		try {
			serverAddress = decrypt();
			onlineValidationEnabled = true;
		} catch (Exception e) {
			onlineValidationEnabled = false;
		}
	}
	
	private static String decrypt() throws IOException, GeneralSecurityException{
		FileInputStream fis = new FileInputStream("server.adr");
		ArrayList<Byte> buffer = new ArrayList<>();
		int inp;
		while ((inp = fis.read()) != -1) {
			buffer.add((byte) inp);
		}

		int i = 0;
		byte[] finalBuffer = new byte[buffer.size()];
		while (i != buffer.size()) {
			finalBuffer[i] = buffer.get(i);
			i++;
		}

		byte[] decryptedBuffer = LicenseEncryptionFunctions
				.decrypt(finalBuffer);
		return LicenseUtil.convertByteToString(decryptedBuffer);
	}
}
