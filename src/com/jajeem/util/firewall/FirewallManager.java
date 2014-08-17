package com.jajeem.util.firewall;

import java.io.IOException;

import com.jajeem.util.cmd.CmdPromt;

public class FirewallManager {
	private static void applyRule(String rule){
		CmdPromt cmd = new CmdPromt();
		try {
			cmd.runCommand(rule);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void StartUp(){
		String[] startUpRules = FirewallRuleList.getStartupList();
		applyRules(startUpRules);
	}
	
	public static void Shutdown(){
		String[] shutdownRules = FirewallRuleList.getShutdownList();
		applyRules(shutdownRules);
	}
	
	public static void UnblockInternet(){
		String[] unblockRules = FirewallRuleList.getUnblockInternetList();
		applyRules(unblockRules);
	}
	
	public static void BlockInternet(){
		String[] blockRules = FirewallRuleList.getBlockInternetList();
		applyRules(blockRules);
	}
	
	private static void applyRules(String[] list) {
		String[] rules = list;
		for (int i = 0; i < rules.length; i++) {
			applyRule(rules[i]);
		}
	}
	
	private static void getRuleList(){
		String rule = FirewallRuleList.getRuleList();
		CmdPromt cmd = new CmdPromt();
		try {
			String result = cmd.getCommandResults(rule);
			result = result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean containsRule(){
		return false;
	}
	
	public static boolean isBlocked(){
		return false;
	}
	
	public static void main(String[] s){
		FirewallManager.getRuleList();
	}
}
