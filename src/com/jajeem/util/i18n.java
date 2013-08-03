package com.jajeem.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringEscapeUtils;

public class i18n {
	private static Properties props = new Properties();

	public i18n() {
		try {
			File path = new File("lang/" + Config.getParam("lang") + ".txt");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(path), "UTF-8"));
			i18n.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		if (props == null || props.isEmpty())
			throw new Exception("lang file is not loaded before!");
		
		String p = props.getProperty(key.trim().replaceAll("\\s", "").toLowerCase());
		
		if(p == null) {
			p = key;
		}

		return StringEscapeUtils.unescapeJava(p);
	}
}
