package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.table.WebTable;
import javax.swing.table.DefaultTableModel;

public class skooo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					skooo frame = new skooo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public skooo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		WebPanel webPanel = new WebPanel();
		contentPane.add(webPanel, BorderLayout.CENTER);
		
		WebTable webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		webTable.getColumnModel().getColumn(0).setResizable(false);
		webTable.getColumnModel().getColumn(1).setResizable(false);
		webTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		webTable.getColumnModel().getColumn(1).setMaxWidth(30);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webTable, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webTable, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
					.addContainerGap())
		);
		webPanel.setLayout(gl_webPanel);
	}
}
