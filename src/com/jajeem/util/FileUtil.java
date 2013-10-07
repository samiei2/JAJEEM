package com.jajeem.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
	public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            if(!dest.exists()){
            	dest.createNewFile();
            }
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }
        finally {
            is.close();
            os.close();
        }
    }

}
