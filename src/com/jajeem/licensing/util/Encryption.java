package com.jajeem.licensing.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	public static void main(String[] args) {
		
	}
	
	public static byte[] SHA256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes("UTF-8"));
		return hash;
	}
}
