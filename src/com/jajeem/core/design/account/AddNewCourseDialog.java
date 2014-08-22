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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;

import com.alee.extended.date.WebDateField;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Instructor;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.ui.combobox.JajeemComboBox;
import com.jajeem.util.i18n;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AddNewCourseDialog extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final WebPanel contentPanel = new WebPanel();
	private WebTextField courseNameTF;
	private JajeemComboBox classTypeTF;
	private WebLabel courseNameLabel;
	private WebLabel instructorNameLabel;
	private WebLabel ClassTypeLabel;
	private WebLabel levelLabel;
	private WebLabel startDateLabel;
	private WebDateField startDateTF;
	private WebLabel sessionLabel;
	private WebTextField sessionTF;
	private JajeemComboBox instructorNameCombo;
	private WebLabel dayLabel1;
	private JajeemComboBox dayCombo1;
	private WebLabel startTimeLabel1;
	private WebLabel endTimeLabel1;
	private JajeemComboBox endTimeTF1;
	private WebLabel dayLabel2;
	private JajeemComboBox dayCombo2;
	private WebLabel startTimeLabel2;
	private JajeemComboBox startTimeTF2;
	private WebLabel endTimeLabel2;
	private JajeemComboBox endTimeTF2;
	private WebLabel dayLabel3;
	private JajeemComboBox dayCombo3;
	private WebLabel startTimeLabel3;
	private JajeemComboBox startTimeTF3;
	private WebLabel endTimeLabel3;
	private JajeemComboBox endTimeTF3;
	private WebLabel dayLabel4;
	private JajeemComboBox dayCombo4;
	private WebLabel startTimeLabel4;
	private JajeemComboBox startTimeTF4;
	private WebLabel endTimeLabel4;
	private JajeemComboBox endTimeTF4;
	private WebLabel dayLabel5;
	private JajeemComboBox dayCombo5;
	private WebLabel startTimeLabel5;
	private JajeemComboBox startTimeTF5;
	private WebLabel endTimeLabel5;
	private JajeemComboBox endTimeTF5;
	private JajeemComboBox levelCombo;
	private JajeemComboBox startTimeTF1;
	private CustomAccountButton okButton_1;
	private CustomAccountButton cancelButton_1;

	/**
	 * Create the dialog. New Course Mode
	 * 
	 * @param courseList
	 * @throws Exception
	 * @wbp.parser.constructor
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddNewCourseDialog(final EventList<Course> courseList,
			final EventList<Instructor> instructorList) throws Exception {
		setTitle(i18n.getParam("Add new course"));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 946, 389);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setOpaque(false);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.NORTH);
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
			classTypeTF = new JajeemComboBox();
			classTypeTF.setModel(new DefaultComboBoxModel(new String[] {
					"Termic", "Intensive", "Super-Intensive", "Thursday",
					"Friday" }));
		}
		contentPanel.add(classTypeTF);
		contentPanel.add(levelLabel);
		{
			levelCombo = new JajeemComboBox();
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
			instructorNameCombo = new JajeemComboBox();
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
			dayCombo1 = new JajeemComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel1);
		}
		{
			startTimeTF1 = new JajeemComboBox();
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
			endTimeTF1 = new JajeemComboBox();
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
			dayCombo2 = new JajeemComboBox();
			dayCombo2.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo2);
		}
		{
			startTimeLabel2 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel2);
		}
		{
			startTimeTF2 = new JajeemComboBox();
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
			endTimeTF2 = new JajeemComboBox();
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
			dayCombo3 = new JajeemComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel3);
		}
		{
			startTimeTF3 = new JajeemComboBox();
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
			endTimeTF3 = new JajeemComboBox();
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
			dayCombo4 = new JajeemComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel4);
		}
		{
			startTimeTF4 = new JajeemComboBox();
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
			endTimeTF4 = new JajeemComboBox();
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
			dayCombo5 = new JajeemComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel5);
		}
		{
			startTimeTF5 = new JajeemComboBox();
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
			endTimeTF5 = new JajeemComboBox();
			endTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF5);
		}
		{
			WebPanel buttonPane = new WebPanel();
			buttonPane.setOpaque(false);
			buttonPane.setMargin(new Insets(3, 0, 0, 0));
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton_1 = new CustomAccountButton("/icons/noa_en/accountokbutton.png");
				okButton_1.setUndecorated(true);
				okButton_1.setActionCommand("OK");
				okButton_1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String courseName = courseNameTF.getText();
							String instructorName = (String) instructorNameCombo
									.getSelectedItem();
							String classType = (String) classTypeTF
									.getSelectedItem();
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
									JOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													JOptionPane.ERROR_MESSAGE);
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
									JOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													JOptionPane.ERROR_MESSAGE);
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
									JOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													JOptionPane.ERROR_MESSAGE);
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
									JOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													JOptionPane.ERROR_MESSAGE);
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
									JOptionPane
											.showMessageDialog(
													getRootPane(),
													"Start time cannot be greater that end time!",
													"Error",
													JOptionPane.ERROR_MESSAGE);
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
							JOptionPane
									.showMessageDialog(
											getRootPane(),
											"Please fill all fields and use correct format for each field",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				getRootPane().setDefaultButton(okButton_1);
			}
			{
				cancelButton_1 = new CustomAccountButton("/icons/noa_en/accountcancelbutton.png");
				cancelButton_1.setUndecorated(true);
				cancelButton_1.setActionCommand("Cancel");
				cancelButton_1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap(266, Short.MAX_VALUE)
						.addComponent(okButton_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cancelButton_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(266, Short.MAX_VALUE))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(okButton_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addComponent(cancelButton_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddNewCourseDialog(final EventList<Course> courseList,
			final Course course, final EventList<Course> selectedCourse,
			EventList<Instructor> instructorList) throws Exception {
		setTitle(i18n.getParam("Edit course"));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(400, 200, 610, 278);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.NORTH);
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
			classTypeTF = new JajeemComboBox();
		}
		contentPanel.add(classTypeTF);
		contentPanel.add(levelLabel);
		{
			levelCombo = new JajeemComboBox();
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
			instructorNameCombo = new JajeemComboBox();
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
			dayCombo1 = new JajeemComboBox();
			dayCombo1.setModel(new DefaultComboBoxModel(new String[] { "Su",
					"Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo1);
		}
		{
			startTimeLabel1 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel1);
		}
		{
			startTimeTF1 = new JajeemComboBox();
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
			endTimeTF1 = new JajeemComboBox();
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
			dayCombo2 = new JajeemComboBox();
			dayCombo2.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo2);
		}
		{
			startTimeLabel2 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel2);
		}
		{
			startTimeTF2 = new JajeemComboBox();
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
			endTimeTF2 = new JajeemComboBox();
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
			dayCombo3 = new JajeemComboBox();
			dayCombo3.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo3);
		}
		{
			startTimeLabel3 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel3);
		}
		{
			startTimeTF3 = new JajeemComboBox();
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
			endTimeTF3 = new JajeemComboBox();
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
			dayCombo4 = new JajeemComboBox();
			dayCombo4.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo4);
		}
		{
			startTimeLabel4 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel4);
		}
		{
			startTimeTF4 = new JajeemComboBox();
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
			endTimeTF4 = new JajeemComboBox();
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
			dayCombo5 = new JajeemComboBox();
			dayCombo5.setModel(new DefaultComboBoxModel(new String[] { "",
					"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" }));
			contentPanel.add(dayCombo5);
		}
		{
			startTimeLabel5 = new WebLabel(i18n.getParam("Start time"));
			contentPanel.add(startTimeLabel5);
		}
		{
			startTimeTF5 = new JajeemComboBox();
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
			endTimeTF5 = new JajeemComboBox();
			endTimeTF5.setModel(new DefaultComboBoxModel(new String[] { "",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22" }));
			contentPanel.add(endTimeTF5);
		}
		{
			WebPanel buttonPane = new WebPanel();
			buttonPane.setMargin(new Insets(3, 0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
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
							String classType = (String) classTypeTF
									.getSelectedItem();
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
							JOptionPane
									.showMessageDialog(
											getRootPane(),
											"Please fill all fields and use correct format for each field",
											"Error", JOptionPane.ERROR_MESSAGE);
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
		
		pack();
	}
}
