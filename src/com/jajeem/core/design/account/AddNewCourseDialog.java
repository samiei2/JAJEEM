package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import com.jajeem.core.model.Instructor;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.i18n;

public class AddNewCourseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final WebPanel contentPanel = new WebPanel();
	private WebTextField courseNameTF;
	private WebComboBox classTypeTF;
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
	 * Create the dialog. New Course Mode
	 * 
	 * @param courseList
	 * @throws Exception 
	 * @wbp.parser.constructor
	 */
	public AddNewCourseDialog(final EventList<Course> courseList,
			final EventList<Instructor> instructorList) throws Exception {
		setTitle(i18n.getParam("Add new course"));
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
			courseNameLabel = new WebLabel(i18n.getParam("Course name"));
		}
		contentPanel.add(courseNameLabel);
		contentPanel.add(courseNameTF);
		{
			ClassTypeLabel = new WebLabel(i18n.getParam("Class type"));
		}
		contentPanel.add(ClassTypeLabel);
		{
			levelLabel = new WebLabel(i18n.getParam("Level"));
			levelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			classTypeTF = new WebComboBox();
			classTypeTF.setModel(new DefaultComboBoxModel(new String[] {"Termic", "Intensive", "Super-Intensive", "Thursday", "Friday"}));
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
			instructorNameLabel = new WebLabel(i18n.getParam("Instructor name"));
			instructorNameLabel.setAlignmentY(0.0f);
		}
		contentPanel.add(instructorNameLabel);
		{
			instructorNameCombo = new WebComboBox();
			ArrayList<String> insList = new ArrayList<String>();
			for (Instructor ins : instructorList) {
				insList.add(ins.getUsername());
			}

			instructorNameCombo.setModel(new DefaultComboBoxModel(insList
					.toArray()));
			contentPanel.add(instructorNameCombo);
		}
		{
			startDateLabel = new WebLabel(i18n.getParam("Start date"));
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
			sessionLabel = new WebLabel(i18n.getParam("Sessions"));
			contentPanel.add(sessionLabel);
		}
		{
			sessionTF = new WebTextField();
			sessionTF.setColumns(10);
			contentPanel.add(sessionTF);
		}
		{
			dayLabel1 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel1);
		}
		{
			dayCombo1 = new WebComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel1 = new WebLabel(i18n.getParam("End time"));
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
			startTimeLabel2 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel2 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel3 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel3);
		}
		{
			dayCombo3 = new WebComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel3 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel4 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel4);
		}
		{
			dayCombo4 = new WebComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel4 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel5 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel5);
		}
		{
			dayCombo5 = new WebComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel5 = new WebLabel(i18n.getParam("End time"));
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
				WebButton okButton = new WebButton(i18n.getParam("OK"));
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String courseName = courseNameTF.getText();
							String instructorName = (String) instructorNameCombo
									.getSelectedItem();
							String classType = (String) classTypeTF.getSelectedItem();
							String level = (String) levelCombo
									.getSelectedItem();
							long startDate = startDateTF.getDate().getTime();
							int sessionCount = Integer.parseInt(sessionTF
									.getText());

							String day1 = "";
							int startTime1 = -1;
							int endTime1 = -1;
							String day2 = "";
							int startTime2 = -1;
							int endTime2 = -1;
							String day3 = "";
							int startTime3 = -1;
							int endTime3 = -1;
							String day4 = "";
							int startTime4 = -1;
							int endTime4 = -1;
							String day5 = "";
							int startTime5 = -1;
							int endTime5 = -1;

							if (!dayCombo1.getSelectedItem().equals("")) {
								day1 = (String) dayCombo1.getSelectedItem();
								startTime1 = Integer
										.parseInt((String) startTimeTF1
												.getSelectedItem());
								endTime1 = Integer.parseInt((String) endTimeTF1
										.getSelectedItem());
								if (startTime1 > endTime1) {
									WebOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													WebOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							if (!dayCombo2.getSelectedItem().equals("")) {
								day2 = (String) dayCombo2.getSelectedItem();
								startTime2 = Integer
										.parseInt((String) startTimeTF2
												.getSelectedItem());
								endTime2 = Integer.parseInt((String) endTimeTF2
										.getSelectedItem());
								if (startTime2 > endTime2) {
									WebOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													WebOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							if (!dayCombo3.getSelectedItem().equals("")) {
								day3 = (String) dayCombo3.getSelectedItem();
								startTime3 = Integer
										.parseInt((String) startTimeTF3
												.getSelectedItem());
								endTime3 = Integer.parseInt((String) endTimeTF3
										.getSelectedItem());
								if (startTime3 > endTime3) {
									WebOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													WebOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							if (!dayCombo4.getSelectedItem().equals("")) {
								day4 = (String) dayCombo4.getSelectedItem();
								startTime4 = Integer
										.parseInt((String) startTimeTF4
												.getSelectedItem());
								endTime4 = Integer.parseInt((String) endTimeTF4
										.getSelectedItem());
								if (startTime4 > endTime4) {
									WebOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													WebOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							if (!dayCombo5.getSelectedItem().equals("")) {
								day5 = (String) dayCombo5.getSelectedItem();
								startTime5 = Integer
										.parseInt((String) startTimeTF5
												.getSelectedItem());
								endTime5 = Integer.parseInt((String) endTimeTF5
										.getSelectedItem());
								if (startTime5 > endTime5) {
									WebOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													WebOptionPane.ERROR_MESSAGE);
									return;
								}
							}

							Course course = new Course(courseName,
									instructorName, classType, level,
									startDate, sessionCount, day1, startTime1,
									endTime1, day2, startTime2, endTime2, day3,
									startTime3, endTime3, day4, startTime4,
									endTime4, day5, startTime5, endTime5);
							courseList.add(course);

							for (Instructor instructor : instructorList) {
								if (course.getInstructor().equals(
										instructor.getUsername())) {
									course.setInstructorId(instructor.getId());
								}
							}

							RoomService rs = new RoomService();
							rs.getCourseDAO().create(course);

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
				WebButton cancelButton = new WebButton(i18n.getParam("Cancel"));
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

	/**
	 * Create the dialog. Edit Course Mode
	 * 
	 * @param courseList
	 * @param course
	 * @param instructorList
	 * @param selected
	 *            courseList
	 * @throws Exception 
	 * 
	 */
	public AddNewCourseDialog(final EventList<Course> courseList,
			final Course course, final EventList<Course> selectedCourse,
			EventList<Instructor> instructorList) throws Exception {
		setTitle(i18n.getParam("Edit course"));
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
			courseNameLabel = new WebLabel(i18n.getParam("Course name"));
		}
		contentPanel.add(courseNameLabel);
		contentPanel.add(courseNameTF);
		{
			ClassTypeLabel = new WebLabel(i18n.getParam("Class type"));
		}
		contentPanel.add(ClassTypeLabel);
		{
			levelLabel = new WebLabel(i18n.getParam("Level"));
			levelLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			classTypeTF = new WebComboBox();
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
			instructorNameLabel = new WebLabel(i18n.getParam("Instructor name"));
			instructorNameLabel.setAlignmentY(0.0f);
		}
		contentPanel.add(instructorNameLabel);
		{
			instructorNameCombo = new WebComboBox();
			ArrayList<String> insList = new ArrayList<String>();
			for (Instructor ins : instructorList) {
				insList.add(ins.getUsername());
			}

			instructorNameCombo.setModel(new DefaultComboBoxModel(insList
					.toArray()));
			contentPanel.add(instructorNameCombo);
		}
		{
			startDateLabel = new WebLabel(i18n.getParam("Start date"));
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
			sessionLabel = new WebLabel(i18n.getParam("Sessions"));
			contentPanel.add(sessionLabel);
		}
		{
			sessionTF = new WebTextField();
			sessionTF.setColumns(10);
			contentPanel.add(sessionTF);
		}
		{
			dayLabel1 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel1);
		}
		{
			dayCombo1 = new WebComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel1 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel2 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel2);
		}
		{
			dayCombo2 = new WebComboBox();
			dayCombo2.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo2);
		}
		{
			startTimeLabel2 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel2 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel3 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel3);
		}
		{
			dayCombo3 = new WebComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel3 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel4 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel4);
		}
		{
			dayCombo4 = new WebComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel4 = new WebLabel(i18n.getParam("End time"));
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
			dayLabel5 = new WebLabel(i18n.getParam("Day"));
			contentPanel.add(dayLabel5);
		}
		{
			dayCombo5 = new WebComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel(i18n.getParam("Start time"));
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
			endTimeLabel5 = new WebLabel(i18n.getParam("End time"));
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
				WebButton okButton = new WebButton(i18n.getParam("OK"));
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String courseName = courseNameTF.getText();
							String instructorName = (String) instructorNameCombo
									.getSelectedItem();
							String classType = (String) classTypeTF.getSelectedItem();
							String level = (String) levelCombo
									.getSelectedItem();
							long startDate = startDateTF.getDate().getTime();
							int sessionCount = Integer.parseInt(sessionTF
									.getText());

							String day1 = "";
							int startTime1 = -1;
							int endTime1 = -1;
							String day2 = "";
							int startTime2 = -1;
							int endTime2 = -1;
							String day3 = "";
							int startTime3 = -1;
							int endTime3 = -1;
							String day4 = "";
							int startTime4 = -1;
							int endTime4 = -1;
							String day5 = "";
							int startTime5 = -1;
							int endTime5 = -1;

							if (!dayCombo1.getSelectedItem().equals("")) {
								day1 = (String) dayCombo1.getSelectedItem();
								startTime1 = Integer
										.parseInt((String) startTimeTF1
												.getSelectedItem());
								endTime1 = Integer.parseInt((String) endTimeTF1
										.getSelectedItem());
							}
							if (!dayCombo2.getSelectedItem().equals("")) {
								day2 = (String) dayCombo2.getSelectedItem();
								startTime2 = Integer
										.parseInt((String) startTimeTF2
												.getSelectedItem());
								endTime2 = Integer.parseInt((String) endTimeTF2
										.getSelectedItem());
							}
							if (!dayCombo3.getSelectedItem().equals("")) {
								day3 = (String) dayCombo3.getSelectedItem();
								startTime3 = Integer
										.parseInt((String) startTimeTF3
												.getSelectedItem());
								endTime3 = Integer.parseInt((String) endTimeTF3
										.getSelectedItem());
							}
							if (!dayCombo4.getSelectedItem().equals("")) {
								day4 = (String) dayCombo4.getSelectedItem();
								startTime4 = Integer
										.parseInt((String) startTimeTF4
												.getSelectedItem());
								endTime4 = Integer.parseInt((String) endTimeTF4
										.getSelectedItem());
							}
							if (!dayCombo5.getSelectedItem().equals("")) {
								day5 = (String) dayCombo5.getSelectedItem();
								startTime5 = Integer
										.parseInt((String) startTimeTF5
												.getSelectedItem());
								endTime5 = Integer.parseInt((String) endTimeTF5
										.getSelectedItem());
							}

							courseList.removeAll(selectedCourse);
							Course course = new Course(courseName,
									instructorName, classType, level,
									startDate, sessionCount, day1, startTime1,
									endTime1, day2, startTime2, endTime2, day3,
									startTime3, endTime3, day4, startTime4,
									endTime4, day5, startTime5, endTime5);
							courseList.add(course);

							RoomService rs = new RoomService();
							rs.getCourseDAO().update(course);

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
				WebButton cancelButton = new WebButton(i18n.getParam("Cancel"));
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
		classTypeTF.setSelectedItem(course.getClassType());
		levelCombo.setSelectedItem(course.getLevel());
		Date startDate = new Date(course.getStartDate());
		startDateTF.setDate(startDate);
		sessionTF.setText(String.valueOf(course.getSession()));

		dayCombo1.setSelectedItem(course.getDay1());
		dayCombo2.setSelectedItem(course.getDay2());
		dayCombo3.setSelectedItem(course.getDay3());
		dayCombo4.setSelectedItem(course.getDay4());
		dayCombo5.setSelectedItem(course.getDay5());

		startTimeTF1.setSelectedItem(String.valueOf(course.getStartTime1()));
		startTimeTF2.setSelectedItem(String.valueOf(course.getStartTime2()));
		startTimeTF3.setSelectedItem(String.valueOf(course.getStartTime3()));
		startTimeTF4.setSelectedItem(String.valueOf(course.getStartTime4()));
		startTimeTF5.setSelectedItem(String.valueOf(course.getStartTime5()));

		endTimeTF1.setSelectedItem(String.valueOf(course.getEndTime1()));
		endTimeTF2.setSelectedItem(String.valueOf(course.getEndTime2()));
		endTimeTF3.setSelectedItem(String.valueOf(course.getEndTime3()));
		endTimeTF4.setSelectedItem(String.valueOf(course.getEndTime4()));
		endTimeTF5.setSelectedItem(String.valueOf(course.getEndTime5()));
	}

}
