package com.jajeem.quiz.design;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.table.WebTable;
import javax.swing.table.DefaultTableModel;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;

public class QuestionListPanel extends WebPanel {
	private WebTable webTable;

	/**
	 * Create the panel.
	 */
	public QuestionListPanel() {
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(webScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
		);
		
		setWebTable(new WebTable());
		getWebTable().setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Type", "Point", "Content"
			}
		));
		webScrollPane.setViewportView(getWebTable());
		setLayout(groupLayout);

	}

	public WebTable getWebTable() {
		return webTable;
	}

	public void setWebTable(WebTable webTable) {
		this.webTable = webTable;
	}
}
