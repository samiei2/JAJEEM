package com.jajeem.survey.design;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.SurveyService;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class SurveyOpenDialog extends JDialog {
	private WebTable wbTblQuestion;
	private WebTable wbTblSurvey;
	private ArrayList<Survey> surveyList = new ArrayList<>();
	private SurveyMain parentFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SurveyOpenDialog dialog = new SurveyOpenDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param actionListener 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SurveyOpenDialog(SurveyMain frame) {
		setTitle("Open");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SurveyOpenDialog.class.getResource("/com/jajeem/images/survey.png")));
		parentFrame = frame;
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SurveyService qs = new SurveyService();
				DefaultTableModel model = (DefaultTableModel) wbTblSurvey.getModel();
				try {
					ArrayList<Survey> list = qs.list();
					if(list != null){
						//surveyList.addAll(list);
						for (int i = 0; i < list.size(); i++) {
							Survey z = list.get(i);
							surveyList.add(z);
							String title = z.getTitle();
							if(title == null || title.equals(""))
								title = "No Title";
							if(wbTblSurvey.getRowCount() == 0){
								model.addRow(new Object[]{
										1,
										title,
										z.getQuestionList().size()
								});
							}
							else{
								model.addRow(new Object[]{
										Integer.parseInt(String.valueOf(model.getValueAt(wbTblSurvey.getRowCount()-1, 0)))+1,
										title,
										z.getQuestionList().size()
								});
							}
						}
					}
					if(wbTblSurvey.getRowCount() != 0)
						wbTblSurvey.getSelectionModel().setSelectionInterval(0, 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(true);
		setBounds(100, 0, 800, 720);
		setLocationByPlatform(true);
		
		WebPanel webPanel = new WebPanel();
		getContentPane().add(webPanel, BorderLayout.CENTER);
		
		WebButton wbtnOpen = new WebButton();
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setCurrentSurvey(surveyList.get(wbTblSurvey.getSelectedRow()));
				parentFrame.loadCurrentSurvey();
				dispose();
			}
		});
		wbtnOpen.setText("Open");
		
		WebButton wbtnCancel = new WebButton();
		wbtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		wbtnCancel.setText("Cancel");
		
		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(webPanel_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		WebLabel wblblResults = new WebLabel();
		wblblResults.setText("Results");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_webPanel_1.createSequentialGroup()
							.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE))
						.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
						.addComponent(webScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
					.addGap(14))
		);
		
		wbTblQuestion = new WebTable();
		wbTblQuestion.setEditable(false);
		wbTblQuestion.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Title"
			}
		));
		wbTblQuestion.getColumnModel().getColumn(0).setPreferredWidth(43);
		wbTblQuestion.getColumnModel().getColumn(0).setMaxWidth(43);
		webScrollPane_1.setViewportView(wbTblQuestion);
		
		wbTblSurvey = new WebTable();
		wbTblSurvey.setEditable(false);
		wbTblSurvey.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Name","# of Questions"
			}
		));
		wbTblSurvey.getColumnModel().getColumn(0).setPreferredWidth(33);
		wbTblSurvey.getColumnModel().getColumn(0).setMaxWidth(33);
		wbTblSurvey.getColumnModel().getColumn(2).setPreferredWidth(93);
		wbTblSurvey.getColumnModel().getColumn(2).setMaxWidth(93);
		wbTblSurvey.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Survey survey = surveyList.get(wbTblSurvey.getSelectedRow());
				DefaultTableModel model = (DefaultTableModel) wbTblQuestion.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				for (int i = 0; i < survey.getQuestionList().size(); i++) {
					model.addRow(new Object[]{
							wbTblQuestion.getRowCount() == 0 ? 1 : Integer.parseInt(String.valueOf(model.getValueAt(wbTblQuestion.getRowCount()-1, 0)))+1,
							survey.getQuestionList().get(i).getTitle()
					});
				}
				wbTblQuestion.getSelectionModel().setSelectionInterval(0, 0);
			}
		});
		
		webScrollPane.setViewportView(wbTblSurvey);
		webPanel_1.setLayout(gl_webPanel_1);
		webPanel.setLayout(gl_webPanel);
		setVisible(true);
	}
	
	public Survey getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
