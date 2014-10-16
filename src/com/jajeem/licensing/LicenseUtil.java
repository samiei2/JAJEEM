package com.jajeem.licensing;

import java.io.UnsupportedEncodingException;

public class LicenseUtil {

	static String convertByteToString(byte[] decryptedBuffer)
			throws UnsupportedEncodingException {
		return new String(decryptedBuffer, LicenseConstants.UTF_8);
	}
}
