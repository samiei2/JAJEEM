package com.jajeem.filemanager.design;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.table.WebTable;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.util.i18n;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class FileAssignmentTabStudentList extends FileManagerBaseDialog {

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
	private JButton btnOkButton;
	private WebCheckBox chckbxSelectAll;
	private ArrayList<String> listofIps;

	/**
	 * Create the frame.
	 */
	public FileAssignmentTabStudentList() {
		setModal(true);
		JLabel lblSelectStudentsYou = new JLabel("Select students you want to send file to :");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		chckbxSelectAll = new WebCheckBox("Select All");
		chckbxSelectAll.setOpaque(false);
		
		btnOkButton = new JButton("Ok");
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chckbxSelectAll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
							.addComponent(btnOkButton, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblSelectStudentsYou))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectStudentsYou)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxSelectAll, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOkButton))
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
		table.setEditable(false);
		scrollPane.setViewportView(table);
		
		panel.setLayout(gl_panel);
		getMainContentPane().setLayout(groupLayout);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		
		loadTable();
		pack();
		
		initEvents();
	}

	private void loadTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		if(model.getRowCount()>0)
			for (int i = 0; i < model.getRowCount(); i++)
				model.removeRow(i);
		
		JInternalFrame[] listOfAllFrame = InstructorNoa.getDesktopPane().getAllFrames();
		for (int i = 0; i < listOfAllFrame.length; i++) {
			model.addRow(new Object[]{table.getRowCount() == 0 ? 1 : table
					.getRowCount() + 1,listOfAllFrame[i].getClientProperty("username"),listOfAllFrame[i].getClientProperty("ip")});
		}
	}

	private void initEvents() {
		getCloseButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		btnOkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listofIps = new ArrayList<String>();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				for (int i : table.getSelectedRows()) {
					listofIps.add(model.getValueAt(i, 2).toString());
				}
				dispose();
			}
		});
		
		chckbxSelectAll.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(chckbxSelectAll.isSelected())
					table.selectAll();
				else
					table.clearSelection();
			}
		});
	}

	protected ArrayList<String> getListofIps() {
		return listofIps;
	}
}
