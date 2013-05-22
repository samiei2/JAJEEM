package com.jajeem.survey.design;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.service.QuizService;

import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class OpenDialog extends JDialog {

	private WebComboBox wbCmbBxOption;
	private WebComboBox wbCmbBxSelection;
	private WebTable wbTblQuestion;
	private WebTable wbTblQuiz;
	private ArrayList<Quiz> quizList = new ArrayList<>();
	private Main parentFrame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OpenDialog dialog = new OpenDialog(null);
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
	public OpenDialog(Main frame) {
		parentFrame = frame;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				QuizService qs = new QuizService();
				DefaultTableModel model = (DefaultTableModel) wbTblQuiz.getModel();
				try {
					ArrayList<Quiz> list = qs.list();
					if(list != null){
						//quizList.addAll(list);
						for (int i = 0; i < list.size(); i++) {
							Quiz z = list.get(i);
							quizList.add(z);
							if(wbTblQuiz.getRowCount() == 0){
								model.addRow(new Object[]{
										1,
										z.getTitle()
								});
							}
							else{
								model.addRow(new Object[]{
										Integer.parseInt(String.valueOf(model.getValueAt(wbTblQuiz.getRowCount()-1, 0)))+1,
										z.getTitle()
								});
							}
						}
					}
					if(wbTblQuiz.getRowCount() != 0)
						wbTblQuiz.getSelectionModel().setSelectionInterval(0, 0);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(750, 700);
		setLocationByPlatform(true);
		
		WebPanel webPanel = new WebPanel();
		getContentPane().add(webPanel, BorderLayout.CENTER);
		
		WebButton wbtnOpen = new WebButton();
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.setCurrentQuiz(quizList.get(wbTblQuiz.getSelectedRow()));
				parentFrame.loadCurrentQuiz();
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
		
		WebLabel wblblSearch = new WebLabel();
		wblblSearch.setText("Search");
		
		WebTextField webTextField = new WebTextField();
		webTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		WebLabel wblblCategory = new WebLabel();
		wblblCategory.setText("Select");
		
		wbCmbBxSelection = new WebComboBox();
		wbCmbBxSelection.setModel(new DefaultComboBoxModel(new String[] {"Category", "Question Type", "Instructor"}));
		wbCmbBxSelection.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				wbCmbBxOption.setVisible(true);
				if (wbCmbBxSelection.getSelectedIndex() == 0) {
					
				}
				else if(wbCmbBxSelection.getSelectedIndex() == 1){
					
				}
				else if(wbCmbBxSelection.getSelectedIndex() == 2){
					
				}
			}
		});
		
		WebLabel wblblResults = new WebLabel();
		wblblResults.setText("Results");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		
		wbCmbBxOption = new WebComboBox();
		wbCmbBxOption.setVisible(false);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(wblblSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(wblblCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wbCmbBxSelection, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wbCmbBxOption, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
						.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_webPanel_1.createSequentialGroup()
							.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblSearch, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGap(36)
							.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbCmbBxSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(wblblCategory, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbCmbBxOption, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
						.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
					.addGap(14))
		);
		
		wbTblQuestion = new WebTable();
		wbTblQuestion.setEditable(false);
		wbTblQuestion.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title"
			}
		));
		webScrollPane_1.setViewportView(wbTblQuestion);
		
		wbTblQuiz = new WebTable();
		wbTblQuiz.setEditable(false);
		wbTblQuiz.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Name"
			}
		));
		wbTblQuiz.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Quiz quiz = quizList.get(wbTblQuiz.getSelectedRow());
				DefaultTableModel model = (DefaultTableModel) wbTblQuestion.getModel();
				for (int i = 0; i < wbTblQuestion.getRowCount(); i++) {
					model.removeRow(i);
				}
				for (int i = 0; i < quiz.getQuestionList().size(); i++) {
					model.addRow(new Object[]{
							quiz.getQuestionList().get(i).getTitle()
					});
				}
				wbTblQuestion.getSelectionModel().setSelectionInterval(0, 0);
			}
		});
		
		webScrollPane.setViewportView(wbTblQuiz);
		webPanel_1.setLayout(gl_webPanel_1);
		webPanel.setLayout(gl_webPanel);
		setVisible(true);
	}
	
	public Quiz getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
