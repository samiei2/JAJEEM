package com.jajeem.quiz.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizEventListener;
import com.jajeem.events.QuizResponse;
import com.jajeem.events.QuizStop;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;

public class StudentQuizWindow  extends JFrame{

	private JFrame frame;
	private JTextArea textArea;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField33;
	private JTextField textField11;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField22;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JLabel lblInputAnswer;
	private JLabel lblQuestion;
	private JButton btnPrevious;
	private JButton btnNext;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JRadioButton rdbtnNewRadioButton_3;
	private JRadioButton rdbtnNewRadioButton_4;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	@SuppressWarnings("rawtypes")
	private JList list;
	private DefaultListModel model;
	
	private Quiz currentQuiz;
	protected Question currentQuestion;
	private JLabel lbltimer;
	
	private JLabel lbltime;
	
	private QuizEvent quizEvent;
	long remaining; // How many milliseconds remain in the countdown.

	long lastUpdate; // When count was last updated
	
	Timer timer; // Updates the count every second

	NumberFormat format; // Format minutes:seconds with leading zeros

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentQuizWindow window = new StudentQuizWindow();
					Quiz q = new Quiz();
					q.addQuestion(new Question());
					q.addQuestion(new Question());
					q.addQuestion(new Question());
					q.addQuestion(new Question());
					q.addQuestion(new Question());
					window.setCurrentQuiz(q);
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
	public StudentQuizWindow() {
		initialize();
	}

	public StudentQuizWindow(Quiz quiz){
		setCurrentQuiz(quiz);
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
				if(currentQuiz == null || currentQuiz.getQuestionList().size() == 0){
					JOptionPane.showMessageDialog(null, "An error occured while opening the quiz");
					frame.dispose();
				}
				
				
				for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
					model.addElement("Question"+i);
				}
				
				list.setSelectedIndex(0);
				
