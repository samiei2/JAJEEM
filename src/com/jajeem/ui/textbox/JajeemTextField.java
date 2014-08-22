package com.jajeem.ui.textbox;

import java.awt.Insets;

import javax.swing.JTextField;

import com.alee.laf.text.WebTextFieldUI;

public class JajeemTextField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Uri;
	public JajeemTextField(String uri) {
		setUri(uri);
		updateUI();
	}
	
	public JajeemTextField() {
		updateUI();
	}

	@Override
	public void updateUI() {
		if (getUI() == null || !(getUI() instanceof WebTextFieldUI)) {
			try {
				setUI(new JajeemTexFieldUI(this,Uri));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			setUI(getUI());
		}
		invalidate();
	}

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}
}
