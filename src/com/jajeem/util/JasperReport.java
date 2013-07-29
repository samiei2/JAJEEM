package com.jajeem.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;

import java.io.File;
import java.sql.Connection;
import java.util.*;

public class JasperReport {


	public static void generate(String templateName, String outputName) {
		String fileName = FileUtil.getJapserTemplatesPath() + templateName + ".jasper";
		String outFileName = FileUtil.getReportsPath()
				+ outputName + ".pdf";
		HashMap hm = new HashMap();
		
		if(!new File(fileName).exists()){
			return;
		}
		try {
			
//			hm.put("QuizRunId","1"); 
			
			// Fill the report using an empty data source
			JasperPrint print = JasperFillManager.fillReport(fileName, hm,
					(Connection) StartUp.conn);

			// Create a PDF exporter
			JRExporter exporter = new JRPdfExporter();

			// Configure the exporter (set output file name and print
			// object)
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

			// Export the PDF file
			exporter.exportReport();

		} catch (JRException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}