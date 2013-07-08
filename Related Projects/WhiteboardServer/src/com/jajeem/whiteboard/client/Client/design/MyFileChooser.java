package com.jajeem.whiteboard.client.Client.design;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.alee.laf.WebLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class MyFileChooser extends JFileChooser{
	public MyFileChooser() {
		try {
			UIManager.setLookAndFeel(WindowsLookAndFeel.class.getCanonicalName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}

}
