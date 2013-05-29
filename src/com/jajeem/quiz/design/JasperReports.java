package com.jajeem.quiz.design;


import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import com.alee.laf.panel.WebPanel;

public class JasperReports extends WebPanel {

	/**
	 * Create the panel.
	 */
	public JasperReports() {
		JasperPrint jp = new JasperPrint();
		JRViewer viewer = new JRViewer(jp);
		add(viewer);
	}

}
