package com.jajeem.survey.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.SurveyService;

public class TeacherSurveyWindow {

	private JFrame frmSurvey;
	private JTextField textField_9;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxQuestionType;
	private JTextArea textArea;
	
	private Question currentQuestion;
	private com.jajeem.survey.model.Survey currentSurvey;
	final JFileChooser fc = new JFileChooser();
	private ArrayList<com.jajeem.survey.model.Survey> surveyList;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textFieldcard2;
	private JTextField textField_1card2;
	private JPanel panel_1;
	private JPanel panel_2;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private boolean eventsEnabled;
	private JTextField textField_2;
	@SuppressWarnings("unused")
	private enum QuestionTypes{
		MultipleChoicesingle,Multichoice,essay
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherSurveyWindow window = new TeacherSurveyWindow();
					window.frmSurvey.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TeacherSurveyWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmSurvey = new JFrame();
		frmSurvey.setTitle("Survey");
		frmSurvey.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				SurveyService qs = new SurveyService();
				try {
					surveyList = new ArrayList<com.jajeem.survey.model.Survey>();
					ArrayList<com.jajeem.survey.model.Survey> temp = qs.list();
					if(temp != null)
						surveyList.addAll(qs.list());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				surveyList.add(new com.jajeem.survey.model.Survey());
                currentSurvey = surveyList.get(0);
                
			}
		});
		frmSurvey.setBounds(100, 100, 919, 638);
		frmSurvey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new CardLayout());
		frmSurvey.getContentPane().add(mainPanel);
		
		panel_1 = new JPanel();
		
		JPanel panelwindow1 = new JPanel();
		JPanel panelwindow2 = new JPanel();
		
		//////////////////////////////////////////
		JPanel panelcard2 = new JPanel();
		
		JLabel labelcard2 = new JLabel("Directions");
		
		textFieldcard2 = new JTextField();
		textFieldcard2.setColumns(10);
		
		JButton btnShowResultscard2 = new JButton("Show Results");
		GroupLayout gl_panelcard2 = new GroupLayout(panelcard2);
		gl_panelcard2.setHorizontalGroup(
			gl_panelcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelcard2.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelcard2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldcard2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(454, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelcard2.createSequentialGroup()
					.addContainerGap(773, Short.MAX_VALUE)
					.addComponent(btnShowResultscard2, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelcard2.setVerticalGroup(
			gl_panelcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelcard2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelcard2.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelcard2)
						.addComponent(textFieldcard2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnShowResultscard2)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panelcard2.setLayout(gl_panelcard2);
		
		JPanel panel_1card2 = new JPanel();
		GroupLayout groupLayoutcard2 = new GroupLayout(panelwindow2);
		groupLayoutcard2.setHorizontalGroup(
			groupLayoutcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutcard2.createSequentialGroup()
					.addGroup(groupLayoutcard2.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1card2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelcard2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE))
					.addGap(1))
		);
		groupLayoutcard2.setVerticalGroup(
			groupLayoutcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutcard2.createSequentialGroup()
					.addComponent(panelcard2, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1card2, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
		);
		
		JLabel lblQuestionTypescard2 = new JLabel("Question Types");
		
		JLabel lblQuestioncard2 = new JLabel("Question");
		
		JScrollPane scrollPanecard2 = new JScrollPane();
		
		textField_1card2 = new JTextField();
		textField_1card2.setColumns(10);
		
		JLabel lblResultscard2 = new JLabel("Results");
		
		JScrollPane scrollPane_1card2 = new JScrollPane();
		
		JLabel lblansweredcard2 = new JLabel("[Answered]");
		
		JLabel lblNewLabelcard2 = new JLabel("New label");
		GroupLayout gl_panel_1card2 = new GroupLayout(panel_1card2);
		gl_panel_1card2.setHorizontalGroup(
			gl_panel_1card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1card2.createSequentialGroup()
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1card2.createSequentialGroup()
									.addGap(40)
									.addComponent(lblResultscard2)
									.addPreferredGap(ComponentPlacement.RELATED, 820, Short.MAX_VALUE)
									.addComponent(lblansweredcard2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabelcard2)
									.addGap(82))
								.addComponent(scrollPane_1card2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1085, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_panel_1card2.createSequentialGroup()
									.addComponent(scrollPanecard2, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
									.addGap(494))))
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(5)
							.addComponent(lblQuestionTypescard2)
							.addGap(5)
							.addComponent(textField_1card2, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(32)
							.addComponent(lblQuestioncard2)))
					.addContainerGap())
		);
		gl_panel_1card2.setVerticalGroup(
			gl_panel_1card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1card2.createSequentialGroup()
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(5)
							.addComponent(lblQuestionTypescard2))
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(3)
							.addComponent(textField_1card2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(22)
							.addComponent(lblQuestioncard2))
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGap(12)
							.addComponent(scrollPanecard2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResultscard2)
						.addComponent(lblansweredcard2)
						.addComponent(lblNewLabelcard2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1card2, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea textAreacard2 = new JTextArea();
		scrollPanecard2.setViewportView(textAreacard2);
		panel_1card2.setLayout(gl_panel_1card2);
		panelwindow2.setLayout(groupLayoutcard2);
		
		/////////////////////////////////////////
		
		mainPanel.add(panelwindow1,"page1");
		mainPanel.add(panelwindow2,"page2");
		
		GroupLayout groupLayout = new GroupLayout(frmSurvey.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 883, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 508, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_6 = new JPanel();
		
		JLabel label_3 = new JLabel("Directions");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JButton button = new JButton("New");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventsEnabled = false;
				textField_2.setText("");
				
				surveyList.add(new Survey());
				currentSurvey = surveyList.get(surveyList.size()-1);
				
				textArea.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_9.setText("");
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				comboBoxQuestionType.setSelectedIndex(0);
				
				eventsEnabled = true;
			}
		});
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JButton button_2 = new JButton("Save");
		
		JButton button_3 = new JButton("Save As");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(button)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOpen)
					.addGap(151)
					.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button)
						.addComponent(btnOpen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_3)
						.addComponent(button_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 228, 181));
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 228, 181));
		
		JLabel label_1 = new JLabel("[Question ?]");
		
		JLabel label_2 = new JLabel("Question Type ");
		
		comboBoxQuestionType = new JComboBox();
		comboBoxQuestionType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if(comboBoxQuestionType.getSelectedIndex() == 0){
					textField_3.setVisible(true);
					textField_4.setVisible(true);
					textField_5.setVisible(true);
					textField_6.setVisible(true);
					textField_7.setVisible(true);
					radioButton.setVisible(true);
					radioButton_1.setVisible(true);
					radioButton_2.setVisible(true);
					radioButton_3.setVisible(true);
					radioButton_4.setVisible(true);
				}
				else if(comboBoxQuestionType.getSelectedIndex() == 1){
					textField_3.setVisible(false);
					textField_4.setVisible(false);
					textField_5.setVisible(false);
					textField_6.setVisible(false);
					textField_7.setVisible(false);
					radioButton.setVisible(false);
					radioButton_1.setVisible(false);
					radioButton_2.setVisible(false);
					radioButton_3.setVisible(false);
					radioButton_4.setVisible(false);
				}
			}
		});
		comboBoxQuestionType.setModel(new DefaultComboBoxModel(new String[] {"Polling", "Text"}));
		
		JLabel label_5 = new JLabel("Web Link");
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		
		JButton btnImage = new JButton("+Image");
		btnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel lblQuestion = new JLabel("Question");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		
		radioButton = new JRadioButton("");
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(true);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		
		radioButton_1 = new JRadioButton("");
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(true);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		
		radioButton_2 = new JRadioButton("");
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(true);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		
		radioButton_3 = new JRadioButton("");
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(true);
				radioButton_4.setSelected(false);
			}
		});
		
		radioButton_4 = new JRadioButton("");
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(true);
			}
		});
		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
								.addComponent(radioButton_3)
								.addComponent(radioButton_1)
								.addComponent(radioButton)
								.addComponent(lblQuestion)
								.addComponent(radioButton_2)
								.addComponent(radioButton_4))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_3)
										.addComponent(textField_4)
										.addComponent(textField_5)
										.addComponent(textField_6)
										.addComponent(textField_7)
										.addGroup(gl_panel_4.createSequentialGroup()
											.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnImage, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 224, Short.MAX_VALUE))
								.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))
						.addComponent(label_1)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxQuestionType, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
					.addGap(45)
					.addComponent(label_5)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_9, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
					.addGap(18))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addComponent(lblQuestion)
							.addGap(51, 51, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_1))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_2))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_3))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_4))
					.addGap(18)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(btnImage)
							.addGap(32))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(8)))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_5)
						.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(389)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_4, 0, 0, Short.MAX_VALUE)
					.addGap(0))
		);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel = new GroupLayout(panelwindow1);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_6, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
		);
		panelwindow1.setLayout(gl_panel);
		
		JButton btnNewButton_1 = new JButton("Content");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(mainPanel.getLayout());
		        cl.show(mainPanel, "page1");
			}
		});
		
		JButton btnNewButton = new JButton("Response");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(mainPanel.getLayout());
		        cl.show(mainPanel, "page2");
		        
			}
		});
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED, 650, Short.MAX_VALUE)
					.addComponent(btnStart)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(29)
							.addComponent(btnStart))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		panel_1.setLayout(gl_panel_1);
		frmSurvey.getContentPane().setLayout(groupLayout);
		initDataBindings();
	}

	protected void initDataBindings() {
	}
}
