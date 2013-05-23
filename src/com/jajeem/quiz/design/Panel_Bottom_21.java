package com.jajeem.quiz.design;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.Config;

@SuppressWarnings("serial")
public class Panel_Bottom_21 extends WebPanel {

	private Question currentQuestion;
	private WebTextField webTextField;
	private WebTextArea webTextArea;
	private WebTable webTable;
	private WebComboBox webComboBox;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private Timer timer; // Updates the count every second
	private long remaining; // How many milliseconds remain in the countdown.
	private long lastUpdate; // When count was last updated
	private int total;
	private Panel_Bottom_2 parentPanel;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private Quiz currentQuiz;
	
	/**
	 * Create the panel.
	 */
	public Panel_Bottom_21(Panel_Bottom_2 Panel_Bottom_2) {
		this.parentPanel = Panel_Bottom_2;
		quizResponse = parentPanel.getQuizResponse();
		currentQuiz = parentPanel.getCurrentQuiz();
		
		webComboBox = new WebComboBox();
		webComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				currentQuestion = currentQuiz.getQuestionList().get(webComboBox.getSelectedIndex());
				webTextArea.setText(currentQuestion.getTitle());
				if(currentQuestion.getType() == 0){
					webTextField.setText("Single Choice");
				}
				else if(currentQuestion.getType() == 1){
					webTextField.setText("Multiple Choice");
				}
				else {
					webTextField.setText("Essay");
				}
				
				DefaultTableModel model = (DefaultTableModel) webTable.getModel();
				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}
				
				
				for (int i = 0; i < quizResponse.get(webComboBox.getSelectedIndex()).size(); i++) {
					QuizResponse ex = quizResponse.get(webComboBox.getSelectedIndex()).get(i);
					Student student = ex.getStudent();
					Question question = ex.getQuestion();
					ImageIcon imgToolTip = new ImageIcon("icons/bullet-red.png");
					if(ex.getQuestion().isResponseValid())
						imgToolTip = new ImageIcon("icons/bullet-green.png");
					
					String StudentOption = "";
					String QuestionOption = "";
					if(question.getType() == 0){ // setting student's answer
						if(question.getStudentAnswer()[0])
							StudentOption = "First Option";
						if(question.getStudentAnswer()[1])
							StudentOption = "Second Option";
						if(question.getStudentAnswer()[2])
							StudentOption = "Third Option";
						if(question.getStudentAnswer()[3])
							StudentOption = "Fourth Option";
						if(question.getStudentAnswer()[4])
							StudentOption = "Fifth Option";
						if(StudentOption == "")
							StudentOption = "None Selected";
					}
					else if(question.getType() == 1){
						if(question.getStudentAnswer()[0])
							StudentOption += "First Option,";
						if(question.getStudentAnswer()[1])
							StudentOption += "Second Option,";
						if(question.getStudentAnswer()[2])
							StudentOption += "Third Option,";
						if(question.getStudentAnswer()[3])
							StudentOption += "Fourth Option,";
						if(question.getStudentAnswer()[4])
							StudentOption += "Fifth Option";
						if(StudentOption == "")
							StudentOption = "None Selected";
					}
					else
						StudentOption = question.getStudentTextAnswer();
					
					Question temp2 = currentQuestion;
					if(temp2.getType() == 0){ // setting questions correct answer
						if(temp2.getCorrectAnswer()[0])
							QuestionOption = "First Option";
						if(temp2.getCorrectAnswer()[1])
							QuestionOption = "Second Option";
						if(temp2.getCorrectAnswer()[2])
							QuestionOption = "Third Option";
						if(temp2.getCorrectAnswer()[3])
							QuestionOption = "Fourth Option";
						if(temp2.getCorrectAnswer()[4])
							QuestionOption = "Fifth Option";
						if(QuestionOption == "")
							QuestionOption = "None Selected";
					}
					else if(temp2.getType() == 0){
						if(temp2.getCorrectAnswer()[0])
							QuestionOption += "First Option,";
						if(temp2.getCorrectAnswer()[1])
							QuestionOption += "Second Option,";
						if(temp2.getCorrectAnswer()[2])
							QuestionOption += "Third Option,";
						if(temp2.getCorrectAnswer()[3])
							QuestionOption += "Fourth Option,";
						if(temp2.getCorrectAnswer()[4])
							QuestionOption += "Fifth Option";
						if(QuestionOption == "")
							QuestionOption = "None Selected";
					}
					else
						QuestionOption = "N/A";
					
					model.addRow(new Object[]{
							imgToolTip,
							student.getId(),
							student.getFirstName() + " " + student.getLastName(),
							QuestionOption,
							StudentOption
					});
				}
			}
		});
		
		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");
		
		webTextField = new WebTextField();
		webTextField.setEnabled(false);
		webTextField.setEditable(false);
		
		WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebPanel webPanel_1 = new WebPanel();
		
		WebLabel wblblQuestion_1 = new WebLabel();
		wblblQuestion_1.setText("Question");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 994, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
							.addGap(138)
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 483, GroupLayout.PREFERRED_SIZE)
							.addGap(12))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(100))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(194)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		WebLabel wblblResults = new WebLabel();
		wblblResults.setText("Results");
		
		WebLabel wblbltotal = new WebLabel();
		wblbltotal.setText("[Total]");
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("?");
		
		WebLabel wblblAnswered = new WebLabel();
		wblblAnswered.setText("[Answered]");
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("?");
		
		WebLabel wblblcorrect = new WebLabel();
		wblblcorrect.setText("[Correct]");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("?");
		
		WebLabel wblblCorrectRate = new WebLabel();
		wblblCorrectRate.setText("Correct Rate");
		
		WebLabel webLabel_3 = new WebLabel();
		webLabel_3.setText("?");
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(24))
						.addGroup(Alignment.LEADING, gl_webPanel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(375)
							.addComponent(wblbltotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wblblAnswered, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wblblcorrect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(wblblCorrectRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
					.addGap(0))
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblbltotal, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addComponent(wblblAnswered, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(webLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(wblblcorrect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblCorrectRate, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new WebTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Number", "Student Name", "Correct Answer", "Answer"
			}
		));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(91);
		webTable.getColumnModel().getColumn(3).setPreferredWidth(101);
		webScrollPane_1.setViewportView(webTable);
		webPanel_1.setLayout(gl_webPanel_1);
		
		WebLabel wblblQuizInfo = new WebLabel();
		wblblQuizInfo.setText("Quiz Info.");
		
		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction ");
		
		webTextField_1 = new WebTextField();
		webTextField_1.setEditable(false);
		webTextField_1.setEnabled(false);
		
		WebLabel wblblTime = new WebLabel();
		wblblTime.setText("Time Left");
		
		webTextField_2 = new WebTextField();
		webTextField_2.setEditable(false);
		webTextField_2.setEnabled(false);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(wblblQuizInfo, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(wblblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(wblblDirection, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuizInfo, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblTime, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		
		webTextArea = new WebTextArea();
		webTextArea.setEnabled(false);
		webTextArea.setEditable(false);
		webScrollPane.setViewportView(webTextArea);
		setLayout(groupLayout);
		
	}
	
	public void LoadQuiz(Quiz quiz){
		currentQuiz = quiz;
		if(currentQuiz != null){
			webTextField_1.setText(currentQuiz.getTitle());
			webTextField_2.setText(String.valueOf(currentQuiz.getTime()));
			
			/////Setting the timer
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					updateDisplay();
				}

				private void updateDisplay() {
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
				    webTextField_2.setText(format.format(minutes) + ":" + format.format(seconds));

				    // If we've completed the countdown beep and display new page
				    if (remaining == 0) {
				    // Stop updating now.
				        timer.stop();
						try {
							new Config();
							ServerService serv = new ServerService();
							StopQuizCommand cmd = new StopQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
							serv.send(cmd);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
				    }
				}
			};
						  
						
			if(currentQuiz.getTime() != 0){
				webTextField_2.setText(String.valueOf(currentQuiz.getTime()).concat(":00"));
				remaining = currentQuiz.getTime()*60000;
				timer = new Timer(1000,taskPerformer);
				timer.setInitialDelay(0);
				lastUpdate = System.currentTimeMillis();
				timer.start();
			}
			else{
				webTextField_2.setText("");
			}
			//webTextField_2.setText(String.valueOf(new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(Calendar.getInstance().getTime())));
			
			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
				webComboBox.addItem("Question " + i);
			}
			
			webComboBox.setSelectedIndex(0);
			
		}
	}

	public Panel_Bottom_2 getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(Panel_Bottom_2 parentPanel) {
		this.parentPanel = parentPanel;
	}

	public void QuestionAnswered(QuizResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;

		if(currentQuestion != null && question != null && student != null){
			int index = -1;
			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {// find question index in the response list
				if(currentQuiz.getQuestionList().get(i).getId() == question.getId()){
					index = i;
					break;
				}
			}
			for (int i = 0; i < quizResponse.size(); i++) {// search in results,if this question is already answered by this student,then update,otherwise save
				for (int j = 0; j < quizResponse.get(i).size(); j++) { // search in student's response list of the question quizresponse.get(i) = list of responses of students who answered this question
					if(quizResponse.get(i).get(j).getStudent().getId() == student.getId() 
					&& quizResponse.get(i).get(j).getQuestion().getId() == question.getId()){
						quizResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if(!found)
				quizResponse.get(index).add(e);
			
			if(question.getId() == currentQuestion.getId()){// if the student id is equal to current students id then show it's result otherwise just save it
				DefaultTableModel model = (DefaultTableModel) webTable.getModel(); 
				String StudentOption = "";
				String QuestionOption = "";
				if(question.getType() == 0){ // setting student's answer
					if(question.getStudentAnswer()[0])
						StudentOption = "First Option";
					if(question.getStudentAnswer()[1])
						StudentOption = "Second Option";
					if(question.getStudentAnswer()[2])
						StudentOption = "Third Option";
					if(question.getStudentAnswer()[3])
						StudentOption = "Fourth Option";
					if(question.getStudentAnswer()[4])
						StudentOption = "Fifth Option";
					if(StudentOption == "")
						StudentOption = "None Selected";
				}
				else if(question.getType() == 1){
					if(question.getStudentAnswer()[0])
						StudentOption += "First Option,";
					if(question.getStudentAnswer()[1])
						StudentOption += "Second Option,";
					if(question.getStudentAnswer()[2])
						StudentOption += "Third Option,";
					if(question.getStudentAnswer()[3])
						StudentOption += "Fourth Option,";
					if(question.getStudentAnswer()[4])
						StudentOption += "Fifth Option";
					if(StudentOption == "")
						StudentOption = "None Selected";
				}
				else
					StudentOption = question.getStudentTextAnswer();
				
				Question temp2 = currentQuiz.getQuestionList().get(index);
				if(temp2.getType() == 0){ // setting questions correct answer
					if(temp2.getCorrectAnswer()[0])
						QuestionOption = "First Option";
					if(temp2.getCorrectAnswer()[1])
						QuestionOption = "Second Option";
					if(temp2.getCorrectAnswer()[2])
						QuestionOption = "Third Option";
					if(temp2.getCorrectAnswer()[3])
						QuestionOption = "Fourth Option";
					if(temp2.getCorrectAnswer()[4])
						QuestionOption = "Fifth Option";
					if(QuestionOption == "")
						QuestionOption = "None Selected";
				}
				else if(temp2.getType() == 0){
					if(temp2.getCorrectAnswer()[0])
						QuestionOption += "First Option,";
					if(temp2.getCorrectAnswer()[1])
						QuestionOption += "Second Option,";
					if(temp2.getCorrectAnswer()[2])
						QuestionOption += "Third Option,";
					if(temp2.getCorrectAnswer()[3])
						QuestionOption += "Fourth Option,";
					if(temp2.getCorrectAnswer()[4])
						QuestionOption += "Fifth Option";
					if(QuestionOption == "")
						QuestionOption = "None Selected";
				}
				else
					QuestionOption = "N/A";
				
				ImageIcon imgToolTip = new ImageIcon("icons/bullet-red.png");
				if(e.getQuestion().isResponseValid())
					imgToolTip = new ImageIcon("icons/bullet-green.png");
				
				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}
				
				for (int i = 0; i < quizResponse.get(index).size(); i++) {
					model.addRow(new Object[]{
							imgToolTip,
							student.getId(),
							student.getFirstName() + " " + student.getLastName(),
							QuestionOption,
							StudentOption
					});
				}
			}
		}
	}
	
	@SuppressWarnings("serial")
	class WebTableModel extends DefaultTableModel{
		public WebTableModel(Object[][] objects, String[] strings) {
			super(objects, strings);
		}

		@Override
		public Class<?> getColumnClass(int arg0) {
			// TODO Auto-generated method stub
			if(arg0 == 0)
				return Icon.class;
			return super.getColumnClass(arg0);
		}
	}
}


