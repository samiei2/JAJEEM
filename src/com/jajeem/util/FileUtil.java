package com.jajeem.util;

import java.io.File;
import java.io.InputStream;

public class FileUtil {

	public File[] finder(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles();
	}

	public static String getMyDocumentsPath() {
		String myDocuments = null;

		try {
			Process p = Runtime
					.getRuntime()
					.exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
			p.waitFor();

			InputStream in = p.getInputStream();
			byte[] b = new byte[in.available()];
			in.read(b);
			in.close();

			myDocuments = new String(b);
			myDocuments = myDocuments.split("\\s\\s+")[4];

		} catch (Throwable t) {
			t.printStackTrace();
		}
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
