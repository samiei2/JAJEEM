package com.jajeem.filemanager.design;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import com.alee.laf.button.WebButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.table.WebTable;
import javax.swing.table.DefaultTableModel;

public class FileCollect extends WebPanel {
	private WebButton wbtnCollectFiles;
	private WebButton wbtnDeleteCollectedFiles;

	/**
	 * Create the panel.
	 */
	public FileCollect() {
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		wbtnCollectFiles = new WebButton();
		wbtnCollectFiles.setText("Collect Files");
		
		wbtnDeleteCollectedFiles = new WebButton();
		wbtnDeleteCollectedFiles.setEnabled(false);
		wbtnDeleteCollectedFiles.setText("Delete Collected Files");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbtnCollectFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnDeleteCollectedFiles, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(16))
		);
		
		WebTable webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "File Name", "From"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(0).setMinWidth(55);
		webTable.getColumnModel().getColumn(0).setMaxWidth(55);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(2).setMinWidth(105);
		webTable.getColumnModel().getColumn(2).setMaxWidth(105);
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);

	}
}
