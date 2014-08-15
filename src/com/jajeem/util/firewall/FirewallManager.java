package com.jajeem.util.firewall;

import java.io.IOException;

import com.jajeem.util.cmd.CmdPromt;

public class FirewallManager {
	public void applyRule(String rule){
		CmdPromt cmd = new CmdPromt();
		try {
			cmd.runCommand(rule);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void StartUp(){
		String[] startUpRules = FirewallRuleList.getStartupList();
		applyRules(startUpRules);
	}
	
	public void Shutdown(){
		String[] shutdownRules = FirewallRuleList.getShutdownList();
		applyRules(shutdownRules);
	}
	
	public void UnblockInternet(){
		String[] unblockRules = FirewallRuleList.getUnblockInternetList();
		applyRules(unblockRules);
	}
	
	public void BlockInternet(){
		String[] blockRules = FirewallRuleList.getBlockInternetList();
		applyRules(blockRules);
	}
	
	private void applyRules(String[] list) {
		String[] rules = list;
		for (int i = 0; i < rules.length; i++) {
			applyRule(rules[i]);
		}
	}
}
