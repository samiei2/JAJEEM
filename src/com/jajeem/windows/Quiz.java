package com.jajeem.windows;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.exception.JajeemExcetionHandler;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Quiz {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JTextArea textArea;
	private DefaultTableModel tablemodel;
	
	private int instructorId;
	private Question currentQuestion;
	private com.jajeem.quiz.model.Quiz currentQuiz;
	private JTable table;
	final JFileChooser fc = new JFileChooser();
	private ArrayList<com.jajeem.quiz.model.Quiz> quizList;
	private enum QuestionTypes{
		MultipleChoice,Essay
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quiz window = new Quiz();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Quiz() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				LoadQuizes();
			}

			private void LoadQuizes() {
				QuizService qs = new QuizService();
				try {
					quizList = qs.list();
				} catch (SQLException e) {
					quizList = new ArrayList<>();
					e.printStackTrace();
				}
			}
		});
		frame.setBounds(100, 100, 919, 638);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 228, 181));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 850, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 424, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 228, 181));
		
		JButton button = new JButton("Copy");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.addRow(new Object[]{});
			}
		});
		
		JButton button_1 = new JButton("Add");
		button_1.setEnabled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{"al","v","asd","we1"});
			}
		});
		
		JButton button_2 = new JButton("Delete");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.removeRow(table.getSelectedRow());
			}
		});
		
		JLabel label = new JLabel("Question List");
		
		
		JButton moveup_button = new JButton("New button");
		moveup_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == 0)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow()-1);
			}
		});
		
		JButton movedown_button = new JButton("New button");
		movedown_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == table.getRowCount()-1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.moveRow(table.getSelectedRow(), table.getSelectedRow(), table.getSelectedRow()+1);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(48))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED, 311, Short.MAX_VALUE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(moveup_button, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(movedown_button, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addContainerGap()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
								.addComponent(button)
								.addComponent(button_1)
								.addComponent(button_2)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(185)
							.addComponent(moveup_button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(movedown_button)))
					.addContainerGap())
		);
		
		table = new JTable();
		tablemodel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Number", "Type", "Points", "Content"
				});
		table.setModel(tablemodel);
		ListSelectionModel m_modelSelection = table.getSelectionModel();

		m_modelSelection.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				currentQuestion = currentQuiz.getQuestionList().get(table.getSelectedRow());
				LoadQuestion(currentQuestion);
			}
		});
		table.getColumnModel().getColumn(3).setMinWidth(20);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 228, 181));
		
		JLabel label_1 = new JLabel("[Question ?]");
		
		JLabel label_2 = new JLabel("Question Type ");
		
		JRadioButton radioButton_1 = new JRadioButton("(5)");
		
		JRadioButton radioButton_2 = new JRadioButton("(4)");
		
		JRadioButton radioButton_3 = new JRadioButton("(3)");
		
		JRadioButton radioButton_4 = new JRadioButton("(2)");
		
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
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Multi", "Essay"}));
		
		JLabel label_4 = new JLabel("Points");
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		
		JButton button_5 = new JButton("Next");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				///Create the question with current parameters
				currentQuestion = new Question();
				currentQuestion.setTitle("");
				currentQuestion.setAnswer1(textField_3.getText());
				currentQuestion.setAnswer2(textField_4.getText());
				currentQuestion.setAnswer3(textField_5.getText());
				currentQuestion.setAnswer4(textField_6.getText());
				currentQuestion.setAnswer5(textField_7.getText());
				currentQuestion.setInstructorId(getInstructorId());
				try{
					currentQuestion.setPoint(Integer.parseInt(textField_8.getText()));
				}
				catch(Exception ex){
					;
				}
				try{
					if(comboBox_1.getSelectedIndex() == 0)
						currentQuestion.setType(Byte.parseByte("0"));
					else if(comboBox_1.getSelectedIndex() == 1)
						currentQuestion.setType(Byte.parseByte("1"));
					currentQuestion.setUrl(textField_9.getText());
				}
				catch(Exception ex){
					;
				}
				
				/// Add the question to the current quiz and question list
				currentQuiz.addQuestion(currentQuestion);
				tablemodel.addRow(new Object[]{table.getRowCount() - 1,currentQuestion.getType(),currentQuestion.getPoint(),currentQuestion.getPoint(),currentQuestion.getTitle()});
				
				///empty all fields
				
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_9.setText("");
			}
		});
		
		JLabel label_5 = new JLabel("Web Link");
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		
		JButton button_6 = new JButton("+Image");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		textArea = new JTextArea();
		
		JLabel lblQuestion = new JLabel("Question");
		
		JRadioButton radioButton = new JRadioButton("(1)");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
										.addComponent(label_2)
										.addGroup(gl_panel_4.createSequentialGroup()
											.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
												.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
												.addComponent(radioButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
											.addGap(34)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_4.createSequentialGroup()
											.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
											.addComponent(label_4)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
										.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
										.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addContainerGap(429, Short.MAX_VALUE)
							.addComponent(button_5))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGap(90)
							.addComponent(label_5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
								.addComponent(textField_9, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGap(105)
							.addComponent(textField_7, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGap(105)
							.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addContainerGap(44, Short.MAX_VALUE)
							.addComponent(lblQuestion)
							.addGap(18)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 379, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGap(105)
							.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4)
						.addComponent(label_2))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(58)
							.addComponent(lblQuestion))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_4))
							.addGap(32)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_2))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_6))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_5)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_3))
							.addGap(171))))
		);
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JButton btnNewButton_1 = new JButton("Content");
		
		JButton btnNewButton = new JButton("Response");
		
		JButton btnNewButton_2 = new JButton("Reports");
		
		JCheckBox chckbxShuffle = new JCheckBox("Shuffle");
		
		JButton btnNewButton_7 = new JButton("Start");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuizService qs = new QuizService();
				qs.Run(currentQuiz);
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2)
					.addPreferredGap(ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
					.addComponent(chckbxShuffle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_7)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_7)
								.addComponent(chckbxShuffle)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))))
					.addGap(11))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblDirections = new JLabel("Directions");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblPoints = new JLabel("Points");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("New");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentQuiz = new com.jajeem.quiz.model.Quiz();
			}
		});
		
		JButton btnNewButton_4 = new JButton("Open");
		
		JCheckBox chckbxAuto = new JCheckBox("Auto");
		
		JLabel lblTimeLimit = new JLabel("Time Limit");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblShowResults = new JLabel("Show Results");
		
		comboBox = new JComboBox();
		
		JButton btnNewButton_5 = new JButton("Save As");
		
		JButton btnNewButton_6 = new JButton("Save");
		
		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentQuiz = quizList.get(comboBox_2.getSelectedIndex());
				LoadQuiz(currentQuiz);
			}

			private void LoadQuiz(com.jajeem.quiz.model.Quiz currentQuiz) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
			}
		});
		
		
		JLabel lblQuiz = new JLabel("Quiz");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDirections)
						.addComponent(lblPoints))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
							.addComponent(chckbxAuto)
							.addGap(10)
							.addComponent(lblTimeLimit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblShowResults)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNewButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_4)
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addComponent(lblQuiz)))
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_2, 0, 97, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirections)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_3)
						.addComponent(btnNewButton_4)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuiz))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxAuto)
						.addComponent(lblTimeLimit)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblShowResults)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_5)
						.addComponent(lblPoints)
						.addComponent(btnNewButton_6))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}

	private void LoadQuestion(Question currentQuestion2) {
		 textArea.setText(currentQuestion2.getTitle());
		 textField_3.setText(currentQuestion2.getAnswer1());
		 textField_4.setText(currentQuestion2.getAnswer2());
		 textField_5.setText(currentQuestion2.getAnswer3());
		 textField_6.setText(currentQuestion2.getAnswer4());
		 textField_7.setText(currentQuestion2.getAnswer5());
		 textField_8.setText(String.valueOf(currentQuestion2.getPoint()));
		 textField_9.setText(currentQuestion2.getUrl());
		 if(currentQuestion2.getType() == 0){
			 comboBox_1.setSelectedIndex(0);
		 }
		 else if(currentQuestion2.getType() == 1){
			 comboBox_1.setSelectedIndex(1);
		 }
	}

	private int getInstructorId() {
		return instructorId;
	}

	private void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public com.jajeem.quiz.model.Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public void setCurrentQuiz(com.jajeem.quiz.model.Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
	}

	public ArrayList<com.jajeem.quiz.model.Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(ArrayList<com.jajeem.quiz.model.Quiz> quizList) {
		this.quizList = quizList;
	}
}
