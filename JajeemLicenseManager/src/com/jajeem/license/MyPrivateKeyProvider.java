package com.jajeem.license;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.nicholaswilliams.java.licensing.encryption.PrivateKeyDataProvider;
import net.nicholaswilliams.java.licensing.exception.KeyNotFoundException;

public class MyPrivateKeyProvider implements PrivateKeyDataProvider {

	@Override
	public byte[] getEncryptedPrivateKeyData() throws KeyNotFoundException {
		try {
			FileInputStream fis = new FileInputStream("private.key");
			int in;
			int i = 0;
			ArrayList<Byte> buffer = new ArrayList<>();
			while((in = fis.read())!=-1){
				buffer.add((byte) in);
				i++;
			}
			
			byte[] buff = new byte[buffer.size()];
			i = 0;
			while (i!=buffer.size()) {
				buff[i] = buffer.get(i);
				i++;
			}
			return buff;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
