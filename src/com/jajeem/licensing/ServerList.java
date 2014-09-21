package com.jajeem.licensing;

import java.util.ArrayList;

public class ServerList {

	private static ArrayList<String> serverIps = new ArrayList<>();

	static {
		serverIps.add(LicenseSettings.serverAddress);
	}

	public static String getDefault() {
		return serverIps.get(0);
	}

}
