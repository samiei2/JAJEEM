package com.jajeem.util.firewall;

import java.util.HashMap;

public class FirewallRuleList {
	private static HashMap<String, String> rules = new HashMap<>();
	
	static {
		rules.put("1", "netsh advfirewall set allprofiles state on");
		rules.put("2", "netsh advfirewall set allprofiles firewallpolicy allowinbound,allowoutbound");
		rules.put("3", "netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound");
		rules.put("4", "netsh advfirewall firewall add rule name=\"BlockInternetTCP\" protocol=TCP dir=out remoteport=1-1024 action=block");
		rules.put("5", "netsh advfirewall firewall add rule name=\"BlockInternetUDP\" protocol=UDP dir=out remoteport=1-1024 action=block");
		rules.put("6", "netsh advfirewall firewall delete rule name=\"BlockInternetTCP\"");
		rules.put("7", "netsh advfirewall firewall delete rule name=\"BlockInternetUDP\"");
	}

	public static String[] getStartupList() {
		return new String[] { rules.get("1"),rules.get("2")};
	}
	
	public static String[] getShutdownList() {
		return new String[] { rules.get("3")};
	}
	
	public static String[] getBlockInternetList(){
		return new String[] { rules.get("4"), rules.get("5")};
	}
	
	public static String[] getUnblockInternetList(){
		return new String[] { rules.get("6"), rules.get("7")};
	}
}
