package com.jajeem.license;

import net.nicholaswilliams.java.licensing.encryption.PasswordProvider;

public class MyPrivateKeyPasswordProvider implements PasswordProvider {

	protected static final String PASSWD = "Passwd : Hey you son of a bitch,welldone,you cracked it!\nBy the way how's you mother doing?";

	@Override
	public char[] getPassword() {
		return PASSWD.toCharArray();
	}

}
