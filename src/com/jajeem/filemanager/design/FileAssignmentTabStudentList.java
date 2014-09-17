package com.jajeem.filemanager.design;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import com.alee.laf.table.WebTable;
import com.jajeem.util.i18n;

import javax.swing.JScrollPane;

public class FileAssignmentTabStudentList extends FileManagerBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileAssignmentTabStudentList frame = new FileAssignmentTabStudentList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private WebTable table;

	/**
	 * Create the frame.
	 */
	public FileAssignmentTabStudentList() {
		
		JLabel lblSelectStudentsYou = new JLabel("Select students you want to send file to :");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(lblSelectStudentsYou))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectStudentsYou)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
		);
		table = new WebTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		try {
			table.setModel(new DefaultTableModel(new Object[][] {},
					new String[] { "#", i18n.getParam("Student Name"), i18n.getParam("Ip") }) {
				
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] { false, false, false };

				@Override
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(0).setMaxWidth(45);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setMaxWidth(120);
		scrollPane.setViewportView(table);
		
		panel.setLayout(gl_panel);
		getMainContentPane().setLayout(groupLayout);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		
		pack();
	}
}
