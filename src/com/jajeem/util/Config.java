package com.jajeem.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class Config {

	private static Properties props = new Properties();

	public Config() {
		try {
			Config.load(Config.class
					.getResourceAsStream("/com/jajeem/conf/conf.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads configuration file into props
	 * 
	 * @param filename
	 *            String
	 * @throws Exception
	 */
	public static void load(InputStream filename) throws Exception {

		InputStreamReader propFile = new InputStreamReader(filename);

		props.clear();
		props.load(propFile);

		propFile.close();
		propFile = null;
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
			throw new Exception("Configuration file is not loaded before!");

		String p = props.getProperty(key);

		return p;
	}

	/**
	 * sets the value of the key
	 * 
	 * @param key
	 *            String
	 * @param value
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static void setParam(String key, String value) throws Exception {

		if (props == null || props.isEmpty())
			throw new Exception("Configuration file is not loaded before!");

		props.put(key, value);

		URL url = Config.class.getResource("/com/jajeem/conf/conf.properties");
		FileOutputStream out = new FileOutputStream(new File(url.toURI()));
		props.store(out, null);
		out.close();
	}
}
