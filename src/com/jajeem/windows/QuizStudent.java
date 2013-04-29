package com.jajeem.windows;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuizStudent {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblInputAnswer;
	private JLabel lblQuestion;
	private JButton btnPrevious;
	private JButton btnNext;
	@SuppressWarnings("rawtypes")
	private JList list;
	private DefaultListModel model;
	
	private Quiz currentQuiz;
	protected Question currentQuestion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizStudent window = new QuizStudent();
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
	public QuizStudent() {
		initialize();
	}

	public QuizStudent(Quiz quiz){
		setCurrentQuiz(quiz);
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
			}
		});
		frame.setBounds(100, 100, 569, 690);
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
		
		window1 card1 = new window1();
		card1.setBackground(new Color(255, 228, 196));
		window2 card2 = new window2();
		card2.setBackground(new Color(255, 228, 196));
		window3 card3 = new window3();
		card3.setBackground(new Color(255, 228, 196));
		
		final JPanel panel_6 = new JPanel();
		panel_6.setLayout(new CardLayout());
		panel_6.add(card1,"0");
		panel_6.add(card2,"1");
		panel_6.add(card3,"2");
		
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
		
		JTextArea textArea = new JTextArea();
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
				currentQuestion = currentQuiz.getQuestionList().get(list.getSelectedIndex());
				lblQuestion.setText("Question " + (list.getSelectedIndex()+1));
				if(currentQuestion.getType() == 0){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "0");
			        lblInputAnswer.setText("Select an answer");
				}
				if(currentQuestion.getType() == 1){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "1");
			        lblInputAnswer.setText("Select answer");
				}
				if(currentQuestion.getType() == 2){
					CardLayout cl = (CardLayout)(panel_6.getLayout());
			        cl.show(panel_6, "2");
			        lblInputAnswer.setText("Input Answer");
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
		panel_2.setBackground(new Color(153, 255, 153));
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
		
		JLabel lbltime = new JLabel("?Time?");
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
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addComponent(lbltime)
					.addGap(31))
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
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}

	public Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public void setCurrentQuiz(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
	}
}


@SuppressWarnings("serial")
class window1 extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Create the panel.
	 */
	public window1() {
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("");
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("");
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("");
		
		JRadioButton radioButton = new JRadioButton("");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(radioButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_1)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_2)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_3)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(144, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

}

@SuppressWarnings("serial")
class window2 extends JPanel {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Create the panel.
	 */
	public window2() {
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		JCheckBox checkBox = new JCheckBox("");
		
		JCheckBox checkBox_1 = new JCheckBox("");
		
		JCheckBox checkBox_2 = new JCheckBox("");
		
		JCheckBox checkBox_3 = new JCheckBox("");
		
		JCheckBox checkBox_4 = new JCheckBox("");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(checkBox_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(checkBox)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_1))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_2))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_3))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_4))
					.addContainerGap(145, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

}

@SuppressWarnings("serial")
class window3 extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public window3() {
		
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(269, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

}
