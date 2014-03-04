package com.jajeem.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringEscapeUtils;

public class i18n {
	private static Properties props = new Properties();

	static{
		try {
			new Config();
			File path = new File("lang/" + Config.getParam("lang") + ".txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));
			i18n.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public i18n() {
	}

	/**
	 * Loads configuration file into props
	 * 
	 * @param in
	 *            String
	 * @throws Exception
	 */
	public static void load(BufferedReader in) throws Exception {

		props.clear();
		props.load(in);
		in.close();
	}

	/**
	 * Returns the value of the key
	 * 
	 * @param key
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String getParam(String key) throws Exception {

		if (props == null || props.isEmpty()) {
			throw new Exception("lang file is not loaded before!");
		}

		String temp = key.trim().replaceAll("\\s", "")
				.toLowerCase();
		String p = props.getProperty(temp);

		if (p == null) {
			p = key;
		}

		return StringEscapeUtils.unescapeJava(p);
	}
}
