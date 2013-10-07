package com.jajeem.util;

import java.io.File;

public class FileUtil {

	public File[] finder(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles();
	}

	public static String getInboxPath() {
		return getMyDocumentsPath() + "iCalabo\\Inbox";
	}
	
	public static String getRecorderPath() {
		return getMyDocumentsPath() + "iCalabo\\Recorder";
	}

	public static String getOutboxPath() {
		return getMyDocumentsPath() + "iCalabo\\Outbox";
	}

	public static String getMyDocumentsPath() {
		String myDocuments = "C:\\";

		return myDocuments;
	}

	public static String getReportsPath() {
		if (new File(getMyDocumentsPath() + "\\Reports").exists()) {
			return getMyDocumentsPath() + "\\Reports\\";
		} else {
			createDirectories();
			return getMyDocumentsPath() + "\\Reports\\";
		}
	}

	public static String getJapserTemplatesPath() {
		if (new File("util\\Jasper").exists()) {
			return "util\\Jasper\\";
		} else {

			return null;
		}
	}

	public static void createDirectories() {
		String reportsPath = getMyDocumentsPath() + "\\Reports";
		File file = new File(reportsPath);
		if (!file.exists())
			file.mkdir();
	}

}