				/////Setting the timer
				ActionListener taskPerformer = new ActionListener() {
				      public void actionPerformed(ActionEvent evt) {
				    	  updateDisplay();
				      }
				  };
				  
				
				if(currentQuiz.getTime() != 0){
					lbltimer.setText(String.valueOf(currentQuiz.getTime()).concat(":00"));
					remaining = currentQuiz.getTime()*60000;
					timer = new Timer(1000,taskPerformer);
				    timer.setInitialDelay(0);
				    lastUpdate = System.currentTimeMillis();
				    timer.start();
					
				}
				else{
					lbltimer.setText("");
				}
				lbltime.setText(String.valueOf(new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(Calendar.getInstance().getTime())));
			}
		});
		frame.setBounds(100, 100, 650, 690);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 228, 196));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
		);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 228, 181));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_4 = new JPanel();
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 228, 196));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lblQuestion = new JLabel("Question ?");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		lblInputAnswer = new JLabel("Input Answer");
		
		final JPanel panel_6 = new JPanel();
		panel_6.setLayout(new CardLayout());
		
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() != model.getSize()){
					list.setSelectedIndex(list.getSelectedIndex()+1);
				}
			}
		});
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() != 0){
					list.setSelectedIndex(list.getSelectedIndex()-1);
				}
			}
		});
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(53)
							.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInputAnswer)
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
								.addComponent(lblQuestion)
								.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnPrevious, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInputAnswer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNext)
						.addComponent(btnPrevious))
					.addContainerGap())
		);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setEnabled(false);
		scrollPane_1.setViewportView(textArea);
		panel_5.setLayout(gl_panel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
		);
		
		model = new DefaultListModel<>();
		list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Save CurrentQuestion and send to teacher
				if(currentQuestion!=null){
					if(currentQuestion.getType() == 0){
						currentQuestion.setStudentAnswer(new boolean[]{rdbtnNewRadioButton.isSelected(),rdbtnNewRadioButton_1.isSelected(),rdbtnNewRadioButton_2.isSelected()
				        		,rdbtnNewRadioButton_3.isSelected(),rdbtnNewRadioButton_4.isSelected()});
					}
					if(currentQuestion.getType() == 1){
						currentQuestion.setStudentAnswer(new boolean[]{checkBox.isSelected(),checkBox_1.isSelected(),checkBox_2.isSelected()
				        		,checkBox_3.isSelected(),checkBox_4.isSelected()});
					}
					if(currentQuestion.getType() == 2){
						currentQuestion.setStudentAnswer(textField33.getText());
					}
					
					//TODO clean up this code
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							new QuizEvent().fireResponseEvent(new QuizResponse(currentQuestion));
						}
					}).start();
				}
				//Load Next Question
				currentQuestion = currentQuiz.getQuestionList().get(list.getSelectedIndex());
				lblQuestion.setText("Question " + (list.getSelectedIndex()+1));
				textArea.setText(currentQuestion.getTitle());
				
				if(currentQuestion.getType() == 0){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "0");
			        lblInputAnswer.setText("Select an answer");
			        textField11.setText(currentQuestion.getAnswer1());
			        textField_11.setText(currentQuestion.getAnswer2());
			        textField_12.setText(currentQuestion.getAnswer3());
			        textField_13.setText(currentQuestion.getAnswer4());
			        textField_14.setText(currentQuestion.getAnswer5());
			        boolean[] answers = currentQuestion.getStudentAnswer();
			        if(answers != null){
			        	rdbtnNewRadioButton.setSelected(answers[0]);
			        	rdbtnNewRadioButton_1.setSelected(answers[1]);
			        	rdbtnNewRadioButton_2.setSelected(answers[2]);
			        	rdbtnNewRadioButton_3.setSelected(answers[3]);
			        	rdbtnNewRadioButton_4.setSelected(answers[4]);
			        }
			        else{
			        	rdbtnNewRadioButton.setSelected(false);
			        	rdbtnNewRadioButton_1.setSelected(false);
			        	rdbtnNewRadioButton_2.setSelected(false);
			        	rdbtnNewRadioButton_3.setSelected(false);
			        	rdbtnNewRadioButton_4.setSelected(false);
			        }
				}
				if(currentQuestion.getType() == 1){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "1");
			        lblInputAnswer.setText("Select answer");
			        textField22.setText(currentQuestion.getAnswer1());
			        textField_21.setText(currentQuestion.getAnswer2());
			        textField_22.setText(currentQuestion.getAnswer3());
			        textField_23.setText(currentQuestion.getAnswer4());
			        textField_24.setText(currentQuestion.getAnswer5());
			        boolean[] answers = currentQuestion.getStudentAnswer();
			        if(answers != null){
			        	checkBox.setSelected(answers[0]);
			        	checkBox_1.setSelected(answers[1]);
			        	checkBox_2.setSelected(answers[2]);
			        	checkBox_3.setSelected(answers[3]);
			        	checkBox_4.setSelected(answers[4]);
			        }
			        else{
			        	checkBox.setSelected(false);
			        	checkBox_1.setSelected(false);
			        	checkBox_2.setSelected(false);
			        	checkBox_3.setSelected(false);
			        	checkBox_4.setSelected(false);
			        }
			        
				}
				if(currentQuestion.getType() == 2){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "2");
			        lblInputAnswer.setText("Input Answer");
			        String text = currentQuestion.getStudentTextAnswer();
			        if(text != null)
			        	textField33.setText(text);
			        else
			        	textField33.setText("");
				}
				
				if(list.getSelectedIndex() == 0)
					btnPrevious.setVisible(false);
				else
					btnPrevious.setVisible(true);
				if(list.getSelectedIndex() == model.getSize() - 1)
					btnNext.setText("Submit");
				else
					btnNext.setText("Next");
				
			}
		});
		scrollPane.setViewportView(list);
		panel_4.setLayout(gl_panel_4);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 204, 153));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTitle = new JLabel("Title : ");
		
		JLabel lblDirections = new JLabel("Directions : ");
		
		textField = new JTextField();
		textField.setBackground(new Color(245, 245, 245));
		textField.setEnabled(false);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBackground(new Color(245, 245, 245));
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		
		lbltime = new JLabel("?Current Time?");
		
		lbltimer = new JLabel("?Time Limit?");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDirections)
						.addComponent(lblTitle))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textField)
						.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbltimer)
						.addComponent(lbltime))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTitle)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbltime)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirections)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltimer))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		
		JPanel window1 = new JPanel();
		window1.setBackground(new Color(255, 228, 196));
		
		rdbtnNewRadioButton = new JRadioButton("");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnNewRadioButton.setSelected(true);
				rdbtnNewRadioButton_1.setSelected(false);
				rdbtnNewRadioButton_2.setSelected(false);
				rdbtnNewRadioButton_3.setSelected(false);
				rdbtnNewRadioButton_4.setSelected(false);
			}
		});
		rdbtnNewRadioButton.setBackground(new Color(255, 228, 196));
		
		rdbtnNewRadioButton_1 = new JRadioButton("");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNewRadioButton.setSelected(false);
				rdbtnNewRadioButton_1.setSelected(true);
				rdbtnNewRadioButton_2.setSelected(false);
				rdbtnNewRadioButton_3.setSelected(false);
				rdbtnNewRadioButton_4.setSelected(false);
			}
		});
		rdbtnNewRadioButton_1.setBackground(new Color(255, 228, 196));
		
		rdbtnNewRadioButton_2 = new JRadioButton("");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNewRadioButton.setSelected(false);
				rdbtnNewRadioButton_1.setSelected(false);
				rdbtnNewRadioButton_2.setSelected(true);
				rdbtnNewRadioButton_3.setSelected(false);
				rdbtnNewRadioButton_4.setSelected(false);
			}
		});
		rdbtnNewRadioButton_2.setBackground(new Color(255, 228, 196));
		
		rdbtnNewRadioButton_3 = new JRadioButton("");
		rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNewRadioButton.setSelected(false);
				rdbtnNewRadioButton_1.setSelected(false);
				rdbtnNewRadioButton_2.setSelected(false);
				rdbtnNewRadioButton_3.setSelected(true);
				rdbtnNewRadioButton_4.setSelected(false);
			}
		});
		rdbtnNewRadioButton_3.setBackground(new Color(255, 228, 196));
		
		rdbtnNewRadioButton_4 = new JRadioButton("");
		rdbtnNewRadioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNewRadioButton.setSelected(false);
				rdbtnNewRadioButton_1.setSelected(false);
				rdbtnNewRadioButton_2.setSelected(false);
				rdbtnNewRadioButton_3.setSelected(false);
				rdbtnNewRadioButton_4.setSelected(true);
			}
		});
		rdbtnNewRadioButton_4.setBackground(new Color(255, 228, 196));
		
		
		
		textField11 = new JTextField();
		textField11.setEditable(false);
		textField11.setEnabled(false);
		textField11.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setEditable(false);
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setEditable(false);
		textField_12.setEnabled(false);
		textField_12.setColumns(10);
		
		textField_13 = new JTextField();
		textField_13.setEditable(false);
		textField_13.setEnabled(false);
		textField_13.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setEditable(false);
		textField_14.setEnabled(false);
		textField_14.setColumns(10);
		GroupLayout groupLayout11 = new GroupLayout(window1);
		groupLayout11.setHorizontalGroup(
			groupLayout11.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout11.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout11.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField11, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout11.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_11, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout11.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_12, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout11.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_13, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout11.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_14, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout11.setVerticalGroup(
			groupLayout11.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout11.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(textField11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_2)
						.addComponent(textField_12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_3)
						.addComponent(textField_13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout11.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_4)
						.addComponent(textField_14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(144, Short.MAX_VALUE))
		);
		window1.setLayout(groupLayout11);
		
		JPanel window2 = new JPanel();
		window2.setBackground(new Color(255, 228, 196));
		
		
		
		textField22 = new JTextField();
		textField22.setEnabled(false);
		textField22.setEditable(false);
		textField22.setColumns(10);
		
		textField_21 = new JTextField();
		textField_21.setEnabled(false);
		textField_21.setEditable(false);
		textField_21.setColumns(10);
		
		textField_22 = new JTextField();
		textField_22.setEnabled(false);
		textField_22.setEditable(false);
		textField_22.setColumns(10);
		
		textField_23 = new JTextField();
		textField_23.setEnabled(false);
		textField_23.setEditable(false);
		textField_23.setColumns(10);
		
		textField_24 = new JTextField();
		textField_24.setEnabled(false);
		textField_24.setEditable(false);
		textField_24.setColumns(10);
		
		checkBox = new JCheckBox("");
		checkBox.setBackground(new Color(255, 228, 196));
		
		checkBox_1 = new JCheckBox("");
		checkBox_1.setBackground(new Color(255, 228, 196));
		
		checkBox_2 = new JCheckBox("");
		checkBox_2.setBackground(new Color(255, 228, 196));
		
		checkBox_3 = new JCheckBox("");
		checkBox_3.setBackground(new Color(255, 228, 196));
		
		checkBox_4 = new JCheckBox("");
		checkBox_4.setBackground(new Color(255, 228, 196));
		GroupLayout groupLayout22 = new GroupLayout(window2);
		groupLayout22.setHorizontalGroup(
			groupLayout22.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout22.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout22.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout22.createSequentialGroup()
							.addComponent(checkBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField22, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout22.createSequentialGroup()
							.addComponent(checkBox_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_21, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout22.createSequentialGroup()
							.addComponent(checkBox_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_22, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout22.createSequentialGroup()
							.addComponent(checkBox_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_23, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout22.createSequentialGroup()
							.addComponent(checkBox_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_24, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout22.setVerticalGroup(
			groupLayout22.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout22.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout22.createParallelGroup(Alignment.TRAILING)
						.addComponent(checkBox)
						.addComponent(textField22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout22.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_1))
					.addGap(10)
					.addGroup(groupLayout22.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_22, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_2))
					.addGap(10)
					.addGroup(groupLayout22.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_3))
					.addGap(10)
					.addGroup(groupLayout22.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_24, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_4))
					.addContainerGap(145, Short.MAX_VALUE))
		);
		window2.setLayout(groupLayout22);
		
		JPanel window3 = new JPanel();
		window3.setBackground(new Color(255, 228, 196));
		
		
		
		textField33 = new JTextField();
		textField33.setColumns(10);
		GroupLayout groupLayout33 = new GroupLayout(window3);
		groupLayout33.setHorizontalGroup(
			groupLayout33.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout33.createSequentialGroup()
					.addGap(29)
					.addComponent(textField33, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout33.setVerticalGroup(
			groupLayout33.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout33.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField33, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(269, Short.MAX_VALUE))
		);
		window3.setLayout(groupLayout33);
		
		panel_6.add(window1,"0");
		panel_6.add(window2,"1");
		panel_6.add(window3,"2");
		
		frame.setVisible(true);
		
		quizEvent = new QuizEvent();
		quizEvent.addEventListener(new QuizEventListener() {
			
			@Override
			public void quizStoped(QuizStop e) {
				JOptionPane.showMessageDialog(null, "Quiz stopped by the teacher!");
				dispose();
			}
			
			@Override
			public void questionAnswered(QuizResponse e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public void setCurrentQuiz(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
	}
	
	  // Update the displayed time. This method is called from actionPerformed()
	  // which is itself invoked by the timer.
	  void updateDisplay() {
		  NumberFormat format = NumberFormat.getInstance();
		  
	      long now = System.currentTimeMillis(); // current time in ms
	      long elapsed = now - lastUpdate; // ms elapsed since last update
	      remaining -= elapsed; // adjust remaining time
	      lastUpdate = now; // remember this update time

	      // Convert remaining milliseconds to mm:ss format and display
	      if (remaining < 0)
	          remaining = 0;
	      int minutes = (int) (remaining / 60000);
	      int seconds = (int) ((remaining % 60000) / 1000);
	      lbltimer.setText(format.format(minutes) + ":" + format.format(seconds));

	    // If we've completed the countdown beep and display new page
	      if (remaining == 0) {
	      // Stop updating now.
	    	  JOptionPane.showMessageDialog(null, "Times Up!");
	          timer.stop();
	          dispose();
	    }
	  }
}


