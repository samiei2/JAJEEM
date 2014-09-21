package com.jajeem.survey.design.alt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alee.laf.table.WebTable;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.SurveyService;
import com.jajeem.util.WindowResizeAdapter;

public class Survey_OpenDialog extends BaseSurveyOpenFrame {

	private JPanel contentPane;
	private CustomSurveyButton wbtnCancel;
	private CustomSurveyButton wbtnOpen;
	private WebTable wbTblQuestion;
	private WebTable wbTblSurvey;
	
	private ArrayList<Survey> surveyList = new ArrayList<>();
	private Survey_Main parentFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Survey_OpenDialog frame = new Survey_OpenDialog(null);
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
	public Survey_OpenDialog(Survey_Main frame) {
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(
						Survey_OpenDialog.class
								.getResource("/icons/noa_en/survey.png")));
		parentFrame = frame;
		setAlwaysOnTop(true);
		setLocationByPlatform(true);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		wbtnOpen = new CustomSurveyButton("/icons/noa_en/quizopenbutton.png");
		wbtnOpen.setUndecorated(true);
		wbtnCancel = new CustomSurveyButton("/icons/noa_en/quizcancelbutton.png");
		wbtnCancel.setUndecorated(true);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(296, Short.MAX_VALUE)
					.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		wbTblQuestion = new WebTable();
		wbTblQuestion.setEditable(false);
		wbTblQuestion.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Question Title" }));
		wbTblQuestion.getColumnModel().getColumn(0).setPreferredWidth(33);
		wbTblQuestion.getColumnModel().getColumn(0).setMaxWidth(33);
		webScrollPane_1.setViewportView(wbTblQuestion);
		
		wbTblSurvey = new WebTable();
		wbTblSurvey.setEditable(false);
		wbTblSurvey.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Direction", "# of Questions" }));
		wbTblSurvey.getColumnModel().getColumn(0).setPreferredWidth(33);
		wbTblSurvey.getColumnModel().getColumn(0).setMaxWidth(33);
		wbTblSurvey.getColumnModel().getColumn(2).setPreferredWidth(93);
		wbTblSurvey.getColumnModel().getColumn(2).setMaxWidth(93);
		webScrollPane.setViewportView(wbTblSurvey);
		
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		getMainContentPane().setLayout(groupLayout);
		
		initEvents();
		pack();
	}

	private void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SurveyService qs = new SurveyService();
				DefaultTableModel model = (DefaultTableModel) wbTblSurvey
						.getModel();
				try {
					ArrayList<Survey> list = qs.list();
					if (list != null) {
						// surveyList.addAll(list);
						for (int i = 0; i < list.size(); i++) {
							Survey z = list.get(i);
							surveyList.add(z);
							String title = z.getTitle();
							if (title == "" || title == null) {
								title = "No Title";
							}
							if (wbTblSurvey.getRowCount() == 0) {
								model.addRow(new Object[] { 1, title,
										z.getQuestionList().size() });
							} else {
								model.addRow(new Object[] {
										Integer.parseInt(String.valueOf(model
												.getValueAt(wbTblSurvey
														.getRowCount() - 1, 0))) + 1,
										title, z.getQuestionList().size() });
							}
						}
					}
					if (wbTblSurvey.getRowCount() != 0) {
						wbTblSurvey.getSelectionModel().setSelectionInterval(0,
								0);
					}
				} catch (SQLException e) {
					JajeemExceptionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
		
		((CustomSurveyButton)getCloseButton()).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		wbtnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(parentFrame == null)
					System.out.println(true);
				parentFrame.setCurrentSurvey(surveyList.get(wbTblSurvey
						.getSelectedRow()));
				parentFrame.loadCurrentSurvey();
				dispose();
			}
		});
		
		wbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		wbTblSurvey.getSelectionModel().addListSelectionListener(
			new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					Survey survey = surveyList.get(wbTblSurvey
							.getSelectedRow());
					DefaultTableModel model = (DefaultTableModel) wbTblQuestion
							.getModel();
					model.getDataVector().removeAllElements();
					model.fireTableDataChanged();
					for (int i = 0; i < survey.getQuestionList().size(); i++) {
						model.addRow(new Object[] {
								wbTblQuestion.getRowCount() == 0 ? 1
										: Integer.parseInt(String.valueOf(model
												.getValueAt(wbTblQuestion
														.getRowCount() - 1,
														0))) + 1,
								survey.getQuestionList().get(i).getTitle(), });
					}
					wbTblQuestion.getSelectionModel().setSelectionInterval(
							0, 0);
				}
			});
	}
}
