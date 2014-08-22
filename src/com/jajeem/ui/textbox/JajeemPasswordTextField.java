package com.jajeem.ui.textbox;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebPasswordFieldUI;
import com.alee.laf.text.WebTextFieldUI;

public class JajeemPasswordTextField extends JPasswordField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Uri;
	public JajeemPasswordTextField(String uri) {
		setUri(uri);
		updateUI();
	}
	
	public JajeemPasswordTextField() {
		updateUI();
	}

	@Override
	public void updateUI() {
		if (getUI() == null || !(getUI() instanceof WebPasswordFieldUI)) {
			try {
				setUI(new JajeemPasswordTexFieldUI(this,Uri));
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
