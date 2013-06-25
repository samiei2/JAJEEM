package com.jajeem.util;

import java.io.File;

public class FileUtil {

	public File[] finder(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles();
	}

}
