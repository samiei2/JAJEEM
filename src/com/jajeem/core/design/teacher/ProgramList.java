package com.jajeem.core.design.teacher;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.alee.laf.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.scroll.WebScrollPane;
import com.jajeem.util.FileUtil;
import com.jajeem.util.WinRegistry;

public class ProgramList extends com.alee.laf.rootpane.WebDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebList webList;
	private final JPanel contentPanel = new JPanel();
	private int result = StyleConstants.CANCEL_OPTION;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProgramList dialog = new ProgramList(new DefaultListModel());
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param model
	 */
	public ProgramList(final DefaultListModel model) {
		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					String pathToStartMenu = WinRegistry
							.readString(
									WinRegistry.HKEY_LOCAL_MACHINE,
									"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\\",
									"Common Start Menu")
							+ "\\Programs";

					FileUtil fileUtil = new FileUtil();
					final File[] tempfileList = fileUtil
							.finder(pathToStartMenu);
					final ArrayList<File> listOfAllLinks = new ArrayList<>();
					for (int i = 0; i < tempfileList.length; i++) {
						if (tempfileList[i].isDirectory()) {
							listOfAllLinks
									.addAll(getPath(getDirectoryContent(tempfileList[i])));
						} else {
							listOfAllLinks.add(tempfileList[i]);
						}
					}

					Collections.sort(listOfAllLinks);
					final DefaultListModel model = new DefaultListModel();
					for (int i = 0; i < listOfAllLinks.size(); i++) {
						File file = listOfAllLinks.get(i);
						if (file.getName().indexOf(".") != -1) {
							String extension = file.getName().substring(
									file.getName().indexOf("."));
							if (extension.equals(".lnk")) {
								// fileListModel.add(file.getName().substring(
								// 0, file.getName().length() - 4));
								model.addElement(file.getParentFile().getName()
										+ "\\"
										+ file.getName().substring(0,
												file.getName().length() - 4));
							}
						}
					}

					webList.setModel(model);
					webList.repaint();
				} catch (Exception e) {

				}
			}
		});
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		WebButton wbtnAdd = new WebButton();
		wbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				result = StyleConstants.OK_OPTION;
				model.addElement(getSelectedFile());
				setVisible(false);
			}
		});
		wbtnAdd.setText("Add");

		WebButton wbtnCancel = new WebButton();
		wbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				result = StyleConstants.CANCEL_OPTION;
				dispose();
			}
		});
		wbtnCancel.setText("Cancel");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_contentPanel
								.createSequentialGroup()
								.addGap(274)
								.addComponent(wbtnCancel,
										GroupLayout.PREFERRED_SIZE, 67,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(wbtnAdd,
										GroupLayout.PREFERRED_SIZE, 67,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 424,
						Short.MAX_VALUE));
		gl_contentPanel
				.setVerticalGroup(gl_contentPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPanel
										.createSequentialGroup()
										.addComponent(webScrollPane,
												GroupLayout.DEFAULT_SIZE, 213,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wbtnAdd,
																GroupLayout.PREFERRED_SIZE,
																26,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wbtnCancel,
																GroupLayout.PREFERRED_SIZE,
																26,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		webList = new WebList();
		webScrollPane.setViewportView(webList);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		}
	}

	protected ArrayList<File> getDirectoryContent(File file) {
		ArrayList<File> files = new ArrayList<>(Arrays.asList(file.listFiles()));
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				files.addAll(getDirectoryContent(files.get(i)));
			}
		}
		return files;
	}

	protected Collection<? extends File> getPath(
			ArrayList<File> directoryContent) {
		ArrayList<File> list = new ArrayList<>();
		for (int i = 0; i < directoryContent.size(); i++) {
			list.add(directoryContent.get(i));
		}
		return list;
	}

	public String getSelectedFile() {
		if (webList.getSelectedIndex() != -1) {
			return webList.getModel().getElementAt(webList.getSelectedIndex())
					.toString();
		} else {
			return null;
		}
	}

	public int getResult() {
		return result;
	}
}
