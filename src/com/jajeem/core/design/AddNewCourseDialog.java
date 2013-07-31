package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;

import com.alee.extended.date.WebDateField;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.jajeem.room.model.Course;

public class AddNewCourseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final WebPanel contentPanel = new WebPanel();
	private WebTextField courseNameTF;
	private WebTextField classTypeTF;
	private WebLabel courseNameLabel;
	private WebLabel instructorNameLabel;
	private WebLabel ClassTypeLabel;
	private WebLabel levelLabel;
	private WebLabel startDateLabel;
	private WebDateField startDateTF;
	private WebLabel sessionLabel;
	private WebTextField sessionTF;
	private WebComboBox instructorNameCombo;
	private WebLabel dayLabel1;
	private WebComboBox dayCombo1;
	private WebLabel startTimeLabel1;
	private WebLabel endTimeLabel1;
	private WebComboBox endTimeTF1;
	private WebLabel dayLabel2;
	private WebComboBox dayCombo2;
	private WebLabel startTimeLabel2;
	private WebComboBox startTimeTF2;
	private WebLabel endTimeLabel2;
	private WebComboBox endTimeTF2;
	private WebLabel dayLabel3;
	private WebComboBox dayCombo3;
	private WebLabel startTimeLabel3;
	private WebComboBox startTimeTF3;
	private WebLabel endTimeLabel3;
	private WebComboBox endTimeTF3;
	private WebLabel dayLabel4;
	private WebComboBox dayCombo4;
	private WebLabel startTimeLabel4;
	private WebComboBox startTimeTF4;
	private WebLabel endTimeLabel4;
	private WebComboBox endTimeTF4;
	private WebLabel dayLabel5;
	private WebComboBox dayCombo5;
	private WebLabel startTimeLabel5;
	private WebComboBox startTimeTF5;
	private WebLabel endTimeLabel5;
	private WebComboBox endTimeTF5;
	private WebComboBox levelCombo;
	private WebComboBox startTimeTF1;

	/**
	 * Create the dialog.
	 * 
	 * @param courseList
	 */
	public AddNewCourseDialog(final EventList<Course> courseList) {
		setTitle("Add new course");
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 610, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(0, 6, 2, 2));
		{
			courseNameTF = new WebTextField();
			courseNameTF.setColumns(10);
		}
		{
			courseNameLabel = new WebLabel("Course name");
		}
		contentPanel.add(courseNameLabel);
		contentPanel.add(courseNameTF);
		{
			ClassTypeLabel = new WebLabel("Class type");
		}
		contentPanel.add(ClassTypeLabel);
		{
			levelLabel = new WebLabel("Level");
			levelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			classTypeTF = new WebTextField();
			classTypeTF.setColumns(10);
		}
		contentPanel.add(classTypeTF);
		contentPanel.add(levelLabel);
		{
			levelCombo = new WebComboBox();
			levelCombo.setModel(new DefaultComboBoxModel(new String[] { "A",
					"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" }));
			contentPanel.add(levelCombo);
		}
		{
			instructorNameLabel = new WebLabel("Instructor name");
			instructorNameLabel.setAlignmentY(0.0f);
		}
		contentPanel.add(instructorNameLabel);
		{
			instructorNameCombo = new WebComboBox();
			instructorNameCombo.setModel(new DefaultComboBoxModel(new String[] {
					"Ali", "Hassan" }));
			contentPanel.add(instructorNameCombo);
		}
		{
			startDateLabel = new WebLabel("Start date");
			startDateLabel.setAlignmentY(0.0f);
			contentPanel.add(startDateLabel);
		}
		{
			startDateTF = new WebDateField(new Date());
			startDateTF.setHorizontalAlignment(SwingConstants.CENTER);
			startDateTF.setColumns(10);
			contentPanel.add(startDateTF);
		}
		{
			sessionLabel = new WebLabel("Sessions");
			contentPanel.add(sessionLabel);
		}
		{
			sessionTF = new WebTextField();
			sessionTF.setColumns(10);
			contentPanel.add(sessionTF);
		}
		{
			dayLabel1 = new WebLabel("Day");
			contentPanel.add(dayLabel1);
		}
		{
			dayCombo1 = new WebComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel1);
		}
		{
			startTimeTF1 = new WebComboBox();
			startTimeTF1.setModel(new DefaultComboBoxModel(new String[] { "7",
					"8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
					"18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF1);
		}
		{
			endTimeLabel1 = new WebLabel("End time");
			contentPanel.add(endTimeLabel1);
		}
		{
			endTimeTF1 = new WebComboBox();
			endTimeTF1.setModel(new DefaultComboBoxModel(new String[] { "7",
					"8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
					"18", "19", "20", "21", "22" }));
			endTimeTF1.setSelectedIndex(1);
			contentPanel.add(endTimeTF1);
		}
		{
			dayLabel2 = new WebLabel("Day");
			contentPanel.add(dayLabel2);
		}
		{
			dayCombo2 = new WebComboBox();
			dayCombo2.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo2);
		}
		{
			startTimeLabel2 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel2);
		}
		{
			startTimeTF2 = new WebComboBox();
			startTimeTF2.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF2);
		}
		{
			endTimeLabel2 = new WebLabel("End time");
			contentPanel.add(endTimeLabel2);
		}
		{
			endTimeTF2 = new WebComboBox();
			endTimeTF2.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF2);
		}
		{
			dayLabel3 = new WebLabel("Day");
			contentPanel.add(dayLabel3);
		}
		{
			dayCombo3 = new WebComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel3);
		}
		{
			startTimeTF3 = new WebComboBox();
			startTimeTF3.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF3);
		}
		{
			endTimeLabel3 = new WebLabel("End time");
			contentPanel.add(endTimeLabel3);
		}
		{
			endTimeTF3 = new WebComboBox();
			endTimeTF3.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF3);
		}
		{
			dayLabel4 = new WebLabel("Day");
			contentPanel.add(dayLabel4);
		}
		{
			dayCombo4 = new WebComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel4);
		}
		{
			startTimeTF4 = new WebComboBox();
			startTimeTF4.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF4);
		}
		{
			endTimeLabel4 = new WebLabel("End time");
			contentPanel.add(endTimeLabel4);
		}
		{
			endTimeTF4 = new WebComboBox();
			endTimeTF4.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF4);
		}
		{
			dayLabel5 = new WebLabel("Day");
			contentPanel.add(dayLabel5);
		}
		{
			dayCombo5 = new WebComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel5);
		}
		{
			startTimeTF5 = new WebComboBox();
			startTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF5);
		}
		{
			endTimeLabel5 = new WebLabel("End time");
			contentPanel.add(endTimeLabel5);
		}
		{
			endTimeTF5 = new WebComboBox();
			endTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF5);
		}
		{
			WebPanel buttonPane = new WebPanel();
			buttonPane.setMargin(new Insets(3, 0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				WebButton okButton = new WebButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String courseName = courseNameTF.getText();
							String instructorName = (String) instructorNameCombo
									.getSelectedItem();
							String classType = classTypeTF.getText();
							String level = (String) levelCombo
									.getSelectedItem();
							long startDate = startDateTF.getDate().getTime();
							int sessionCount = Integer.parseInt(sessionTF
									.getText());
							String day = (String) dayCombo1.getSelectedItem();
							int startTime = Integer
									.parseInt((String) startTimeTF1
											.getSelectedItem());
							int endTime = Integer.parseInt((String) endTimeTF1
									.getSelectedItem());

							/*Course course = new Course(courseName,
									instructorName, classType, level,
									startDate, sessionCount, day, startTime,
									endTime);
							courseList.add(course);*/
							dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
							WebOptionPane
									.showMessageDialog(
											getRootPane(),
											"Please fill all fields and use correct format for each field",
											"Error",
											WebOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				WebButton cancelButton = new WebButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	/*
	 * 
	 * * * Edit
	 */

	public AddNewCourseDialog(final EventList<Course> courseList,
			final Course course) {
		setTitle("Edit course");
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 610, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(0, 6, 2, 2));
		{
			courseNameTF = new WebTextField();
			courseNameTF.setColumns(10);
		}
		{
			courseNameLabel = new WebLabel("Course name");
		}
		contentPanel.add(courseNameLabel);
		contentPanel.add(courseNameTF);
		{
			ClassTypeLabel = new WebLabel("Class type");
		}
		contentPanel.add(ClassTypeLabel);
		{
			levelLabel = new WebLabel("Level");
			levelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			classTypeTF = new WebTextField();
			classTypeTF.setColumns(10);
		}
		contentPanel.add(classTypeTF);
		contentPanel.add(levelLabel);
		{
			levelCombo = new WebComboBox();
			levelCombo.setModel(new DefaultComboBoxModel(new String[] { "A",
					"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" }));
			contentPanel.add(levelCombo);
		}
		{
			instructorNameLabel = new WebLabel("Instructor name");
			instructorNameLabel.setAlignmentY(0.0f);
		}
		contentPanel.add(instructorNameLabel);
		{
			instructorNameCombo = new WebComboBox();
			instructorNameCombo.setModel(new DefaultComboBoxModel(new String[] {
					"Ali", "Hassan" }));
			contentPanel.add(instructorNameCombo);
		}
		{
			startDateLabel = new WebLabel("Start date");
			startDateLabel.setAlignmentY(0.0f);
			contentPanel.add(startDateLabel);
		}
		{
			startDateTF = new WebDateField(new Date());
			startDateTF.setHorizontalAlignment(SwingConstants.CENTER);
			startDateTF.setColumns(10);
			contentPanel.add(startDateTF);
		}
		{
			sessionLabel = new WebLabel("Sessions");
			contentPanel.add(sessionLabel);
		}
		{
			sessionTF = new WebTextField();
			sessionTF.setColumns(10);
			contentPanel.add(sessionTF);
		}
		{
			dayLabel1 = new WebLabel("Day");
			contentPanel.add(dayLabel1);
		}
		{
			dayCombo1 = new WebComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel1);
		}
		{
			startTimeTF1 = new WebComboBox();
			startTimeTF1.setModel(new DefaultComboBoxModel(new String[] { "7",
					"8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
					"18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF1);
		}
		{
			endTimeLabel1 = new WebLabel("End time");
			contentPanel.add(endTimeLabel1);
		}
		{
			endTimeTF1 = new WebComboBox();
			endTimeTF1.setModel(new DefaultComboBoxModel(new String[] { "7",
					"8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
					"18", "19", "20", "21", "22" }));
			endTimeTF1.setSelectedIndex(1);
			contentPanel.add(endTimeTF1);
		}
		{
			dayLabel2 = new WebLabel("Day");
			contentPanel.add(dayLabel2);
		}
		{
			dayCombo2 = new WebComboBox();
			dayCombo2.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo2);
		}
		{
			startTimeLabel2 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel2);
		}
		{
			startTimeTF2 = new WebComboBox();
			startTimeTF2.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF2);
		}
		{
			endTimeLabel2 = new WebLabel("End time");
			contentPanel.add(endTimeLabel2);
		}
		{
			endTimeTF2 = new WebComboBox();
			endTimeTF2.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF2);
		}
		{
			dayLabel3 = new WebLabel("Day");
			contentPanel.add(dayLabel3);
		}
		{
			dayCombo3 = new WebComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel3);
		}
		{
			startTimeTF3 = new WebComboBox();
			startTimeTF3.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF3);
		}
		{
			endTimeLabel3 = new WebLabel("End time");
			contentPanel.add(endTimeLabel3);
		}
		{
			endTimeTF3 = new WebComboBox();
			endTimeTF3.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF3);
		}
		{
			dayLabel4 = new WebLabel("Day");
			contentPanel.add(dayLabel4);
		}
		{
			dayCombo4 = new WebComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel4);
		}
		{
			startTimeTF4 = new WebComboBox();
			startTimeTF4.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF4);
		}
		{
			endTimeLabel4 = new WebLabel("End time");
			contentPanel.add(endTimeLabel4);
		}
		{
			endTimeTF4 = new WebComboBox();
			endTimeTF4.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF4);
		}
		{
			dayLabel5 = new WebLabel("Day");
			contentPanel.add(dayLabel5);
		}
		{
			dayCombo5 = new WebComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel("Start time");
			contentPanel.add(startTimeLabel5);
		}
		{
			startTimeTF5 = new WebComboBox();
			startTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(startTimeTF5);
		}
		{
			endTimeLabel5 = new WebLabel("End time");
			contentPanel.add(endTimeLabel5);
		}
		{
			endTimeTF5 = new WebComboBox();
			endTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF5);
		}
		{
			WebPanel buttonPane = new WebPanel();
			buttonPane.setMargin(new Insets(3, 0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				WebButton okButton = new WebButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String courseName = courseNameTF.getText();
							String instructorName = (String) instructorNameCombo
									.getSelectedItem();
							String classType = classTypeTF.getText();
							String level = (String) levelCombo
									.getSelectedItem();
							long startDate = startDateTF.getDate().getTime();
							int sessionCount = Integer.parseInt(sessionTF
									.getText());
							String day = (String) dayCombo1.getSelectedItem();
							int startTime = Integer
									.parseInt((String) startTimeTF1
											.getSelectedItem());
							int endTime = Integer.parseInt((String) endTimeTF1
									.getSelectedItem());

							/*Course course = new Course(courseName,
									instructorName, classType, level,
									startDate, sessionCount, day, startTime,
									endTime);
							courseList.add(course);
							*/
							dispose();
						} catch (Exception e1) {
							e1.printStackTrace();
							WebOptionPane
									.showMessageDialog(
											getRootPane(),
											"Please fill all fields and use correct format for each field",
											"Error",
											WebOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				WebButton cancelButton = new WebButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}

		courseNameTF.setText(course.getName());
		instructorNameCombo.setSelectedItem(course.getInstructor());
		classTypeTF.setText(course.getClassType());
		levelCombo.setSelectedItem(course.getLevel());
		Date startDate = new Date(course.getStartDate());
		startDateTF.setDate(startDate);
		sessionTF.setText(String.valueOf(course.getSession()));
		
		dayCombo1.setSelectedItem(course.getDay1());
		dayCombo2.setSelectedItem(course.getDay2());
		dayCombo3.setSelectedItem(course.getDay3());
		dayCombo4.setSelectedItem(course.getDay4());
		dayCombo5.setSelectedItem(course.getDay5());
		
		startTimeTF1.setSelectedItem(course.getStartTime1());
		startTimeTF2.setSelectedItem(course.getStartTime2());
		startTimeTF3.setSelectedItem(course.getStartTime3());
		startTimeTF4.setSelectedItem(course.getStartTime4());
		startTimeTF5.setSelectedItem(course.getStartTime5());
		
		endTimeTF1.setSelectedItem(course.getEndTime1());
		endTimeTF2.setSelectedItem(course.getEndTime2());
		endTimeTF3.setSelectedItem(course.getEndTime3());
		endTimeTF4.setSelectedItem(course.getEndTime4());
		endTimeTF5.setSelectedItem(course.getEndTime5());
	}

}
