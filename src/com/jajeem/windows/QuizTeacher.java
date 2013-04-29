package com.jajeem.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.service.QuizService;

public class QuizTeacher {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_8;
	private JTextField textField_9;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxShowResults;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxQuestionType;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxQuizes;
	private JTextArea textArea;
	private DefaultTableModel tablemodel;
	
	private int instructorId;
	private Question currentQuestion;
	private com.jajeem.quiz.model.Quiz currentQuiz;
	private JTable table;
	final JFileChooser fc = new JFileChooser();
	private ArrayList<com.jajeem.quiz.model.Quiz> quizList;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_5;
	private boolean eventsEnabled;
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
					QuizTeacher window = new QuizTeacher();
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
	public QuizTeacher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
					quizList = new ArrayList<com.jajeem.quiz.model.Quiz>();
					ArrayList<com.jajeem.quiz.model.Quiz> temp = qs.list();
					if(temp != null)
						quizList.addAll(qs.list());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				quizList.add(new com.jajeem.quiz.model.Quiz());
                currentQuiz = quizList.get(0);
                
                
//                JTableBinding tb = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, currentQuiz.getQuestionList(), table);
//        		// define the properties to be used for the columns
//        	    BeanProperty id = BeanProperty.create("id");
//        	    BeanProperty type = BeanProperty.create("type");
//        	    BeanProperty point = BeanProperty.create("point");
//        	    BeanProperty title = BeanProperty.create("title");
//        	    // configure how the properties map to columns
//        	    tb.addColumnBinding(id).setColumnName("Id");
//        	    tb.addColumnBinding(type).setColumnName("Type").setColumnClass(boolean.class);
//        	    tb.addColumnBinding(point).setColumnName("Point").setColumnClass(Integer.class);
//        	    tb.addColumnBinding(title).setColumnName("Content").setColumnClass(String.class);
//
//        	    // realize the binding
//        	    tb.bind();
			}
		});
		frame.setBounds(100, 100, 919, 638);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		
		panel_1 = new JPanel();
		
		panel_2 = new JPanel();
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
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	System.out.println(table.getModel().getClass());
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				Question question = new Question();
                Object[] obj = new Object[]{
                		table.getRowCount(),
                        "MultiChoice (Single)",
                        0,
                        ""};
				model.addRow(obj);
                currentQuiz.addQuestion(question);
                textArea.setText("");
                textField_3.setText("");
                textField_4.setText("");
                textField_5.setText("");
                textField_6.setText("");
                textField_7.setText("");
                textField_8.setText("");
                textField_9.setText("");
                comboBoxQuestionType.setSelectedIndex(0);
                radioButton.setSelected(false);
                radioButton_1.setSelected(false);
                radioButton_2.setSelected(false);
                radioButton_3.setSelected(false);
                radioButton_4.setSelected(false);
                checkBox.setSelected(false);
                checkBox_1.setSelected(false);
                checkBox_2.setSelected(false);
                checkBox_3.setSelected(false);
                checkBox_4.setSelected(false);
                System.out.println(model.getDataVector().get(0));
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.removeRow(table.getSelectedRow());
				currentQuiz.getQuestionList().remove(table.getSelectedRow());
				if(table.getRowCount() != 0){
					ListSelectionModel m_modelSelection = table.getSelectionModel();
					m_modelSelection.setSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				}
			}
		});
		
		JLabel label = new JLabel("Question List");
		
		
		JButton btnUp = new JButton("New button");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = table.getSelectedRow();
				if(currentIndex == 0)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex - 1);
				Question temp = currentQuiz.getQuestionList().get(currentIndex);
				currentQuiz.getQuestionList().remove(currentIndex);
				currentQuiz.getQuestionList().add(currentIndex - 1, temp);
			}
		});
		
		JButton btnDown = new JButton("New button");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = table.getSelectedRow();
				if(currentIndex == table.getRowCount()-1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex+1);
				Question temp = currentQuiz.getQuestionList().get(currentIndex);
				currentQuiz.getQuestionList().remove(currentIndex);
				currentQuiz.getQuestionList().add(currentIndex + 1, temp);
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
							.addComponent(btnCopy, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(48))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED, 311, Short.MAX_VALUE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnUp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDown, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))))
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
								.addComponent(btnCopy)
								.addComponent(btnAdd)
								.addComponent(btnDelete)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(185)
							.addComponent(btnUp)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDown)))
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
                            if(table.getSelectedRow() == -1)
                                return;
                            //int i = table.getSelectedRow();
                            //ArrayList<Question> q = currentQuiz.getQuestionList();
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
		
		comboBoxQuestionType = new JComboBox();
		comboBoxQuestionType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				CardLayout cl = (CardLayout)(panel_5.getLayout());
		        cl.show(panel_5, (String)evt.getItem());
		        radioButton.setSelected(false);
		        radioButton_1.setSelected(false);
		        radioButton_2.setSelected(false);
		        radioButton_3.setSelected(false);
		        radioButton_4.setSelected(false);
		        checkBox.setSelected(false);
		        checkBox_1.setSelected(false);
		        checkBox_2.setSelected(false);
		        checkBox_3.setSelected(false);
		        checkBox_4.setSelected(false);
		        if(table.getSelectedRow() != -1 && eventsEnabled){
		        	tablemodel.setValueAt(comboBoxQuestionType.getSelectedItem().toString(),table.getSelectedRow(), 1);
		        	currentQuiz.getQuestionList().get(table.getSelectedRow()).setType((byte) comboBoxQuestionType.getSelectedIndex());
		        }
			}
		});
		comboBoxQuestionType.setModel(new DefaultComboBoxModel(new String[] {"Multiple Choice(single)", "Multiple Choice", "Key in answer"}));
		
		JLabel label_4 = new JLabel("Points");
		
		textField_8 = new JTextField();
		textField_8.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(table.getSelectedRow() != -1 && eventsEnabled){
					table.setValueAt(textField_8.getText(), table.getSelectedRow(), 2);
					currentQuestion.setTitle(textField_8.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(table.getSelectedRow() != -1 && eventsEnabled){
					table.setValueAt(textField_8.getText(), table.getSelectedRow(), 2);
					currentQuestion.setTitle(textField_8.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		textField_8.setColumns(10);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				///Create the question with current parameters
				eventsEnabled = false;
                if(currentQuestion == null)
                	currentQuestion = new Question();
                if(comboBoxQuestionType.getSelectedIndex()==0) {
                	if(!radioButton.isSelected()
                			&& !radioButton_1.isSelected()
                			&& !radioButton_2.isSelected()
                			&& !radioButton_3.isSelected()
                			&& !radioButton_4.isSelected()){
                		JOptionPane.showMessageDialog(null, "You must select one correct answer!");
                		return;
                	}
                }
                else if(comboBoxQuestionType.getSelectedIndex()==1){
                	if(!checkBox.isSelected() 
                			&& !radioButton_1.isSelected()
                			&& !radioButton_2.isSelected()
                			&& !radioButton_3.isSelected()
                			&& !radioButton_4.isSelected()){
                		JOptionPane.showMessageDialog(null, "You must select at least one correct answer!");
                		return;
                	}
                }
				currentQuestion.setTitle(textArea.getText());
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
					if(comboBoxQuestionType.getSelectedIndex() == 0){
						currentQuestion.setType(Byte.parseByte("0"));
						currentQuestion.setCorrectAnswer(
								new boolean[]{
										radioButton.isSelected(),
										radioButton_1.isSelected(),
										radioButton_2.isSelected(),
										radioButton_3.isSelected(),
										radioButton_4.isSelected()});
					}
					else if(comboBoxQuestionType.getSelectedIndex() == 1){
						currentQuestion.setType(Byte.parseByte("1"));
						currentQuestion.setCorrectAnswer(
								new boolean[]{
										checkBox.isSelected(),
										checkBox_1.isSelected(),
										checkBox_2.isSelected(),
										checkBox_3.isSelected(),
										checkBox_4.isSelected()});
					}
					else if(comboBoxQuestionType.getSelectedIndex() == 2){
						currentQuestion.setType(Byte.parseByte("2"));
						
					}
					
					currentQuestion.setUrl(textField_9.getText());
				}
				catch(Exception ex){
					;
				}
				
				/// Add the question to the current quiz and question list
				if(table.getSelectedRow() == -1){ // meaning no question is selected so the changes are made to a new question not an existing one
					currentQuiz.addQuestion(currentQuestion);
					tablemodel.addRow(new Object[]{table.getRowCount() + 1,comboBoxQuestionType.getSelectedItem().toString(),currentQuestion.getPoint(),currentQuestion.getTitle()});
				}
				else{ //changes are made to an existing question because a question is already selected
					
				}
				///empty all fields and ready for new question
				textArea.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_9.setText("");
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				checkBox.setSelected(false);
				checkBox_1.setSelected(false);
				checkBox_2.setSelected(false);
				checkBox_3.setSelected(false);
				checkBox_4.setSelected(false);
				
				eventsEnabled = true;
				//currentQuestion = new Question();
				currentQuiz.addQuestion(new Question());
				tablemodel.addRow(new Object[]{table.getRowCount(),comboBoxQuestionType.getSelectedItem().toString(),0,""});
				ListSelectionModel m_modelSelection = table.getSelectionModel();
				m_modelSelection.setSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
				
			}
		});
		
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
				if(table.getSelectedRow() != -1 && eventsEnabled){
					table.setValueAt(textArea.getText(), table.getSelectedRow(), 3);
					currentQuestion.setTitle(textArea.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(table.getSelectedRow() != -1 && eventsEnabled){
					table.setValueAt(textArea.getText(), table.getSelectedRow(), 3);
					currentQuestion.setTitle(textArea.getText());
				}
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
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListSelectionModel m_modelSelection = table.getSelectionModel();
				m_modelSelection.setSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
			}
		});
		
		panel_5 = new JPanel(new CardLayout());
		panel_5.setBackground(new Color(255, 228, 181));
		
		Box card1 = Box.createVerticalBox();
		radioButton = new JRadioButton();
		radioButton.setBackground(new Color(255, 228, 181));
		card1.add(radioButton);
		card1.add(Box.createRigidArea(new Dimension(1,5)));
		radioButton_1 = new JRadioButton();
		radioButton_1.setBackground(new Color(255, 228, 181));
		card1.add(radioButton_1);
		card1.add(Box.createRigidArea(new Dimension(1,5)));
		radioButton_2 = new JRadioButton();
		radioButton_2.setBackground(new Color(255, 228, 181));
		card1.add(radioButton_2);
		card1.add(Box.createRigidArea(new Dimension(1,5)));
		radioButton_3 = new JRadioButton();
		radioButton_3.setBackground(new Color(255, 228, 181));
		card1.add(radioButton_3);
		card1.add(Box.createRigidArea(new Dimension(1,5)));
		radioButton_4 = new JRadioButton();
		radioButton_4.setBackground(new Color(255, 228, 181));
		card1.add(radioButton_4);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(true);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(true);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(true);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
			}
		});
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(true);
				radioButton_4.setSelected(false);
			}
		});
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(true);
			}
		});
		
		Box card2 = Box.createVerticalBox();
		checkBox = new JCheckBox();
		checkBox.setBackground(new Color(255, 228, 181));
		card2.add(checkBox);
		card2.add(Box.createRigidArea(new Dimension(1,5)));
		checkBox_1 = new JCheckBox();
		checkBox_1.setBackground(new Color(255, 228, 181));
		card2.add(checkBox_1);
		card2.add(Box.createRigidArea(new Dimension(1,5)));
		checkBox_2 = new JCheckBox();
		checkBox_2.setBackground(new Color(255, 228, 181));
		card2.add(checkBox_2);
		card2.add(Box.createRigidArea(new Dimension(1,5)));
		checkBox_3 = new JCheckBox();
		checkBox_3.setBackground(new Color(255, 228, 181));
		card2.add(checkBox_3);
		card2.add(Box.createRigidArea(new Dimension(1,5)));
		checkBox_4 = new JCheckBox();
		checkBox_4.setBackground(new Color(255, 228, 181));
		card2.add(checkBox_4);
		
		Box card3 = Box.createVerticalBox();
		
		panel_5.add(card1,"Multiple Choice(single)");
		panel_5.add(card2,"Multiple Choice");
		panel_5.add(card3,"Key in answer");
		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(label_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBoxQuestionType, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
									.addComponent(label_4)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(btnPrevious)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(90)
							.addComponent(label_5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnImage, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
								.addComponent(textField_9, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblQuestion)
								.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textField_3)
								.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
								.addComponent(textField_4)
								.addComponent(textField_5)
								.addComponent(textField_6)
								.addComponent(textField_7))))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(textField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
					.addGap(11)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnImage))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNext)
						.addComponent(btnPrevious))
					.addGap(13))
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
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_4, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
					.addContainerGap(3, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JButton btnNewButton_1 = new JButton("Content");
		
		JButton btnNewButton = new JButton("Response");
		
		JButton btnNewButton_2 = new JButton("Reports");
		
		JCheckBox chckbxShuffle = new JCheckBox("Shuffle");
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//QuizService qs = new QuizService();
				//qs.Run(currentQuiz);
				QuizStudent qs = new QuizStudent(currentQuiz);
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
					.addComponent(btnStart)
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
								.addComponent(btnStart)
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
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quizList.add(new com.jajeem.quiz.model.Quiz());
				currentQuiz = quizList.get(quizList.size()-1);
				eventsEnabled = false;
				for (int i = 0; i < table.getRowCount(); i++) {
					tablemodel.removeRow(i);
				}
				
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				checkBox.setSelected(false);
				checkBox_1.setSelected(false);
				checkBox_2.setSelected(false);
				checkBox_3.setSelected(false);
				checkBox_4.setSelected(false);
				
				textArea.setText("");
				textField.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_9.setText("");
				
				eventsEnabled = true;
			}
		});
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JCheckBox chckbxAuto = new JCheckBox("Auto");
		
		JLabel lblTimeLimit = new JLabel("Time Limit");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblShowResults = new JLabel("Show Results");
		
		comboBoxShowResults = new JComboBox();
		
		JButton btnSaveas = new JButton("Save As");
		
		JButton btnSave = new JButton("Save");
		
		comboBoxQuizes = new JComboBox();
		comboBoxQuizes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentQuiz = quizList.get(comboBoxQuizes.getSelectedIndex());
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
							.addComponent(comboBoxShowResults, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnNew)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOpen)
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addComponent(lblQuiz)))
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveas, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxQuizes, 0, 97, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirections)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNew)
						.addComponent(btnOpen)
						.addComponent(comboBoxQuizes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuiz))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxAuto)
						.addComponent(lblTimeLimit)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblShowResults)
						.addComponent(comboBoxShowResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveas)
						.addComponent(lblPoints)
						.addComponent(btnSave))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		initDataBindings();
	}

	private void LoadQuestion(Question currentQuestion2) {
		 eventsEnabled = false;
		 textArea.setText(currentQuestion2.getTitle());
		 textField_3.setText(currentQuestion2.getAnswer1());
		 textField_4.setText(currentQuestion2.getAnswer2());
		 textField_5.setText(currentQuestion2.getAnswer3());
		 textField_6.setText(currentQuestion2.getAnswer4());
		 textField_7.setText(currentQuestion2.getAnswer5());
		 textField_8.setText(String.valueOf(currentQuestion2.getPoint()));
		 textField_9.setText(currentQuestion2.getUrl());
		 if(currentQuestion2.getType() == 0){
			 comboBoxQuestionType.setSelectedIndex(0);
		 }
		 else if(currentQuestion2.getType() == 1){
			 comboBoxQuestionType.setSelectedIndex(1);
		 }
		 else if(currentQuestion2.getType() == 2){
			 comboBoxQuestionType.setSelectedIndex(2);
		 }
		 eventsEnabled = true;
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
	protected void initDataBindings() {
	}
}
