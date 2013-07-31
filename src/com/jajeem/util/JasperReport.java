package com.jajeem.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class JasperReport {

	public static void generate(String templateName, String outputName, String query) {
		String fileName = FileUtil.getJapserTemplatesPath() + templateName
				+ ".jasper";
		String outFileName = FileUtil.getReportsPath() + outputName + ".pdf";
		HashMap hm = new HashMap();

		if (!new File(fileName).exists()) {
			return;
		}
		try {
			PreparedStatement ps = null;
			ResultSet rs = null;
			Connection con = BaseDAO.getConnection();

			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			JRResultSetDataSource jrResultSetDataSource = new JRResultSetDataSource(
					rs);
			JasperPrint print = JasperFillManager.fillReport(fileName, hm,
					jrResultSetDataSource);

			// Create a PDF exporter
			JRExporter exporter = new JRPdfExporter();

			// Configure the exporter (set output file name and print
			// object)
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

			// Export the PDF file
			exporter.exportReport();
			
			JasperViewer jv = new JasperViewer(print, false);
	        jv.setTitle("Report");
	        jv.setVisible(true);

		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}