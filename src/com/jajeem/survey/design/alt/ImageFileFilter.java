package com.jajeem.survey.design.alt;

import java.io.File;

import javax.swing.ImageIcon;

import com.alee.extended.filefilter.DefaultFileFilter;

public class ImageFileFilter extends DefaultFileFilter {

	/** The end name of files */
	private String endNames;

	/** The description of this end name */
	private String description;

	public ImageFileFilter(String endNames, String description) {
		this.endNames = endNames;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		String fileName = file.getName().toLowerCase();
		if (file.isDirectory()) {
			// the file is directory
			return true;
		}

		// the file ends with names in the endNames
		return fileName.endsWith(endNames);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
