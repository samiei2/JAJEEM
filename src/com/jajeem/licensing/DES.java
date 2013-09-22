package com.jajeem.licensing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

public class DES {

    private static Cipher ecipher;
    private static Cipher dcipher;
    private static SecretKey key;

    // 8-byte initialization vector
    private static byte[] iv = {

    (byte) 0xB2, (byte) 0x12, (byte) 0xD5, (byte) 0xB2,

    (byte) 0x44, (byte) 0x21, (byte) 0xC3, (byte) 0xC3 };

    public static void main(String[] args) {

	try {

	    SecretKey key = KeyGenerator.getInstance("DES").generateKey();

	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

	    ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

	    dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

	    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

	    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

	    encrypt(new FileInputStream("cleartext.txt"), new FileOutputStream(
		    "encrypted.dat"));

	    decrypt(new FileInputStream("encrypted.dat"), new FileOutputStream(
		    "cleartext-reversed.txt"));

	} catch (FileNotFoundException e) {
	    System.out.println("File Not Found:" + e.getMessage());
	    return;
	} catch (InvalidAlgorithmParameterException e) {
	    System.out
		    .println("Invalid Alogorithm Parameter:" + e.getMessage());
	    return;
	} catch (NoSuchAlgorithmException e) {
	    System.out.println("No Such Algorithm:" + e.getMessage());
	    return;
	} catch (NoSuchPaddingException e) {
	    System.out.println("No Such Padding:" + e.getMessage());
	    return;
	} catch (InvalidKeyException e) {
	    System.out.println("Invalid Key:" + e.getMessage());
	    return;
	}

	try {

	    // generate secret key using DES algorithm
	    key = KeyGenerator.getInstance("DES").generateKey();

	    ecipher = Cipher.getInstance("DES");
	    dcipher = Cipher.getInstance("DES");

	    // initialize the ciphers with the given key

	    ecipher.init(Cipher.ENCRYPT_MODE, key);

	    dcipher.init(Cipher.DECRYPT_MODE, key);

	    String encrypted = encrypt("This is a classified message!");

	    String decrypted = decrypt(encrypted);

	    System.out.println("Decrypted: " + decrypted);

	} catch (NoSuchAlgorithmException e) {
	    System.out.println("No Such Algorithm:" + e.getMessage());
	    return;
	} catch (NoSuchPaddingException e) {
	    System.out.println("No Such Padding:" + e.getMessage());
	    return;
	} catch (InvalidKeyException e) {
	    System.out.println("Invalid Key:" + e.getMessage());
	    return;
	}

    }

    private static void encrypt(InputStream is, OutputStream os) {

	try {

	    byte[] buf = new byte[1024];

	    // bytes at this stream are first encoded

	    os = new CipherOutputStream(os, ecipher);

	    // read in the clear text and write to out to encrypt

	    int numRead = 0;

	    while ((numRead = is.read(buf)) >= 0) {

		os.write(buf, 0, numRead);

	    }

	    // close all streams

	    os.close();

	}

	catch (IOException e) {

	    System.out.println("I/O Error:" + e.getMessage());

	}

    }

    private static void decrypt(InputStream is, OutputStream os) {

	try {

	    byte[] buf = new byte[1024];

	    // bytes read from stream will be decrypted

	    CipherInputStream cis = new CipherInputStream(is, dcipher);

	    // read in the decrypted bytes and write the clear text to out

	    int numRead = 0;

	    while ((numRead = cis.read(buf)) >= 0) {

		os.write(buf, 0, numRead);

	    }

	    // close all streams

	    cis.close();

	    is.close();

	    os.close();

	}

	catch (IOException e) {

	    System.out.println("I/O Error:" + e.getMessage());

	}

    }

    public static String encrypt(String str) {

	try {

	 // generate secret key using DES algorithm
	    key = KeyGenerator.getInstance("DES").generateKey();

	    ecipher = Cipher.getInstance("DES");
	    dcipher = Cipher.getInstance("DES");

	    // initialize the ciphers with the given key

	    ecipher.init(Cipher.ENCRYPT_MODE, key);

	    dcipher.init(Cipher.DECRYPT_MODE, key);
	    // encode the string into a sequence of bytes using the named
	    // charset

	    // storing the result into a new byte array.

	    byte[] utf8 = str.getBytes("UTF8");

	    byte[] enc = ecipher.doFinal(utf8);

	    // encode to base64

	    enc = BASE64EncoderStream.encode(enc);

	    return new String(enc);

	}

	catch (Exception e) {

	    e.printStackTrace();

	}

	return null;

    }

    public static String decrypt(String str) {

	try {

	 // generate secret key using DES algorithm
	    key = KeyGenerator.getInstance("DES").generateKey();

	    ecipher = Cipher.getInstance("DES");
	    dcipher = Cipher.getInstance("DES");

	    // initialize the ciphers with the given key

	    ecipher.init(Cipher.ENCRYPT_MODE, key);

	    dcipher.init(Cipher.DECRYPT_MODE, key);
	    // decode with base64 to get bytes

	    byte[] dec = BASE64DecoderStream.decode(str.getBytes());

	    byte[] utf8 = dcipher.doFinal(dec);

	    // create new string based on the specified charset

	    return new String(utf8, "UTF8");

	}

	catch (Exception e) {

	    e.printStackTrace();

	}

	return null;

    }
}